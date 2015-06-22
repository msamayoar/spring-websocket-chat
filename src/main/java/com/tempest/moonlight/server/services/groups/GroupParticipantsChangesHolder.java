package com.tempest.moonlight.server.services.groups;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;

import java.util.Set;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupParticipantsChangesHolder {
    public final String signature;

    public final Set<GenericParticipant> updatedParticipants;
    public final Set<GenericParticipant> addedParticipants;
    public final Set<GenericParticipant> removedParticipants;

    public GroupParticipantsChangesHolder(String signature, Set<GenericParticipant> updatedParticipants, Set<GenericParticipant> addedParticipants, Set<GenericParticipant> removedParticipants) {
        this.signature = signature;
        this.updatedParticipants = updatedParticipants;
        this.addedParticipants = addedParticipants;
        this.removedParticipants = removedParticipants;
    }

    @Override
    public String toString() {
        return "GroupParticipantsChangesHolder{" +
                "signature='" + signature + '\'' +
                ", updatedParticipants=" + updatedParticipants +
                ", addedParticipants=" + addedParticipants +
                ", removedParticipants=" + removedParticipants +
                '}';
    }
}
