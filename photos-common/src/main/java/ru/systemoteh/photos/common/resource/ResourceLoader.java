package ru.systemoteh.photos.common.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader {

    boolean isSupport(String resourceName);

    InputStream getInputStream(String resourceName) throws IOException;
}
