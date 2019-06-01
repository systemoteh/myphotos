package ru.systemoteh.photos.model;

import javax.validation.Path;

public interface ImageResource extends AutoCloseable {

    Path getTempPath();

    @Override
    void close();
}
