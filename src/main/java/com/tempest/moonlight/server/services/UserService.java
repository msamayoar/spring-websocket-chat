package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.exceptions.UserAlreadyExistsException;
import com.tempest.moonlight.server.services.dto.RegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface UserService extends UserDetailsService {
    boolean checkUserExists(String login);
    boolean checkUserPassword(String login, String password);
    User registerUser(String login, String password) throws UserAlreadyExistsException;
}
