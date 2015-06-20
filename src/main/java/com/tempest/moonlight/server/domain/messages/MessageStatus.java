package com.tempest.moonlight.server.domain.messages;

import com.tempest.moonlight.server.domain.HasIntValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-20.
 */
public enum MessageStatus implements HasIntValue {
    ARRIVED(0),
    DELIVERED(1),
    READ(2)
    ;
    public final int value;

    MessageStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    private static final Map<Integer, MessageStatus> MAP = new HashMap<>();

    static {
        for (MessageStatus status : values()) {
            MAP.put(status.getValue(), status);
        }
    }

    public static MessageStatus getByValue(int value) {
        return MAP.get(value);
    }
}
