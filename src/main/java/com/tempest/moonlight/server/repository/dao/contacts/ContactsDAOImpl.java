package com.tempest.moonlight.server.repository.dao.contacts;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.repository.dao.AbstractMockDAO;
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
                entry -> {
                    GenericParticipant owner = entry.getKey().getOwner();
                    return owner.getType() == ParticipantType.USER && entry.getKey().getOwner().getSignature().equals(login);
                }
        );
    }
}
