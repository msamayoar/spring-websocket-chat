package com.tempest.moonlight.server.groups.dto;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;

import java.util.List;

/**
 * Created by Yurii on 2015-06-22.
 */
public class GroupParticipantsDTO {
    private String signature;
    private List<GenericParticipantDTO> participants;

    public GroupParticipantsDTO() {
    }

    public GroupParticipantsDTO(String signature, List<GenericParticipantDTO> participants) {
        this.signature = signature;
        this.participants = participants;
    }

    public GroupParticipantsDTO(Group group, List<GenericParticipantDTO> participants) {
        this(group.getSignature(), participants);
    }

    @Override
    public String toString() {
        return "GroupParticipantsDTO{" +
                "signature='" + signature + '\'' +
                ", participants=" + participants +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public GroupParticipantsDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public List<GenericParticipantDTO> getParticipants() {
        return participants;
    }

    public GroupParticipantsDTO setParticipants(List<GenericParticipantDTO> participants) {
        this.participants = participants;
        return this;
    }
}
