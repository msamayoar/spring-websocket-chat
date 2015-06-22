package com.tempest.moonlight.server.repository.dao.groups;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.repository.dao.AbstractMockDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by Yurii on 2015-06-22.
 */
@Repository
public class GroupDAOMockImpl extends AbstractMockDAO<Group, String> implements GroupDAO {
}
