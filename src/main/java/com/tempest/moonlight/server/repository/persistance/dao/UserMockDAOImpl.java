package com.tempest.moonlight.server.repository.persistance.dao;

import com.tempest.moonlight.server.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Yurii on 2015-05-07.
 */
@Repository
public class UserMockDAOImpl extends AbstractMockDAO<User, String> implements UserDAO {
}
