package com.tempest.moonlight.server.exceptions.contacts;

/**
 * Created by Yurii on 2015-06-20.
 */
public abstract class ContactRequestException extends Exception {
    public ContactRequestException() {
        super();
    }

    public ContactRequestException(String message) {
        super(message);
    }

    public ContactRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactRequestException(Throwable cause) {
        super(cause);
    }

    protected ContactRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
