package com.tempest.moonlight.server.users.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Yurii on 2015-05-08.
 */
public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
