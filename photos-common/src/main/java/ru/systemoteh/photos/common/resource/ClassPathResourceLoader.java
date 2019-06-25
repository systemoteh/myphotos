package ru.systemoteh.photos.common.resource;

import ru.systemoteh.photos.exception.ConfigException;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;

@ApplicationScoped
public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public boolean isSupport(String resourceName) {
        return resourceName.startsWith("classpath:");
    }

    @Override
    public InputStream getInputStream(String resourceName) {
        String classPathResourceName = resourceName.replace("classpath:", "");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            InputStream inputStream = classLoader.getResourceAsStream(classPathResourceName);
            if (inputStream != null) {
                return inputStream;
            }
        }
        throw new ConfigException("Classpath resource not found: " + classPathResourceName);
    }
}
