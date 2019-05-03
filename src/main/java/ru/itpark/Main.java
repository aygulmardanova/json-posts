package ru.itpark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
//        var ids = List.of(12);

        {
            var context = new AnnotationConfigApplicationContext(
                    "ru.itpark.client", "ru.itpark.processor", "ru.itpark.service");

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
            var context = new GenericGroovyApplicationContext("beans.groovy");

            var service = context.getBean(PostService.class);
            ids.forEach(id ->
                    System.out.println("\n" + id + ": " + service.getPost(id))
            );
            System.out.println("\nURL: " + service.getClient().getUrl());
        }

        {
            var context = new AnnotationConfigApplicationContext();
            context.registerBean("placeholderSubstitutionBFPP", PlaceholderSubstitutionBFPP.class);
            context.registerBean("beanPostProcessor", CachedAnnotationBPP.class);
            context.registerBean("requestClient", RequestClient.class);
            context.registerBean("postService", PostService.class);
            context.refresh();

            var service = context.getBean(PostService.class);
            ids.forEach(id ->
                    System.out.println("\n" + id + ": " + service.getPost(id))
            );
            System.out.println("\nURL: " + service.getClient().getUrl());
        }
    }
}
