package com.tempest.moonlight.server.repository.persistence.dao;

import com.tempest.moonlight.server.event.UserSession;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface ActiveUsersDAO extends IdentifiedEntityDAO<UserSession, UserSession> {
    Collection<UserSession> getActiveSessions();
    Collection<String> getActiveUsers();
    boolean containsSessionsOfUser(String login);
}
