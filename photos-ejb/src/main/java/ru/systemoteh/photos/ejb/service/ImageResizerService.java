package ru.systemoteh.photos.ejb.service;

import ru.systemoteh.photos.common.config.ImageCategory;

import java.nio.file.Path;

public interface ImageResizerService {

    void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory);
}
