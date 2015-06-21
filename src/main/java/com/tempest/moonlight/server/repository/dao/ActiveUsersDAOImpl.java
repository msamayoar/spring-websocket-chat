package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.event.UserSession;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Yurii on 2015-06-10.
 */
@Repository
public class ActiveUsersDAOImpl extends AbstractMockDAO<UserSession, UserSession> implements ActiveUsersDAO {
//    @Override
//    public Collection<UserSession> getActiveSessions() {
//        return getMap().values();
//    }

//    @Override
//    public Collection<String> getActiveUsers() {
//        return CollectionsUtils.convertToSet(getActiveSessions(), UserSession::getLogin);
//    }


    @Override
    public Map<String, Boolean> areUsersActive(Collection<String> logins) {
        return logins.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        this::containsSessionsOfUser
                )
        );
    }

    @Override
    public boolean containsSessionsOfUser(String login) {
        return CollectionsUtils.mapKeysContain(
                getMap(),
                entry -> entry.getKey().getLogin().equals(login)
        );
    }
}
