package com.tempest.moonlight.server.domain;

import com.tempest.moonlight.server.domain.messages.ChatMessage;

import java.io.Serializable;

/**
 * Created by Yurii on 2015-05-08.
 */
public class MessageKey implements Serializable {
    public final String from;
    public final String to;
    public final ParticipantType type;
    public final long time;
    public final String udid;
    public final String packetId;

    @Override
    public String toString() {
        return "MessageKey{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", time=" + time +
                ", udid='" + udid + '\'' +
                ", packetId='" + packetId + '\'' +
                '}';
    }

    public MessageKey(String from, String to, ParticipantType type, long time, String udid, String packetId) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.time = time;
        this.udid = udid;
        this.packetId = packetId;
    }

    public MessageKey(ChatMessage chatMessage) {
        this(chatMessage.getFrom(), chatMessage.getRecipient().getSignature(), chatMessage.getRecipient().getType(), chatMessage.getTime(), chatMessage.getUuid(), chatMessage.getPacketId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageKey that = (MessageKey) o;

        if (time != that.time) return false;
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
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + udid.hashCode();
        result = 31 * result + packetId.hashCode();
        return result;
    }

    /*public static class Builder {
        private String from;
        private String to;
        private long time;
        private String id;

        public Builder() {}

        public Builder(String from, String to, long time, String id) {
            this.from = from;
            this.to = to;
            this.time = time;
            this.id = id;
        }

        public MessageKey build() {
            return new MessageKey(from, to, time, id);
        }

        public String getFrom() {
            return from;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public String getTo() {
            return to;
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public long getTime() {
            return time;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public String getId() {
            return id;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }
    }*/
}
