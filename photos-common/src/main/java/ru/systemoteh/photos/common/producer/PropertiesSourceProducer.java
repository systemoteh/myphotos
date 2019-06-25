package ru.systemoteh.photos.common.producer;

import ru.systemoteh.photos.common.annotation.cdi.PropertiesSource;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Properties;

public class PropertiesSourceProducer extends AbstractPropertiesLoader {

    @Produces
    @PropertiesSource("")
    private Properties loadProperties(InjectionPoint injectionPoint) {
        Properties properties = new Properties();
        PropertiesSource propertiesSource = injectionPoint.getAnnotated().getAnnotation(PropertiesSource.class);
        loadProperties(properties, propertiesSource.value());
        return properties;
    }
}
