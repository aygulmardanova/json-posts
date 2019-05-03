package ru.itpark.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itpark.client.RequestClient;
import ru.itpark.processor.CachedAnnotationBPP;
import ru.itpark.processor.PlaceholderSubstitutionBFPP;
import ru.itpark.service.PostService;

@Configuration
public class JavaConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PlaceholderSubstitutionBFPP();
    }

    @Bean
    public static BeanPostProcessor beanPostProcessor() {
        return new CachedAnnotationBPP();
    }

    @Bean
    public RequestClient requestClient() {
        return new RequestClient();
    }

    @Bean
    public PostService postService(RequestClient requestClient) {
        return new PostService(requestClient);
    }

}
