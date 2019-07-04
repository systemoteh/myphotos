package ru.systemoteh.photos.ejb.service;

import ru.systemoteh.photos.common.config.ImageCategory;
import ru.systemoteh.photos.model.OriginalImage;

import java.nio.file.Path;

public interface ImageStorageService {

    String saveProtectedImage(Path path);

    String savePublicImage(ImageCategory imageCategory, Path path);

    void deletePublicImage(String url);

    OriginalImage getOriginalImage(String originalUrl);
}
