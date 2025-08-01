package com.minagri.stats.core.jaxrs.control;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.Dates;
import com.minagri.stats.core.java.Strings;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;

public interface JsonObjects {
    static String getString(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? jsonObject.getString(property) : null;
    }

    static Integer getInteger(JsonObject jsonObject, String property) {
        if (!hasValue(jsonObject, property)) {
            return null;
        }
        JsonValue.ValueType valueType = jsonObject.getValue("/" + property).getValueType();
        if (valueType == JsonValue.ValueType.STRING) {
            return Strings.toInteger(jsonObject.getString(property));
        }
        return jsonObject.getInt(property);
    }

    static Long getLong(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? jsonObject.getJsonNumber(property).bigIntegerValue().longValue() : null;
    }

    static Boolean getBoolean(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? jsonObject.getBoolean(property) : null;
    }

    static BigDecimal getBigDecimal(JsonObject jsonObject, String property) {
        if (!hasValue(jsonObject, property)) {
            return null;
        }
        JsonValue.ValueType valueType = jsonObject.getValue("/" + property).getValueType();
        if (valueType == JsonValue.ValueType.NUMBER) {
            return jsonObject.getJsonNumber(property).bigDecimalValue();
        }
        if (valueType == JsonValue.ValueType.STRING) {
            return new BigDecimal(jsonObject.getString(property, "0"));
        }
        throw new RuntimeException("invalid value type for property " + property);
    }

    static LocalDate getLocalDate(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            String stringValue = jsonObject.getString(property);
            return Dates.parseDate(stringValue);
        }
        return null;
    }

    static LocalDateTime getLocalDateTime(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            String stringValue = jsonObject.getString(property);
            return Dates.parseDateTime(stringValue);
        }
        return null;
    }

    static LocalTime getLocalTime(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            String stringValue = jsonObject.getString(property);
            return Dates.parseTime(stringValue);
        }
        return null;
    }

    static YearMonth getYearMonth(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            String stringValue = jsonObject.getString(property);
            return YearMonth.parse(stringValue);
        }
        return null;
    }

    static <T extends Enum<?>> T getEnum(JsonObject jsonObject, String property, Class<T> enumClass) {
        if (hasValue(jsonObject, property)) {
            String value = jsonObject.getString(property);
            return Enums.getFromName(value, enumClass);
        }
        return null;
    }

    static <T extends Enum<?>> T getEnumFromCode(JsonObject jsonObject, String property, Class<T> enumClass) {
        if (hasValue(jsonObject, property)) {
            String value = jsonObject.getString(property);
            return Enums.getFromCode(value, enumClass);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    static <T extends Enum<?>> T getEnum(JsonObject jsonObject, String property, T... enumValues) {
        if (hasValue(jsonObject, property)) {
            String value = jsonObject.getString(property);
            return Arrays.stream(enumValues).filter(ec -> ec.name().equals(value)).findFirst().orElseThrow();
        }
        return null;
    }

    static UUID getUUID(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            String stringValue = jsonObject.getString(property);
            return UUID.fromString(stringValue);
        }
        return null;
    }

    static <T> List<T> getObjectList(JsonObject jsonObject, String property, Function<JsonObject, T> objectMapper) {
        return hasValue(jsonObject, property) ? JsonArrays.asObjectList(jsonObject.getJsonArray(property), objectMapper) : Collections.emptyList();
    }

    static List<Long> getLongList(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? JsonArrays.asLongList(jsonObject.getJsonArray(property)) : Collections.emptyList();
    }

    static List<String> getStringList(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? JsonArrays.asStringList(jsonObject.getJsonArray(property)) : Collections.emptyList();
    }

    static JsonObject getJsonObject(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? jsonObject.getJsonObject(property) : null;
    }

    static List<JsonObject> getJsonObjectList(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? JsonArrays.asJsonObjectList(jsonObject.getJsonArray(property)) : Collections.emptyList();
    }

    static <T extends Enum<?>> List<T> getEnumList(JsonObject jsonObject, String property, Class<T> enumClass) {
        return getEnumList(jsonObject, property, enumClass.getEnumConstants());
    }

    @SuppressWarnings("unchecked")
    static <T extends Enum<?>> List<T> getEnumList(JsonObject jsonObject, String property, T... enumValues) {
        return hasValue(jsonObject, property) ? JsonArrays.asEnumList(jsonObject.getJsonArray(property), enumValues) : Collections.emptyList();
    }

    static Map<String, String> getStringMap(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            Map<String, String> stringMap = new HashMap<>();
            JsonObject stringMapObject = jsonObject.getJsonObject(property);
            stringMapObject.keySet().forEach(key -> stringMap.put(key, stringMapObject.getString(key)));
            return stringMap;
        }
        return Collections.emptyMap();
    }

    static Map<String, Long> getLongMap(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            Map<String, Long> map = new HashMap<>();
            JsonObject mapObject = jsonObject.getJsonObject(property);
            mapObject.keySet().forEach(key -> map.put(key, mapObject.getJsonNumber(key).longValue()));
            return map;
        }
        return Collections.emptyMap();
    }

    static Map<String, Double> getDoubleMap(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            Map<String, Double> map = new HashMap<>();
            JsonObject mapObject = jsonObject.getJsonObject(property);
            mapObject.keySet().forEach(key -> map.put(key, mapObject.getJsonNumber(key).doubleValue()));
            return map;
        }
        return Collections.emptyMap();
    }

    static Map<String, Boolean> getBooleanMap(JsonObject jsonObject, String property) {
        if (hasValue(jsonObject, property)) {
            Map<String, Boolean> map = new HashMap<>();
            JsonObject mapObject = jsonObject.getJsonObject(property);
            mapObject.keySet().forEach(key -> map.put(key, mapObject.getBoolean(key)));
            return map;
        }
        return Collections.emptyMap();
    }

    static List<UUID> getUUIDList(JsonObject jsonObject, String property) {
        return hasValue(jsonObject, property) ? JsonArrays.asUUIDList(jsonObject.getJsonArray(property)) : Collections.emptyList();
    }

    static String toString(JsonObject jsonObject) {
        StringWriter stringWriter = new StringWriter();
        Json.createWriter(stringWriter).write(jsonObject);
        return stringWriter.toString();
    }

    static JsonObject fromString(String input) {
        JsonObject result = null;
        if (input != null) {
            try (StringReader reader = new StringReader(input)) {
                result = Json.createReader(reader).readObject();
            }
        }
        return result;
    }

    static <T> List<JsonObject> createJsonObjectList(Collection<T> objectList, Function<T, JsonObject> mapper) {
        List<JsonObject> jsonObjectList = new ArrayList<>();
        objectList.forEach(obj -> jsonObjectList.add(mapper.apply(obj)));
        return jsonObjectList;
    }

    static boolean hasValue(JsonObject jsonObject, String property) {
        return jsonObject.containsKey(property) && !JsonValue.ValueType.NULL.equals(jsonObject.getValue("/" + property).getValueType());
    }
}
