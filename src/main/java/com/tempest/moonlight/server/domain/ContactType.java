package com.tempest.moonlight.server.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-16.
 */
public enum ContactType implements HasValue<Integer> {
    USER(0), GROUP(1)
    ;

    public final int value;

    ContactType(int value) {
        this.value = value;
    }

    private static final Map<Integer, ContactType> MAP = new HashMap<>();

    static {
        for (ContactType contactType : values()) {
            MAP.put(contactType.value, contactType);
        }
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static ContactType getByValue(int value) {
        ContactType type = MAP.get(value);
        if(type == null) {
            throw new IllegalArgumentException("No appropriate type for value = " + value);
        }
        return type;
    }
}
