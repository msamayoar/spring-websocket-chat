package com.tempest.moonlight.server.repository.persistence.dao;

import com.tempest.moonlight.server.event.UserSession;
import com.tempest.moonlight.server.util.StreamUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Yurii on 2015-06-10.
 */
@Repository
public class ActiveUsersDAOImpl extends AbstractMockDAO<UserSession, UserSession> implements ActiveUsersDAO {
    @Override
    public Collection<UserSession> getActiveSessions() {
        return getMap().values();
    }

    @Override
    public Collection<String> getActiveUsers() {
        return StreamUtils.convert(getActiveSessions(), UserSession::getLogin);
    }


    @Override
    public boolean containsSessionsOfUser(String login) {
        return StreamUtils.mapKeysContain(
                getMap(),
                entry -> entry.getKey().getLogin().equals(login)
        );
    }
}
