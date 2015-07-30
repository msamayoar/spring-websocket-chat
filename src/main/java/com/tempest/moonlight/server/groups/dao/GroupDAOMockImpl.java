package com.tempest.moonlight.server.groups.dao;

import com.tempest.moonlight.server.domain.Group;
import com.tempest.moonlight.server.common.dao.AbstractMockDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by Yurii on 2015-06-22.
 */
@Repository
public class GroupDAOMockImpl extends AbstractMockDAO<Group, String> implements GroupDAO {
}
