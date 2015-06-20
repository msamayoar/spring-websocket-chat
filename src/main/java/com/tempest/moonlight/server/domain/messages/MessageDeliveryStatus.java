package com.tempest.moonlight.server.domain.messages;

import com.tempest.moonlight.server.domain.MessageKey;

/**
 * Created by Yurii on 2015-06-20.
 */
public class MessageDeliveryStatus {
    private String from;
    private String to;
    private int recipientType;

    private String packetId;
    private String uuid;

    private int status;

    public MessageDeliveryStatus() {
    }

    public MessageDeliveryStatus(String from, String to, int recipientType, String packetId, String uuid, int status) {
        this.from = from;
        this.recipientType = recipientType;
        this.to = to;
        this.packetId = packetId;
        this.uuid = uuid;
        this.status = status;
    }

    public MessageDeliveryStatus(String from, int recipientType, String to, String packetId, String uuid, MessageStatus status) {
        this(from, to, recipientType, packetId, uuid, status.getValue());
    }

    public MessageDeliveryStatus(ChatMessage message, MessageStatus status) {
        this(
                message.getFrom(),
                message.getRecipient().getSignature(), message.getRecipient().getType().getValue(),
                message.getPacketId(), message.getUuid(),
                status.getValue()
        );
    }

    @Override
    public String toString() {
        return "MessageDeliveryStatus{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", recipientType=" + recipientType +
                ", packetId='" + packetId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status=" + status +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public MessageDeliveryStatus setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MessageDeliveryStatus setTo(String to) {
        this.to = to;
        return this;
    }

    public int getRecipientType() {
        return recipientType;
    }

    public MessageDeliveryStatus setRecipientType(int recipientType) {
        this.recipientType = recipientType;
        return this;
    }

    public String getPacketId() {
        return packetId;
    }

    public MessageDeliveryStatus setPacketId(String packetId) {
        this.packetId = packetId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public MessageDeliveryStatus setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MessageDeliveryStatus setStatus(int status) {
        this.status = status;
        return this;
    }
}
