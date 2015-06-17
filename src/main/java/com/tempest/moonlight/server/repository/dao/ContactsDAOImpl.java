package com.tempest.moonlight.server.repository.dao;

import com.tempest.moonlight.server.domain.ContactType;
import com.tempest.moonlight.server.domain.GenericContact;
import com.tempest.moonlight.server.util.StreamUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-18.
 */
@Repository
public class ContactsDAOImpl extends AbstractMockDAO<GenericContact, GenericContact> implements ContactsDAO {

    @Override
    public Collection<GenericContact> getContactsOfUser(String login) {
        return StreamUtils.filterMapValues(
                getMap(),
                entry -> entry.getKey().getOwnerType() == ContactType.USER && entry.getKey().getOwner().equals(login)
        );
    }
}
