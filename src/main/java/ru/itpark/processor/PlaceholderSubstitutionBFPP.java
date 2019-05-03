package ru.itpark.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
public class PlaceholderSubstitutionBFPP extends PropertyPlaceholderConfigurer {

    private Resource location;

    public PlaceholderSubstitutionBFPP() {
        location = new ClassPathResource("properties.json");
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        var resource = new EncodedResource(location, "UTF-8");

        Map<String, Object> json = new ObjectMapper()
                .readValue(resource.getInputStream(), new TypeReference<Map<String, Object>>() {});
        props.putAll(json);
    }
}
