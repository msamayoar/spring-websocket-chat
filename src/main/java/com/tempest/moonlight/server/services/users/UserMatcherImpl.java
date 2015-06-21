package com.tempest.moonlight.server.services.users;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.repository.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-21.
 */
@Service
public class UserMatcherImpl implements UserMatcher {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Collection<User> getMatching(String login) {
        return userDAO.getLoginContains(login);
    }
}
