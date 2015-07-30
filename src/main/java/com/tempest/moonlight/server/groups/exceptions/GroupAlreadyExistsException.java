package com.tempest.moonlight.server.groups.exceptions;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupAlreadyExistsException extends GroupsException {
    public GroupAlreadyExistsException(String groupSignature) {
        super("Group with signature =  '" + groupSignature + "' already exists");
    }
}
