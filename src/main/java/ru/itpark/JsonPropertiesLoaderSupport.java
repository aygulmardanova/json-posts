package ru.itpark;

import org.springframework.core.io.support.PropertiesLoaderSupport;

import java.io.IOException;
import java.util.Properties;

public class JsonPropertiesLoaderSupport extends PropertiesLoaderSupport {
    @Override
    protected void loadProperties(Properties props) throws IOException {

        super.loadProperties(props);
    }
}
