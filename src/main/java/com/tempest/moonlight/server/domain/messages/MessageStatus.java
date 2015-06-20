package com.tempest.moonlight.server.domain.messages;

import com.tempest.moonlight.server.domain.HasIntValue;

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
}
