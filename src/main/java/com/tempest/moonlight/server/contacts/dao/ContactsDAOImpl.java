package com.tempest.moonlight.server.contacts.dao;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.common.dao.AbstractMockDAO;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Yurii on 2015-06-18.
 */
@Repository
public class ContactsDAOImpl extends AbstractMockDAO<GenericContact, GenericContact> implements ContactsDAO {

    @Override
    public Collection<GenericContact> getContactsOfUser(String login) {
        /*
        return CollectionsUtils.filterMapValues(
                getMap(),
                entry -> {
                    GenericParticipant owner = entry.getKey().getOwner();
                    return owner.getType() == ParticipantType.USER && entry.getKey().getOwner().getSignature().equals(login);
                }
        );
        */

        return filterContactsByOwner(new GenericParticipant(ParticipantType.USER, login));
    }

    private static Predicate<Map.Entry<GenericContact, GenericContact>> getParticipantSignaturePredicate(String signature) {
        return entry -> entry.getKey().getOwner().getSignature().equals(signature);
    }

    private static Predicate<Map.Entry<GenericContact, GenericContact>> getParticipantTypePredicate(ParticipantType type) {
        return entry -> entry.getKey().getOwner().getType() == type;
    }

    public Collection<GenericContact> filterContactsByOwner(GenericParticipant owner) {
        return CollectionsUtils.filterMapValues(
                getMap(),
                getContactOwnerParticipantPredicate(owner)
        );
    }

    public static Predicate<Map.Entry<GenericContact, GenericContact>> getContactOwnerParticipantPredicate(GenericParticipant owner) {
        return entry -> entry.getKey().getOwner().equals(owner);
    }

    @Override
    public Collection<GenericContact> getGroupParticipants(String groupSignature) {
        return filterContactsByOwner(new GenericParticipant(ParticipantType.GROUP, groupSignature));
    }

    @Override
    public boolean isUserGroupParticipant(String login, String groupSignature) {
        return getMap().containsKey(
                new GenericContact(
                        new GenericParticipant(ParticipantType.GROUP, groupSignature),
                        new GenericParticipant(ParticipantType.USER, login)
                )
        );
    }

    @Override
    public void addRemoveGroupParticipants(String groupSignature, Collection<GenericParticipant> participants, boolean add) {
        GenericParticipant groupAsOwner = new GenericParticipant(ParticipantType.GROUP, groupSignature);
        Consumer<GenericParticipant> consumer = add ?
                participant -> ContactsDAOImpl.this.save(
                        new GenericContact(
                                groupAsOwner,
                                new GenericParticipant(
                                        ParticipantType.USER,
                                        participant.getSignature()
                                )
                        )
                )
                : participant -> ContactsDAOImpl.this.delete(
                new GenericContact(
                        groupAsOwner,
                        new GenericParticipant(
                                ParticipantType.USER,
                                participant.getSignature()
                        )
                )
        );

        participants.forEach(consumer::accept);
    }

    @Override
    public void addGroupParticipants(String groupSignature, Collection<GenericParticipant> participants) {

    }
}
