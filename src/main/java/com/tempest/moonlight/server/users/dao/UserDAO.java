package com.tempest.moonlight.server.users.dao;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.common.dao.DAO;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-08.
 */
public interface UserDAO extends DAO<User, String> {
    Collection<User> getLoginContains(String str);
}
