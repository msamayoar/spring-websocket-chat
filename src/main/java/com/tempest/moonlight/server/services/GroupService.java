package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.Group;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-01.
 */
public interface GroupService {
    boolean checkGroupExists(String group);
    Group createGroup(String group);
    boolean checkUserBelongsToGrouop(String group, String user);
    Collection<String> getParticipants(String group);
    void iviteToGroup(String group, String userToInvite, String performer);
    void leaveGroup(String group, String user);
    void deleteGroup(String group, String performer);
}
