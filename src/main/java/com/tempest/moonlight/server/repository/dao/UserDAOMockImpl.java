package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Yurii on 2015-05-07.
 */
@Repository
public class UserDAOMockImpl extends AbstractMockDAO<User, String> implements UserDAO {
}