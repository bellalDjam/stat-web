package com.minagri.stats.core.jaxrs.control;

import com.minagri.stats.core.java.Strings;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface JsonObjectBuilders {

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, JsonValue value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, String value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Enum<?> value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value.name());
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, BigInteger value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, BigDecimal value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Integer value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Long value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Double value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Boolean value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value);
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, Date value) {
        if (value != null) {
            add(jsonObjectBuilder, property, value.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, LocalDate value) {
        if (value != null) {
            jsonObjectBuilder.add(property, DateTimeFormatter.ISO_LOCAL_DATE.format(value));
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, LocalDateTime value) {
        if (value != null) {
            jsonObjectBuilder.add(property, DateTimeFormatter.ISO_DATE_TIME.format(value));
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, LocalTime value) {
        if (value != null) {
            jsonObjectBuilder.add(property, DateTimeFormatter.ISO_TIME.format(value));
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, YearMonth value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value.toString());
        }
    }

    static void add(JsonObjectBuilder jsonObjectBuilder, String property, UUID value) {
        if (value != null) {
            jsonObjectBuilder.add(property, value.toString());
        }
    }

    static void addStringArray(JsonObjectBuilder jsonObjectBuilder, String property, Collection<String> values) {
        if (values != null) {
            jsonObjectBuilder.add(property, JsonArrays.createStringArray(values));
        }
    }

    static <T> void addStringArray(JsonObjectBuilder jsonObjectBuilder, String property, Collection<T> collection, Function<T, String> valueMapper) {
        if (collection != null) {
            jsonObjectBuilder.add(property, JsonArrays.createStringArray(collection, valueMapper));
        }
    }

    static void addLongArray(JsonObjectBuilder jsonObjectBuilder, String property, Collection<Long> values) {
        if (values != null) {
            jsonObjectBuilder.add(property, JsonArrays.createLongArray(values));
        }
    }

    static <T> void addLongArray(JsonObjectBuilder jsonObjectBuilder, String property, Collection<T> collection, Function<T, Long> valueMapper) {
        if (collection != null) {
            jsonObjectBuilder.add(property, JsonArrays.createLongArray(collection, valueMapper));
        }
    }

    static <T> void addObject(JsonObjectBuilder jsonObjectBuilder, String property, T object, Function<T, JsonObject> objectMapper) {
        if (object != null) {
            jsonObjectBuilder.add(property, objectMapper.apply(object));
        }
    }

    static <T> void addObjectArray(JsonObjectBuilder jsonObjectBuilder, String property, Collection<T> collection, Function<T, JsonObject> objectMapper) {
        if (collection != null) {
            jsonObjectBuilder.add(property, JsonArrays.createObjectArray(collection, objectMapper));
        }
    }

    static <T> void addEmptyArray(JsonObjectBuilder jsonObjectBuilder, String property) {
        jsonObjectBuilder.add(property, Json.createArrayBuilder());
    }

    static void addStringMap(JsonObjectBuilder jsonObjectBuilder, String property, Map<String, ?> map) {
        if (map != null) {
            addObjectArray(jsonObjectBuilder, property, map.keySet(), key -> {
                String value = Strings.nullToEmpty(map.get(key));

                JsonObjectBuilder valueBuilder = Json.createObjectBuilder();
                valueBuilder.add("key", key);
                valueBuilder.add("value", value);
                return valueBuilder.build();
            });
        }
    }

    static void addLongMap(JsonObjectBuilder jsonObjectBuilder, String property, Map<String, Long> map) {
        if (map != null) {
            JsonObjectBuilder mapBuilder = Json.createObjectBuilder();
            map.forEach(mapBuilder::add);
            jsonObjectBuilder.add(property, mapBuilder);
        }
    }

    static void addDoubleMap(JsonObjectBuilder jsonObjectBuilder, String property, Map<String, Double> map) {
        if (map != null) {
            JsonObjectBuilder mapBuilder = Json.createObjectBuilder();
            map.forEach(mapBuilder::add);
            jsonObjectBuilder.add(property, mapBuilder);
        }
    }

    static void addBooleanMap(JsonObjectBuilder jsonObjectBuilder, String property, Map<String, Boolean> map) {
        if (map != null) {
            JsonObjectBuilder mapBuilder = Json.createObjectBuilder();
            map.forEach(mapBuilder::add);
            jsonObjectBuilder.add(property, mapBuilder);
        }
    }

    static <T> void addLongProperty(JsonObjectBuilder jsonObjectBuilder, String property, T object, Function<T, Long> valueMapper) {
        if (object != null) {
            add(jsonObjectBuilder, property, valueMapper.apply(object));
        }
    }

    static <T> void addStringProperty(JsonObjectBuilder jsonObjectBuilder, String property, T object, Function<T, String> valueMapper) {
        if (object != null) {
            add(jsonObjectBuilder, property, valueMapper.apply(object));
        }
    }

    static <T> void addUUIDProperty(JsonObjectBuilder jsonObjectBuilder, String property, T object, Function<T, UUID> valueMapper) {
        if (object != null) {
            add(jsonObjectBuilder, property, valueMapper.apply(object));
        }
    }
}
