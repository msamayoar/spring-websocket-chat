package com.tempest.moonlight.server.domain;

import com.tempest.moonlight.server.domain.messages.ChatMessage;
import com.tempest.moonlight.server.domain.messages.MessageDeliveryStatus;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-05-08.
 */
public class MessageKey implements Serializable {
    public final String from;
    public final String to;
    public final ParticipantType type;
    public final String udid;
    public final String packetId;

    @Override
    public String toString() {
        return "{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", udid='" + udid + '\'' +
                ", packetId='" + packetId + '\'' +
                '}';
    }

    public MessageKey(String from, String to, ParticipantType type, String udid, String packetId) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.udid = udid;
        this.packetId = packetId;
    }

    public MessageKey(String from, String to, int participantType, String udid, String packetId) {
        this.from = from;
        this.to = to;
        this.type = ParticipantType.getByValue(participantType);
        this.udid = udid;
        this.packetId = packetId;
    }

    public static MessageKey fromChatMessage(ChatMessage chatMessage) {
        return new MessageKey(
                chatMessage.getFrom(),
                chatMessage.getRecipient().getSignature(), chatMessage.getRecipient().getType(),
                chatMessage.getUuid(), chatMessage.getPacketId()
        );
    }

    public static MessageKey fromDeliveryStatus(MessageDeliveryStatus deliveryStatus) {
        return new MessageKey(
                deliveryStatus.getFrom(),
                deliveryStatus.getTo(),
                deliveryStatus.getRecipientType(),
                deliveryStatus.getUuid(),
                deliveryStatus.getPacketId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageKey that = (MessageKey) o;

        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;
        if (type != that.type) return false;
        if (!udid.equals(that.udid)) return false;
        return packetId.equals(that.packetId);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + udid.hashCode();
        result = 31 * result + packetId.hashCode();
        return result;
    }
}
