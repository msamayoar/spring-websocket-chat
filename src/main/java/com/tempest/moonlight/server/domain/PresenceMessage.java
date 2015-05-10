package com.tempest.moonlight.server.domain;

/**
 * Created by Yurii on 2015-05-07.
 */
public class PresenceMessage {
    private String user;
    private PresenceStatus status;

    public PresenceMessage() {
    }

    public PresenceMessage(String user, PresenceStatus status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public PresenceMessage setUser(String user) {
        this.user = user;
        return this;
    }

    public PresenceStatus getStatus() {
        return status;
    }

    public PresenceMessage setStatus(PresenceStatus status) {
        this.status = status;
        return this;
    }
}
