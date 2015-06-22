package com.tempest.moonlight.server.exceptions.groups;

/**
 * Created by Yurii on 2015-06-22.
 */
public class IllegalGroupAccessException extends GroupsException {
    public IllegalGroupAccessException(String groupSignature) {
        super("Illegal access to group with signature = " + groupSignature);
    }
}
