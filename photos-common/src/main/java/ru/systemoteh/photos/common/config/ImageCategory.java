package ru.systemoteh.photos.common.config;

import lombok.Getter;

@Getter
public enum ImageCategory {

    PROFILE_AVATAR("media/avatar/", 128, 128, true, 0.8),

    SMALL_PHOTO("media/photo/", 400, 250, true, 0.7),

    LARGE_PHOTO("media/photo/", 1600, 900, false, 1.0);

    private final String relativeRoot;
    private final int width;
    private final int height;
    private final boolean crop;
    private final double quality;
    private final String outputFormat = "jpg";

    ImageCategory(String relativeRoot, int width, int height, boolean crop, double quality) {
        this.relativeRoot = relativeRoot;
        this.width = width;
        this.height = height;
        this.crop = crop;
        this.quality = quality;
    }

    public static boolean isImageCategoryUrl(String url) {
        if (url != null) {
            for (ImageCategory imageCategory : ImageCategory.values()) {
                if (url.contains(imageCategory.getRelativeRoot())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getRelativeRoot() {
        return relativeRoot;
    }
}
