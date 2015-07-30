package com.tempest.moonlight.server.contacts.dto;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.common.dto.BiDirectionalDTO;

/**
 * Created by Yurii on 2015-06-21.
 */
public class GenericParticipantDTO implements BiDirectionalDTO<GenericParticipant> {

    protected int type;
    protected String signature;

    public GenericParticipantDTO() {
    }

    @Override
    public void fillEntity(GenericParticipant participant) {
        participant.setType(ParticipantType.getByValue(type));
        participant.setSignature(signature);
    }

    @Override
    public void fillWithEntity(GenericParticipant participant) {
        type = participant.getType().getValue();
        signature = participant.getSignature();
    }

    @Override
    public String toString() {
        return "GenericParticipantDTO{" +
                "type=" + type +
                ", signature='" + signature + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public GenericParticipantDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public GenericParticipantDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }
}
