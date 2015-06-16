package com.tempest.moonlight.server.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tempest.moonlight.server.event.UserSession;

import java.io.IOException;

/**
 * Created by Yurii on 2015-06-11.
 */
public class UserSessionMessageSerializer extends JsonSerializer<UserSession> {
    @Override
    public void serialize(UserSession value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("login", value.getLogin());
        jgen.writeEndObject();
    }
}
