package com.tempest.moonlight.server.contacts.exceptions;

/**
 * Created by Yurii on 2015-06-21.
 */
public abstract class ContactsException extends Exception {
    public ContactsException() {
        super();
    }

    public ContactsException(String message) {
        super(message);
    }

    public ContactsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactsException(Throwable cause) {
        super(cause);
    }

    protected ContactsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
