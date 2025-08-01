package com.minagri.stats.core.jaxrs.control;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.minagri.stats.core.enumeration.control.Enums;

import java.io.IOException;

public class EnumerationCodeJsonSerializer<E extends Enum<?>> extends com.fasterxml.jackson.databind.JsonSerializer<E> {
    @Override
    public void serialize(E enumValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (enumValue != null) {
            jsonGenerator.writeString(Enums.getCode(enumValue));
        }
    }
}
