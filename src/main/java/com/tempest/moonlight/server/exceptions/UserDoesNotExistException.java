package com.tempest.moonlight.server.exceptions;

/**
 * Created by Yurii on 2015-05-08.
 */
public class UserDoesNotExistException extends MessageHandlingException {
    public UserDoesNotExistException(String username) {
        super("User with login '" + username + "' does not exist.");
    }
}
