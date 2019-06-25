package ru.systemoteh.photos.common.resource;

import javax.enterprise.inject.Vetoed;
import java.io.IOException;
import java.io.InputStream;

@Vetoed
public interface ResourceLoader {

    boolean isSupport(String resourceName);

    InputStream getInputStream(String resourceName) throws IOException;
}
