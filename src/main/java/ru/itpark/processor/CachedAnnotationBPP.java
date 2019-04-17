package ru.itpark.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import ru.itpark.annotation.Cached;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CachedAnnotationBPP implements BeanPostProcessor {

    private final Map<Integer, Object> cachedPosts = new HashMap<>();
    private final Map<String, Method> methods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method ->
                        method.isAnnotationPresent(Cached.class))
                .forEach(method ->
                        methods.put(beanName, method));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!methods.containsKey(beanName))
            return bean;

        var enhancer = new Enhancer();
        enhancer.setSuperclass(methods.get(beanName).getDeclaringClass());

        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            if (!methods.get(beanName).equals(method))
                return method.invoke(bean, objects);

            if (cachedPosts.containsKey(objects[0])) {
                return cachedPosts.get(objects[0]) + " (proxied)";
            }
            var result = methodProxy.invokeSuper(o, objects);
            cachedPosts.put((Integer) objects[0], result);

            return result;
        });

        return enhancer.create();
    }

}
