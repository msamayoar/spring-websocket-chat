package com.tempest.moonlight.server.exceptions;

/**
 * Created by Yurii on 2015-06-01.
 */
public class GroupAlreadyExistsException extends Exception {
    public GroupAlreadyExistsException() {
        super();
    }

    public GroupAlreadyExistsException(String message) {
        super(message);
    }

    public GroupAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected GroupAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
