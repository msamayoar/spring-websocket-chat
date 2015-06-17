package com.tempest.moonlight.server.services;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.exceptions.UserAlreadyExistsException;
import com.tempest.moonlight.server.repository.dao.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yurii on 2015-05-08.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDAO userDAO;

    private MessageDigest messageDigestInstance;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public boolean checkUserExists(String login) {
        return userDAO.existsWithKey(login);
    }

    @Override
    public boolean checkUserPassword(String login, String password) {
        if(!StringUtils.hasText(login) || !StringUtils.hasText(password)) {
            return false;
        }

        User user = userDAO.get(login);
        if(user == null) {
            return false;
        }

        return user.getPassword().equals(encodePassword(password));
    }

    @Override
    public User registerUser(String login, String password) throws UserAlreadyExistsException{
        if (checkUserExists(login)) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(login, encodePassword(password));
        userDAO.save(user);
        return user;
    }

    private static String encodePassword(String password) {
        String passwordEncoded = password;
        try {
            passwordEncoded = new String(
                    Base64.encode(
                            MessageDigest.getInstance("MD5").digest(
                                    password.getBytes()
                            )
                    )
            );
        } catch (NoSuchAlgorithmException e) {
            logger.error("error during encoding password", e);
        }
        return passwordEncoded;
    }
}
