package ru.itpark;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import ru.itpark.client.RequestClient;
import ru.itpark.config.JavaConfig;
import ru.itpark.processor.CachedAnnotationBPP;
import ru.itpark.processor.PlaceholderSubstitutionBFPP;
import ru.itpark.service.PostService;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    var ids = List.of(12, 14, 17, 12, 14);
    {
      var context = new AnnotationConfigApplicationContext("ru.itpark.client", "ru.itpark.processor", "ru.itpark.service");
      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
      System.out.println("\nURL: " + service.getClient().getUrl());
    }

    {
      var context = new AnnotationConfigApplicationContext(JavaConfig.class);
      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
      System.out.println("\nURL: " + service.getClient().getUrl());
    }

    {
      var context = new ClassPathXmlApplicationContext("beans.xml");
      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
      System.out.println("\nURL: " + service.getClient().getUrl());
    }

    {
      // нужно просто посмотреть сюда: org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency
      // и найти разницу между context:annotation-config и без context:annotation-config
      // подсказка: SimpleAutowireCandidateResolver и не Simple :-)
      // исходя из этого вытекает всё остальное: что для Groovy, что для Programmatic
      var factory = new DefaultListableBeanFactory();
      factory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
      var context = new GenericApplicationContext(factory);
      var reader = new XmlBeanDefinitionReader(context);
      reader.loadBeanDefinitions("xml-beans.xml");
      context.refresh();
      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
      System.out.println("\nURL: " + service.getClient().getUrl());
    }


    {
      var context = new GenericGroovyApplicationContext("beans.groovy");
      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
      System.out.println("\nURL: " + service.getClient().getUrl());
    }


    {
      var context = new GenericApplicationContext();

      context.registerBean("placeholderSubstitutionBFPP", PlaceholderSubstitutionBFPP.class);
      context.registerBean("autowiredAnnotationBeanPostProcessor", AutowiredAnnotationBeanPostProcessor.class);
      context.registerBean("beanPostProcessor", CachedAnnotationBPP.class);
      context.registerBean("requestClient", RequestClient.class);
      context.registerBean("postService", PostService.class);
      context.refresh();

      var service = context.getBean(PostService.class);
      ids.forEach(id ->
          System.out.println("\n" + id + ": " + service.getPost(id))
      );
//            TODO: fix placeholder 'url'
      System.out.println("\nURL: " + service.getClient().getUrl());
    }

  }
}
