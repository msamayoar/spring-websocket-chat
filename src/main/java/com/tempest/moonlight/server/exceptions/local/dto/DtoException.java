package com.tempest.moonlight.server.exceptions.local.dto;

/**
 * Created by Yurii on 2015-06-19.
 */
public abstract class DtoException extends RuntimeException {
    public DtoException() {
        super();
    }

    public DtoException(String message) {
        super(message);
    }

    public DtoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DtoException(Throwable cause) {
        super(cause);
    }

    protected DtoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
