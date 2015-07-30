package com.tempest.moonlight.server.groups.dto;

import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupParticipantsChangeDTO {

    private String signature;

    private List<GenericParticipantDTO> add;
    private List<GenericParticipantDTO> remove;

    public GroupParticipantsChangeDTO() {
    }

    public GroupParticipantsChangeDTO(String signature, Collection<GenericParticipantDTO> participantsToAdd, Collection<GenericParticipantDTO> participantsToRemove) {
        this.signature = signature;
        this.add = new ArrayList<>(participantsToAdd);
        this.remove = new ArrayList<>(participantsToRemove);
    }

    @Override
    public String toString() {
        return "GroupParticipantsChangeDTO{" +
                "signature='" + signature + '\'' +
                ", add=" + add +
                ", remove=" + remove +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public GroupParticipantsChangeDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public List<GenericParticipantDTO> getAdd() {
        return add;
    }

    public GroupParticipantsChangeDTO setAdd(List<GenericParticipantDTO> add) {
        this.add = add;
        return this;
    }

    public List<GenericParticipantDTO> getRemove() {
        return remove;
    }

    public GroupParticipantsChangeDTO setRemove(List<GenericParticipantDTO> remove) {
        this.remove = remove;
        return this;
    }
}
