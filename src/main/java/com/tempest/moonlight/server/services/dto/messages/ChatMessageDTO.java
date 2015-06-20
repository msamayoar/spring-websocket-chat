package com.tempest.moonlight.server.services.dto.messages;

import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.services.dto.BiDirectionalDTO;

/**
 * Created by Yurii on 2015-06-20.
 */
public class ChatMessageDTO implements BiDirectionalDTO<ChatMessage> {

    private String from;
    private int recipientType;
    private String recipient;

    private long time;
    private String uuid;
    private String packetId;

    private String subject;
    private String text;

    @Override
    public void fillEntity(ChatMessage chatMessage) {
        chatMessage.setFrom(from);
        chatMessage.setTo(recipient);
        chatMessage.setType(ParticipantType.getByValue(recipientType));

        chatMessage.setPacketId(packetId);

        chatMessage.setSubject(subject);
        chatMessage.setText(text);
    }

    @Override
    public void fillWithEntity(ChatMessage chatMessage) {
        from = chatMessage.getFrom();
        recipientType = chatMessage.getRecipient().getType().getValue();
        recipient = chatMessage.getRecipient().getSignature();

        time = chatMessage.getTime();
        uuid = chatMessage.getUuid();
        packetId = chatMessage.getPacketId();

        subject = chatMessage.getSubject();
        text = chatMessage.getText();
    }

    public String getFrom() {
        return from;
    }

    public ChatMessageDTO setFrom(String from) {
        this.from = from;
        return this;
    }

    public int getRecipientType() {
        return recipientType;
    }

    public ChatMessageDTO setRecipientType(int recipientType) {
        this.recipientType = recipientType;
        return this;
    }

    public String getRecipient() {
        return recipient;
    }

    public ChatMessageDTO setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public long getTime() {
        return time;
    }

    public ChatMessageDTO setTime(long time) {
        this.time = time;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public ChatMessageDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getPacketId() {
        return packetId;
    }

    public ChatMessageDTO setPacketId(String packetId) {
        this.packetId = packetId;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ChatMessageDTO setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public ChatMessageDTO setText(String text) {
        this.text = text;
        return this;
    }
}
