package com.tempest.moonlight.server.domain.contacts;

import com.tempest.moonlight.server.annotations.DTO;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.repository.dao.IdentifiedEntity;
import com.tempest.moonlight.server.services.dto.EntityDTO;
import com.tempest.moonlight.server.services.dto.GenericContactDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-06-16.
 */
@DTO(type = EntityDTO.DtoType.S2C, dto = GenericContactDTO.class)
//@DTO(type = EntityDTO.DtoType.C2S, dto = GenericContactDTO.class)
@Component
public class GenericContact implements IdentifiedEntity<GenericContact>, Serializable {

    private GenericParticipant owner;
    private GenericParticipant contact;

    public GenericContact() {
    }

    public GenericContact(GenericParticipant owner, GenericParticipant contact) {
        this.owner = owner;
        this.contact = contact;
    }

    public GenericContact(ParticipantType ownerType, String owner, ParticipantType contactType, String contact) {
        this(new GenericParticipant(ownerType, owner), new GenericParticipant(contactType, contact));
    }

    public GenericContact(ParticipantType ownerType, String owner, GenericParticipant contact) {
        this(new GenericParticipant(ownerType, owner), contact);
    }

    public static GenericContact invert(GenericContact contact) {
        return new GenericContact(
                contact.contact,
                contact.owner
        );
    }

    public GenericContact invert() {
        return invert(this);
    }


    @Override
    public String toString() {
        return "GenericContact{" +
                "owner=" + owner +
                ", contact=" + contact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericContact that = (GenericContact) o;

        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return !(contact != null ? !contact.equals(that.contact) : that.contact != null);

    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }

    @Override
    public GenericContact getKey() {
        return this;
    }

    public GenericParticipant getOwner() {
        return owner;
    }

    public GenericContact setOwner(GenericParticipant owner) {
        this.owner = owner;
        return this;
    }

    public GenericParticipant getContact() {
        return contact;
    }

    public GenericContact setContact(GenericParticipant contact) {
        this.contact = contact;
        return this;
    }
}
