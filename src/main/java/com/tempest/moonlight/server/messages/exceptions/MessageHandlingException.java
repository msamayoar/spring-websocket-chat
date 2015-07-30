package com.tempest.moonlight.server.messages.exceptions;

/**
 * Created by Yurii on 2015-05-08.
 */
public abstract class MessageHandlingException extends Exception {
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
