package com.minagri.stats.core.jaxrs.control;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.Classes;

import java.io.IOException;

public class EnumerationCodeJsonDeserializer<E extends Enum<?>> extends com.fasterxml.jackson.databind.JsonDeserializer<E> {
    private Class<E> enumClass;

    public EnumerationCodeJsonDeserializer() {
        enumClass = Classes.getGenericTypeParameter(getClass(), EnumerationCodeJsonDeserializer.class);
    }

    @Override
    public E deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String codeValue = jsonParser.readValueAs(String.class);
        return Enums.getFromCode(codeValue, enumClass);
    }
}
