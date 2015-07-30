package com.tempest.moonlight.server.domain.contacts;

import com.tempest.moonlight.server.annotations.DTO;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.common.dto.EntityDTO;
import com.tempest.moonlight.server.contacts.dto.GenericParticipantDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Yurii on 2015-06-20.
 */
@DTO(type = EntityDTO.DtoType.BiDir, dto = GenericParticipantDTO.class)
@Component
public class GenericParticipant {
    private ParticipantType type;
    /**
     * Login (if user) of groupId (if group)
     */
    private String signature;

    public GenericParticipant() {
    }

    public GenericParticipant(ParticipantType type, String signature) {
        this.type = type;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "GenericParticipant{" +
                "type=" + type +
                ", signature='" + signature + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericParticipant that = (GenericParticipant) o;

        if (type != that.type) return false;
        return !(signature != null ? !signature.equals(that.signature) : that.signature != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    public ParticipantType getType() {
        return type;
    }

    public GenericParticipant setType(ParticipantType type) {
        this.type = type;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public GenericParticipant setSignature(String signature) {
        this.signature = signature;
        return this;
    }
}
