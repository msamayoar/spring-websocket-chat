package com.tempest.moonlight.server.domain;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.repository.dao.IdentifiedEntity;

/**
 * Created by Yurii on 2015-06-01.
 */
public class Group implements IdentifiedEntity<String> {

    private String signature;

    private boolean opened;

    private boolean empty;
    private long lastChangeTime;

    public Group() {
    }

    public Group(String signature) {
        this.signature = signature;
    }

    public static GenericParticipant toGenericParticipant(Group group) {
        return new GenericParticipant(ParticipantType.GROUP, group.getSignature());
    }

    public GenericParticipant toGenericParticipant() {
        return toGenericParticipant(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return signature.equals(group.signature);

    }

    @Override
    public int hashCode() {
        return signature.hashCode();
    }

    @Override
    public String toString() {
        return "Group{" +
                "signature='" + signature + '\'' +
                ", opened=" + opened +
                ", lastChangeTime=" + lastChangeTime +
                ", empty=" + empty +
                '}';
    }

    @Override
    public String getKey() {
        return signature;
    }

    public String getSignature() {
        return signature;
    }

    public Group setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public boolean isOpened() {
        return opened;
    }

    public Group setOpened(boolean opened) {
        this.opened = opened;
        return this;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Group setEmpty(boolean empty) {
        this.empty = empty;
        return this;
    }

    public long getLastChangeTime() {
        return lastChangeTime;
    }

    public Group setLastChangeTime(long lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
