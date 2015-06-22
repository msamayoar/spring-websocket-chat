package com.tempest.moonlight.server.services.groups;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.repository.dao.contacts.ContactsDAO;
import com.tempest.moonlight.server.repository.dao.groups.GroupDAO;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yurii on 2015-06-22.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private ContactsDAO contactsDAO;

    @Override
    public boolean existsGroup(String group) {
        return groupDAO.existsWithKey(group);
    }

    @Override
    public Group createGroup(String groupSignature) {
        Group group = new Group();
        group.setEmpty(true);
        group.setLastChangeTime(System.currentTimeMillis());
        groupDAO.save(group);
        return group;
    }

    @Override
    public boolean addUserToGroup(Group group, String userToAdd) {
        GenericContact groupParticipant = new GenericContact(
                group.toGenericParticipant(),
                new GenericParticipant(ParticipantType.USER, userToAdd)
        );
        if(!contactsDAO.exists(groupParticipant)) {
            contactsDAO.save(groupParticipant);
            group.setEmpty(false);
            group.setLastChangeTime(System.currentTimeMillis());
            groupDAO.save(group);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUserBelongsToGroup(String group, String user) {
        return contactsDAO.isUserGroupParticipant(user, group);
    }

    @Override
    public GroupParticipantsChangesHolder processAddRemoveParticipants(String groupSignature, Collection<GenericParticipant> participantsToAdd, Collection<GenericParticipant> participantsToRemove) {
        Group group = getGroup(groupSignature);
        Set<GenericParticipant> currentParticipants = new HashSet<>(getParticipants(group));

        boolean changesPerformed = false;

        HashSet<GenericParticipant> toRemove = new HashSet<>(participantsToRemove);
        toRemove.retainAll(currentParticipants);
        if(!toRemove.isEmpty()) {
            contactsDAO.addRemoveGroupParticipants(groupSignature, toRemove, false);
            currentParticipants.removeAll(toRemove);
            changesPerformed = true;
        }

        Set<GenericParticipant> toAdd = new HashSet<>(participantsToAdd);
        toAdd.removeAll(currentParticipants);
        if(!toAdd.isEmpty()) {
            contactsDAO.addRemoveGroupParticipants(groupSignature, toAdd, true);
            currentParticipants.addAll(toAdd);
            changesPerformed = true;
        }

        if(changesPerformed) {
            group.setEmpty(currentParticipants.isEmpty());
            group.setLastChangeTime(System.currentTimeMillis());
        }

        return new GroupParticipantsChangesHolder(
                groupSignature,
                currentParticipants,
                toAdd,
                toRemove
        );
    }

    @Override
    public Collection<GenericParticipant> getParticipants(String groupSignature) {
        return CollectionsUtils.convertToSet(
                contactsDAO.getGroupParticipants(groupSignature),
                GenericContact::getContact
        );
    }

    @Override
    public Group getGroup(String groupSignature) {
        return groupDAO.get(groupSignature);
    }

    @Override
    public void inviteToGroup(String group, String userToInvite, String performer) {

    }

    @Override
    public void leaveGroup(String group, String user) {

    }

    @Override
    public void deleteGroup(String group, String performer) {

    }
}
