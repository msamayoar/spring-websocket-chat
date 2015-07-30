package com.tempest.moonlight.server.common.dto;

import org.springframework.messaging.Message;

import java.util.Map;

/**
 * Created by Yurii on 2015-07-30.
 */
public interface ToMessageConvertedEntity<T> {
//    Message<T> toMessage();
    Map<String, Object> getHeaders();
    T getPayload();
}
