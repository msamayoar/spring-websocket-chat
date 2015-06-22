package com.tempest.moonlight.server.services.users;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface UserService extends UserDetailsService {
    boolean checkUserExists(String login);
    boolean checkUserPassword(String login, String password);
    User registerUser(String login, String password) throws UserAlreadyExistsException;
    Collection<GenericParticipant> getMatching(String login);
}
