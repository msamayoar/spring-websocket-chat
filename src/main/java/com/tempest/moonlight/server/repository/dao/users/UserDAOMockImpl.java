package com.tempest.moonlight.server.repository.dao.users;

import com.tempest.moonlight.server.domain.User;
import com.tempest.moonlight.server.repository.dao.AbstractMockDAO;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Yurii on 2015-05-07.
 */
@Repository
public class UserDAOMockImpl extends AbstractMockDAO<User, String> implements UserDAO {
    @Override
    public Collection<User> getLoginContains(String str) {
        String strLC = str.toLowerCase();
        return CollectionsUtils.filterMapValuesByKey(
                getMap(),
                key -> key.toLowerCase().contains(strLC)
        );
    }
}
