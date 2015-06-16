package com.tempest.moonlight.server.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempest.moonlight.server.serialization.GenericContactJsonSerializer;

/**
 * Created by Yurii on 2015-06-16.
 */
@JsonSerialize(using = GenericContactJsonSerializer.class)
public class GenericContact {
    /**
     * Type of user's contact
     */
    private ContactType type;

    /**
     * Id of user's contact. It is a login - for user, groupId - for group
     */
    private String id;

    public GenericContact() {
    }

    public GenericContact(ContactType type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return "GenericContact{" +
                "type=" + type +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericContact that = (GenericContact) o;

        if (type != that.type) return false;
        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public ContactType getType() {
        return type;
    }

    public GenericContact setType(ContactType type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public GenericContact setId(String id) {
        this.id = id;
        return this;
    }
}
