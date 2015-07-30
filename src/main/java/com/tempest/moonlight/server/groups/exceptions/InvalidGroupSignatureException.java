package com.tempest.moonlight.server.groups.exceptions;

/**
 * Created by Yurii on 2015-06-22.
 */
public class InvalidGroupSignatureException extends GroupsException {
    public InvalidGroupSignatureException() {
        super("Group signature can not be empty");
    }
}
