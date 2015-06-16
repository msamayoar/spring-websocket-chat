package com.tempest.moonlight.server.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tempest.moonlight.server.domain.GenericContact;

import java.io.IOException;

/**
 * Created by Yurii on 2015-06-16.
 */
public class GenericContactJsonSerializer extends JsonSerializer<GenericContact> {
    @Override
    public void serialize(GenericContact value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("type", value.getType().getValue());
        jgen.writeStringField("id", value.getId());
        jgen.writeEndObject();
    }
}
