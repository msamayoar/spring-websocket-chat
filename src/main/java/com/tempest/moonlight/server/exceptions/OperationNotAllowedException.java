package com.tempest.moonlight.server.exceptions;

/**
 * Created by Yurii on 2015-06-01.
 */
public class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException() {
        super();
    }

    public OperationNotAllowedException(String message) {
        super(message);
    }

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected OperationNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
