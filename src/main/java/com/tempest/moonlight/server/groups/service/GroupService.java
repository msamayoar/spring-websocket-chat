package com.tempest.moonlight.server.groups.service;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-01.
 */
public interface GroupService {

    boolean existsGroup(String group);
    Group createGroup(String groupSignature);
    boolean addUserToGroup(Group group, String userToAdd);

    Collection<GenericParticipant> getParticipants(String groupSignature);
    default Collection<GenericParticipant> getParticipants(Group group) {
        return getParticipants(group.getSignature());
    }

    Group getGroup(String groupSignature);

    boolean checkUserBelongsToGroup(String group, String user);
    default boolean checkUserBelongsToGroup(Group group, String user) {
        return checkUserBelongsToGroup(group.getSignature(), user);
    }

    GroupParticipantsChangesHolder processAddRemoveParticipants(String groupSignature, Collection<GenericParticipant> participantsToAdd, Collection<GenericParticipant> participantsToRemove);
    default GroupParticipantsChangesHolder processAddRemoveParticipants(Group group, Collection<GenericParticipant> participantsToAdd, Collection<GenericParticipant> participantsToRemove) {
        return processAddRemoveParticipants(group.getSignature(), participantsToAdd, participantsToRemove);
    }

    void inviteToGroup(String group, String userToInvite, String performer);
    void leaveGroup(String group, String user);
    void deleteGroup(String group, String performer);

}
