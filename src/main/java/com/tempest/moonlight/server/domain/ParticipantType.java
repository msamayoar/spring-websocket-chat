package com.tempest.moonlight.server.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-16.
 */
public enum ParticipantType implements HasValue<Integer> {
    USER(0), GROUP(1)
    ;

    public final int value;

    ParticipantType(int value) {
        this.value = value;
    }

    private static final Map<Integer, ParticipantType> MAP = new HashMap<>();

    static {
        for (ParticipantType participantType : values()) {
            MAP.put(participantType.value, participantType);
        }
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static ParticipantType getByValue(int value) {
        ParticipantType type = MAP.get(value);
        if(type == null) {
            throw new IllegalArgumentException("No appropriate type for value = " + value);
        }
        return type;
    }
}
