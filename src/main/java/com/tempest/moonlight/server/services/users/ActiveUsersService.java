package com.tempest.moonlight.server.services.users;

import com.tempest.moonlight.server.event.UserSession;

/**
 * Created by Yurii on 2015-06-10.
 */
public interface ActiveUsersService {
    /**
     *
     * @param userSession new session of user
     * @return true if this is the only available session of user (user did not have opened sessions before), false elsewhere
     */
    boolean addUserSession(UserSession userSession);

    UserSession getBySessionId(String sessionId);

    boolean sessionExists(String sessionId);

    /**
     *
     * @param sessionId ID of WebSocket session
     * @param login login of user
     * @return true if this session was the only opened user's session
     */
    boolean deleteUserSession(String sessionId, String login);
}
