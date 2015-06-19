package com.tempest.moonlight.server.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempest.moonlight.server.serialization.GenericContactJsonSerializer;
import com.tempest.moonlight.server.annotations.DTO;
import com.tempest.moonlight.server.repository.dao.IdentifiedEntity;
import com.tempest.moonlight.server.services.dto.EntityDTO;
import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-06-16.
 */
//@JsonSerialize(using = GenericContactJsonSerializer.class)
@DTO(type = EntityDTO.DtoType.S2C, dto = GenericContactDTO.class)
//@DTO(type = EntityDTO.DtoType.C2S, dto = GenericContactDTO.class)
@Component
public class GenericContact implements IdentifiedEntity<GenericContact>, Serializable {
    /**
     * Type of user's contact
     */
    private ContactType type;

    /**
     * Id of user's contact. It is a login - for user, groupId - for group
     */
    private String signature;

    private ContactType ownerType;

    private String owner;

    public GenericContact() {
    }

    public GenericContact(ContactType type, String signature, ContactType ownerType, String owner) {
        this.type = type;
        this.signature = signature;
        this.ownerType = ownerType;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "GenericContact{" +
                "type=" + type +
                ", signature='" + signature + '\'' +
                ", ownerType=" + ownerType +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericContact that = (GenericContact) o;

        if (type != that.type) return false;
        return !(signature != null ? !signature.equals(that.signature) : that.signature != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    @Override
    public GenericContact getKey() {
        return this;
    }

    public ContactType getType() {
        return type;
    }

    public GenericContact setType(ContactType type) {
        this.type = type;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public GenericContact setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public ContactType getOwnerType() {
        return ownerType;
    }

    public GenericContact setOwnerType(ContactType ownerType) {
        this.ownerType = ownerType;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public GenericContact setOwner(String owner) {
        this.owner = owner;
        return this;
    }
}
