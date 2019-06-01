package ru.systemoteh.photos.exception;

public class InvalidAccessTokenException extends BusinessException {

    public InvalidAccessTokenException(String message) {
        super(message);
    }

}
