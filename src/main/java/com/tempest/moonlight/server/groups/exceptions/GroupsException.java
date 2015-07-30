package com.tempest.moonlight.server.groups.exceptions;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupsException extends Exception {
    public GroupsException() {
        super();
    }

    public GroupsException(String message) {
        super(message);
    }

    public GroupsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupsException(Throwable cause) {
        super(cause);
    }

    protected GroupsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
