package com.tempest.moonlight.server.domain.messages;

/**
 * Created by Yurii on 2015-06-20.
 */
public class MessageDeliveryStatus {
    private String packetId;
    private String uuid;
    private int status;

    public MessageDeliveryStatus() {
    }

    public MessageDeliveryStatus(String packetId, String uuid, int status) {
        this.packetId = packetId;
        this.uuid = uuid;
        this.status = status;
    }

    public MessageDeliveryStatus(String packetId, String uuid, MessageStatus status) {
        this(packetId, uuid, status.getValue());
    }

    public MessageDeliveryStatus(ChatMessage chatMessage, MessageStatus status) {
        this(chatMessage.getPacketId(), chatMessage.getUuid(), status.getValue());
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
