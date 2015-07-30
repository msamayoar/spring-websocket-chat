package com.tempest.moonlight.server.messages.dao;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.MessageKey;
import com.tempest.moonlight.server.common.dao.AbstractMockDAO;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by Yurii on 2015-05-08.
 */
@Repository
public class MessageDAOMockImpl extends AbstractMockDAO<ChatMessage, MessageKey> implements MessageDAO {
    @Override
    public Collection<ChatMessage> getMessagesOfUser(String login) {
        return CollectionsUtils.filterMapValuesByKey(getMap(), messagesOfUserPredicate(login));
    }

    private static Predicate<MessageKey> messagesOfUserPredicate(String user) {
        return messagesOfParticipantPredicate(new GenericParticipant(ParticipantType.USER, user));
    }

    private static Predicate<MessageKey> messagesOfParticipantPredicate(GenericParticipant participant) {
        return key -> key.type == participant.getType() && (participant.getSignature().equals(key.from) || participant.getSignature().equals(key.to));
    }

    private static Predicate<MessageKey> messagesToParticipantPredicate(GenericParticipant participant) {
        return key -> key.type == participant.getType() && participant.getSignature().equals(key.to);
    }

    private static Predicate<MessageKey> messagesBetweenPredicate(String user, GenericParticipant companion) {
        return messagesOfUserPredicate(user).and(messagesOfParticipantPredicate(companion));
    }

    @Override
    public Collection<ChatMessage> getMessagesBetween(String user, GenericParticipant companion) {
        return CollectionsUtils.filterMapValuesByKey(getMap(), messagesBetweenPredicate(user, companion));
    }

    @Override
    public Collection<ChatMessage> getMessagesTo(GenericParticipant genericParticipant) {
        return CollectionsUtils.filterMapValuesByKey(getMap(), messagesToParticipantPredicate(genericParticipant));
    }
}
