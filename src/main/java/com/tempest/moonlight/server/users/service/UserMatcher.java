package com.tempest.moonlight.server.users.service;

import com.tempest.moonlight.server.domain.User;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-21.
 */
public interface UserMatcher {
    Collection<User> getMatching(String login);
}
