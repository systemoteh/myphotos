package ru.systemoteh.photos.model;

import java.nio.file.Path;

public interface ImageResource extends AutoCloseable {

    Path getTempPath();

    @Override
    void close();
}
