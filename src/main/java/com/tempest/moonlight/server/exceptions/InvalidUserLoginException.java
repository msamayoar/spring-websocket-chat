package com.tempest.moonlight.server.exceptions;

/**
 * Created by Yurii on 2015-05-08.
 */
public class InvalidUserLoginException extends MessageHandlingException {
    public InvalidUserLoginException() {
        super("Invalid user login");
    }

    public InvalidUserLoginException(String login) {
        super("Invalid user login '" + login + "'.");
    }
}
