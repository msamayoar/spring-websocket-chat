package com.tempest.moonlight.server.domain;

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

    @Override
    public String toString() {
        return "MessageKey{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", time=" + time +
                ", udid='" + udid + '\'' +
                '}';
    }

    public MessageKey(String from, String to, ParticipantType type, long time, String udid) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.time = time;
        this.udid = udid;
    }

    public MessageKey(ChatMessage chatMessage) {
        this(chatMessage.getFrom(), chatMessage.getTo(), chatMessage.getType(), chatMessage.getTime(), chatMessage.getUuid());
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
        return udid.equals(that.udid);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + udid.hashCode();
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
