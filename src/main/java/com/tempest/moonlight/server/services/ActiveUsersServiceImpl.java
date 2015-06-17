package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.event.UserSession;
import com.tempest.moonlight.server.repository.dao.ActiveUsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yurii on 2015-06-10.
 */
@Service
public class ActiveUsersServiceImpl implements ActiveUsersService {

    @Autowired
    private ActiveUsersDAO activeUsersDAO;

    @Override
    public boolean addUserSession(UserSession userSession) {
        boolean containsSessionsOfUser = activeUsersDAO.containsSessionsOfUser(userSession.getLogin());
        activeUsersDAO.save(userSession);
        return !containsSessionsOfUser;
    }

    @Override
    public UserSession getBySessionId(String sessionId) {
        return activeUsersDAO.get(new UserSession(sessionId));
    }

    @Override
    public boolean sessionExists(String sessionId) {
        return activeUsersDAO.exists(new UserSession(sessionId));
    }

    @Override
    public boolean deleteUserSession(String sessionId, String login) {
        activeUsersDAO.deleteWithKey(new UserSession(sessionId, login));
        return !activeUsersDAO.containsSessionsOfUser(login);
    }
}
