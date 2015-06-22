package com.tempest.moonlight.server.services.users;

import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.event.UserSession;
import com.tempest.moonlight.server.repository.dao.users.ActiveUsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public Collection<GenericParticipant> getActive(Collection<GenericParticipant> participants) {
//        Map<String, Boolean> usersActive = activeUsersDAO.areUsersActive(
//                participants.stream().filter(
//                        participant -> participant.getType() == ParticipantType.USER
//                ).map(GenericParticipant::getSignature).collect(Collectors.toSet())
//        );
//
//        return participants.stream().filter(
//                participant -> usersActive.computeIfAbsent(
//                        participant.getSignature(),
//                        signature -> false
//                )
//        ).collect(Collectors.toSet());


        return participants.stream().filter(
                participant ->
                        activeUsersDAO.containsSessionsOfUser(participant.getSignature())
        ).collect(Collectors.toSet());
    }

//    @Override
//    public UserSession getBySessionId(String sessionId) {
//        return activeUsersDAO.get(new UserSession(sessionId));
//    }

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
