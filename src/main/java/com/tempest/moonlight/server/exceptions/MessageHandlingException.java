package com.tempest.moonlight.server.exceptions;

/**
 * Created by Yurii on 2015-05-08.
 */
public class MessageHandlingException extends RuntimeException {
    public MessageHandlingException() {
        super();
    }

    public MessageHandlingException(String message) {
        super(message);
    }

    public MessageHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageHandlingException(Throwable cause) {
        super(cause);
    }

    protected MessageHandlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
