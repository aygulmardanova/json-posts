package ru.itpark.processor;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PlaceholderSubstitutionBFPP extends PropertyPlaceholderConfigurer {

    public PlaceholderSubstitutionBFPP() {
        setLocation(new ClassPathResource("connection.properties"));
    }

}
