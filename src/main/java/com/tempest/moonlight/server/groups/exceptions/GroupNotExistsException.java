package com.tempest.moonlight.server.groups.exceptions;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupNotExistsException extends GroupsException {
    public GroupNotExistsException(String groupSignature) {
        super("Group with signature = '" + groupSignature + "' does not exist");
    }
}
