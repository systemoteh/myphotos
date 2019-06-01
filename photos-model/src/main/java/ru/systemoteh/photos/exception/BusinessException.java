package ru.systemoteh.photos.exception;

public abstract class BusinessException extends ApplicationException {

    public BusinessException(String message) {
        super(message, null, true, false);
    }
}
