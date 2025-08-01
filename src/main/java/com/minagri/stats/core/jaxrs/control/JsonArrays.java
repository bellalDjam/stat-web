package com.minagri.stats.core.jaxrs.control;

import jakarta.json.*;

import java.io.StringReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface JsonArrays {

    static JsonArray readArray(String jsonValue) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonValue))) {
            return reader.readArray();
        }
    }

    static List<Long> asLongList(JsonArray jsonArray) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }

        List<Long> longValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            longValues.add(jsonArray.getJsonNumber(i).longValue());
        }
        return longValues;
    }

    static List<String> asStringList(JsonArray jsonArray) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }

        List<String> stringValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            stringValues.add(jsonArray.getString(i));
        }
        return stringValues;
    }

    static List<UUID> asUUIDList(JsonArray jsonArray) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }

        List<UUID> uuidValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            uuidValues.add(UUID.fromString(jsonArray.getString(i)));
        }
        return uuidValues;
    }

    static <T extends Enum<?>> List<T> asEnumList(JsonArray jsonArray, Class<T> enumClass) {
        return asEnumList(jsonArray, enumClass.getEnumConstants());
    }

    @SafeVarargs
    static <T extends Enum<?>> List<T> asEnumList(JsonArray jsonArray, T... enumValues) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }

        List<T> enumList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String enumString = jsonArray.getString(i);
            enumList.add(Arrays.stream(enumValues)
                    .filter(ec -> ec.name().equals(enumString))
                    .findFirst()
                    .orElseThrow(RuntimeException::new));
        }
        return enumList;
    }

    static <T> List<T> asObjectList(JsonArray jsonArray, Function<JsonObject, T> valueMapper) {
        return asJsonObjectList(jsonArray).stream().map(valueMapper).collect(Collectors.toList());
    }

    static List<JsonObject> asJsonObjectList(JsonArray jsonArray) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }

        List<JsonObject> objectValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            objectValues.add(jsonArray.getJsonObject(i));
        }
        return objectValues;
    }

    static JsonArray createStringArray(Collection<String> values) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        values.forEach(jsonArrayBuilder::add);
        return jsonArrayBuilder.build();
    }

    static <T> JsonArray createStringArray(Collection<T> collection, Function<T, String> valueMapper) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        collection.forEach(obj -> jsonArrayBuilder.add(valueMapper.apply(obj)));
        return jsonArrayBuilder.build();
    }

    static JsonArray createLongArray(Collection<Long> longList) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        longList.forEach(jsonArrayBuilder::add);
        return jsonArrayBuilder.build();
    }

    static <T> JsonArray createLongArray(Collection<T> objectList, Function<T, Long> mapper) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        objectList.forEach(obj -> jsonArrayBuilder.add(mapper.apply(obj)));
        return jsonArrayBuilder.build();
    }

    static JsonArray createUUIDArray(Collection<UUID> uuidList) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        uuidList.forEach(uuid -> jsonArrayBuilder.add(uuid.toString()));
        return jsonArrayBuilder.build();
    }

    static <T> JsonArray createUUIDArray(Collection<T> objectList, Function<T, UUID> mapper) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        objectList.forEach(obj -> jsonArrayBuilder.add(mapper.apply(obj).toString()));
        return jsonArrayBuilder.build();
    }

    static <T> JsonArray createObjectArray(Collection<T> objectList, Function<T, JsonObject> mapper) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        objectList.forEach(obj -> jsonArrayBuilder.add(mapper.apply(obj)));
        return jsonArrayBuilder.build();
    }

    static JsonArray createJsonObjectArray(Collection<JsonObject> objectList) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        objectList.forEach(jsonArrayBuilder::add);
        return jsonArrayBuilder.build();
    }

    static JsonArray getAsJsonArray(String input) {
        JsonArray result = null;
        if (input != null) {
            try (StringReader reader = new StringReader(input)) {
                result = Json.createReader(reader).readArray();
            }
        }
        return result;
    }
}
