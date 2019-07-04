package ru.systemoteh.photos.ejb.service.impl;

import net.coobird.thumbnailator.Thumbnails;
import ru.systemoteh.photos.common.config.ImageCategory;
import ru.systemoteh.photos.ejb.service.ImageResizerService;
import ru.systemoteh.photos.exception.ApplicationException;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static net.coobird.thumbnailator.geometry.Positions.CENTER;

@ApplicationScoped
public class ThumbnailatorImageResizerService implements ImageResizerService {

    @Override
    public void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory) {
        try {
            Thumbnails.Builder<File> builder = Thumbnails.of(sourcePath.toFile());
            if (imageCategory.isCrop()) {
                builder.crop(CENTER);
            }
            builder.size(imageCategory.getWidth(), imageCategory.getHeight())
                    .outputFormat(imageCategory.getOutputFormat())
                    .outputQuality(imageCategory.getQuality())
                    .allowOverwrite(true)
                    .toFile(destinationPath.toFile());
        } catch (IOException ex) {
            throw new ApplicationException("Can't resize image: " + ex.getMessage(), ex);
        }
    }
}
