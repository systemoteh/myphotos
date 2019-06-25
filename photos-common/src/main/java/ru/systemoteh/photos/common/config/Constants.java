package ru.systemoteh.photos.common.config;

import java.util.concurrent.TimeUnit;

public class Constants {

    public static final long MAX_UPLOADED_PHOTO_SIZE_IN_BYTES = 10 * 1024 * 1024; // 10 Mb

    public static final long DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS = TimeUnit.SECONDS.toMillis(30);

    private Constants() {
    }
}
