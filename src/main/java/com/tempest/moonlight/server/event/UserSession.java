package com.tempest.moonlight.server.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempest.moonlight.server.common.dao.IdentifiedEntity;
import com.tempest.moonlight.server.serialization.UserSessionMessageSerializer;

import java.io.Serializable;

@JsonSerialize(using = UserSessionMessageSerializer.class)
public class UserSession implements Serializable, IdentifiedEntity<UserSession> {
	private String login;
    private String sessionId;

    public UserSession(String sessionId, String login) {
        this(sessionId);
        this.login = login;
    }

    public UserSession(String sessionId) {
        if(sessionId == null) {
            throw new NullPointerException();
        }
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        return sessionId.equals(that.sessionId);

    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "login='" + login + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

    public String getSessionId() {
        return sessionId;
    }

    public UserSession setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public UserSession getKey() {
        return this;
    }
}
