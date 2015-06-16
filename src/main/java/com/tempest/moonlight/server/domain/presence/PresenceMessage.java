package com.tempest.moonlight.server.domain.presence;

/**
 * Created by Yurii on 2015-05-07.
 */
public class PresenceMessage {
    private String login;
    private PresenceStatus status;

    public PresenceMessage(String login, PresenceStatus status) {
        this.login = login;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PresenceMessage{" +
                "login='" + login + '\'' +
                ", status=" + status +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public PresenceMessage setLogin(String login) {
        this.login = login;
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
