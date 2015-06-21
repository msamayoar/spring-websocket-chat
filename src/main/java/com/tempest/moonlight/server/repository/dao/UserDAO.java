package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.User;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface UserDAO extends DAO<User, String> {
    Collection<User> getLoginContains(String str);
}
