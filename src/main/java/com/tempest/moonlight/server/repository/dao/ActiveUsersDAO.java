package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.event.UserSession;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface ActiveUsersDAO extends IdentifiedEntityDAO<UserSession, UserSession> {
//    Collection<UserSession> getActiveSessions();
//    Collection<String> getActiveUsers();

    Map<String, Boolean> areUsersActive(Collection<String> logins);

    boolean containsSessionsOfUser(String login);
}
