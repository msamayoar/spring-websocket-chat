package com.tempest.moonlight.server.domain.messages;

import com.tempest.moonlight.server.annotations.DTO;
import com.tempest.moonlight.server.domain.MessageKey;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.repository.dao.IdentifiedEntity;
import com.tempest.moonlight.server.services.dto.EntityDTO;
import com.tempest.moonlight.server.services.dto.messages.ChatMessageDTO;

import java.io.Serializable;

/**
 * Created by Yurii on 4/21/2015.
 */
@DTO(type = EntityDTO.DtoType.BiDir, dto = ChatMessageDTO.class)
public class ChatMessage implements Serializable, IdentifiedEntity<MessageKey> {

    private transient MessageKey key;

    private String from;
    private GenericParticipant recipient;

    private long time;
    private String uuid;
    private String packetId;

    private String subject;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage setUp(String from, String to, ParticipantType type, long time, String udid) {
        return setFrom(from).setTo(to).setType(type).setTime(time).setUuid(udid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage chatMessage = (ChatMessage) o;

        return getKey().equals(chatMessage.getKey());
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "key=" + key +
                ", from='" + from + '\'' +
                ", recipient=" + recipient +
                ", time=" + time +
                ", uuid='" + uuid + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public MessageKey getKey() {
        if(key == null) {
            key = new MessageKey(this);
        }
        return key;
    }

    public String getFrom() {
        return from;
    }

    public ChatMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public GenericParticipant getRecipient() {
        return recipient;
    }

    public ChatMessage setRecipient(GenericParticipant recipient) {
        this.recipient = recipient;
        return this;
    }

    private GenericParticipant checkGetRecipient() {
        if(recipient == null) {
            recipient = new GenericParticipant();
        }
        return recipient;
    }

    public ChatMessage setTo(String to) {
        checkGetRecipient().setSignature(to);
        return this;
    }

    public ChatMessage setType(ParticipantType type) {
        checkGetRecipient().setType(type);
        return this;
    }

    public long getTime() {
        return time;
    }

    public ChatMessage setTime(long time) {
        this.time = time;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public ChatMessage setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getPacketId() {
        return packetId;
    }

    public ChatMessage setPacketId(String packetId) {
        this.packetId = packetId;
        return this;
    }

    public String getSubject() {
        return subject == null ? "" : subject;
    }

    public ChatMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public ChatMessage setText(String text) {
        this.text = text;
        return this;
    }
}
