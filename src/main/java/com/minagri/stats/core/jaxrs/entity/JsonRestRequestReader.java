package com.minagri.stats.core.jaxrs.entity;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.core.jaxrs.control.JsonObjects;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class JsonRestRequestReader {
    protected JsonObject request;

    public JsonRestRequestReader(JsonObject request) {
        this.request = request;
    }

    public JsonRestRequestReader(String request) {
        this.request = JsonObjects.fromString(request);
    }

    public String getRequiredString(String property) {
        requireProperty(property);
        return getOptionalString(property);
    }

    public String getRequiredString(String property, String... validValues) {
        String value = getRequiredString(property);
        if (!Arrays.asList(validValues).contains(value)) {
            throw new BadRequestException("property " + property + " should be in " + Arrays.toString(validValues));
        }
        return value;
    }

    public String getOptionalString(String property) {
        String result = convertProperty(property, JsonObjects::getString);
        return Strings.isBlank(result) ? null : result;
    }

    public Boolean getRequiredBoolean(String property) {
        requireProperty(property);
        return getOptionalBoolean(property);
    }

    public Boolean getOptionalBoolean(String property) {
        return convertProperty(property, JsonObjects::getBoolean);
    }

    public BigDecimal getRequiredBigDecimal(String property) {
        requireProperty(property);
        return getOptionalBigDecimal(property);
    }

    public BigDecimal getOptionalBigDecimal(String property) {
        return convertProperty(property, JsonObjects::getBigDecimal);
    }

    public Integer getRequiredInteger(String property) {
        requireProperty(property);
        return getOptionalInteger(property);
    }

    public Integer getOptionalInteger(String property) {
        return convertProperty(property, JsonObjects::getInteger);
    }

    public Integer getRequiredIntegerInRange(String property, Integer lower, Integer upper) {
        requireProperty(property);
        Integer integerProperty = getOptionalInteger(property);
        if (integerProperty == null || integerProperty < lower || integerProperty > upper) {
            throw new BadRequestException("property " + property + " should be between " + lower + " and " + upper);
        }
        return integerProperty;
    }

    public Long getOptionalLong(String property) {
        return convertProperty(property, JsonObjects::getLong);
    }

    public Long getRequiredLong(String property) {
        requireProperty(property);
        return getOptionalLong(property);
    }

    public LocalDate getRequiredLocalDate(String property) {
        requireProperty(property);
        return getOptionalLocalDate(property);
    }

    public LocalDate getOptionalLocalDate(String property) {
        return convertProperty(property, JsonObjects::getLocalDate);
    }

    public LocalDateTime getRequiredLocalDateTime(String property) {
        requireProperty(property);
        return getOptionalLocalDateTime(property);
    }

    public LocalDateTime getOptionalLocalDateTime(String property) {
        return convertProperty(property, JsonObjects::getLocalDateTime);
    }

    public Map<String, String> getRequiredStringMap(String property) {
        requireProperty(property);
        return getOptionalStringMap(property);
    }

    public YearMonth getRequiredYearMonth(String property) {
        requireProperty(property);
        return getOptionalYearMonth(property);
    }

    public YearMonth getOptionalYearMonth(String property) {
        return convertProperty(property, JsonObjects::getYearMonth);
    }

    public Map<String, String> getOptionalStringMap(String property) {
        return convertProperty(property, JsonObjects::getStringMap);
    }

    public List<String> getRequiredStringList(String property) {
        requireProperty(property);
        return getOptionalStringList(property);
    }

    public List<String> getOptionalStringList(String property) {
        return convertProperty(property, JsonObjects::getStringList);
    }

    public UUID getRequiredUUID(String property) {
        requireProperty(property);
        return getOptionalUUID(property);
    }

    public UUID getOptionalUUID(String property) {
        return convertProperty(property, JsonObjects::getUUID);
    }

    public List<UUID> getOptionalUUIDList(String property) {
        try {
            return JsonObjects.getUUIDList(request, property);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public <T> List<T> getRequiredObjectList(String property, Function<JsonObject, T> objectMapper) {
        requireProperty(property);
        return getOptionalObjectList(property, objectMapper);
    }

    public <T> List<T> getOptionalObjectList(String property, Function<JsonObject, T> objectMapper) {
        try {
            return JsonObjects.getObjectList(request, property, objectMapper);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public List<JsonObject> getRequiredObjectList(String property) {
        return getRequiredObjectList(property, o -> o);
    }

    public List<JsonObject> getOptionalObjectList(String property) {
        return getOptionalObjectList(property, o -> o);
    }

    public void consumeRequiredObjectList(String property, Consumer<JsonObject> consumer) {
        getRequiredObjectList(property).forEach(consumer);
    }

    public void consumeOptionalObjectList(String property, Consumer<JsonObject> consumer) {
        getOptionalObjectList(property).forEach(consumer);
    }

    public <T> T getOptionalObject(String property, Function<JsonObject, T> objectMapper) {
        try {
            JsonObject jsonObject = JsonObjects.getJsonObject(request, property);
            if (jsonObject == null) {
                return null;
            }
            return objectMapper.apply(jsonObject);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public <T> T getRequiredObject(String property, Function<JsonObject, T> objectMapper) {
        requireProperty(property);
        return getOptionalObject(property, objectMapper);
    }

    public <T> T readRequiredObject(String property, Function<JsonRestRequestReader, T> objectMapper) {
        requireProperty(property);
        return readOptionalObject(property, objectMapper);
    }

    public <T> T readOptionalObject(String property, Function<JsonRestRequestReader, T> objectMapper) {
        try {
            JsonObject jsonObject = JsonObjects.getJsonObject(request, property);
            if (jsonObject == null) {
                return null;
            }
            return objectMapper.apply(new JsonRestRequestReader(jsonObject));
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public <T extends Enum<?>> T getRequiredEnum(String property, Class<T> enumClass) {
        requireProperty(property);
        return getOptionalEnum(property, enumClass);
    }

    @SafeVarargs
    public final <T extends Enum<?>> T getRequiredEnum(String property, T... validValues) {
        requireProperty(property);
        return getOptionalEnum(property, validValues);
    }

    public <T extends Enum<?>> T getOptionalEnum(String property, Class<T> enumClass) {
        try {
            return JsonObjects.getEnum(request, property, enumClass);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    @SafeVarargs
    public final <T extends Enum<?>> T getOptionalEnum(String property, T... validValues) {
        try {
            return JsonObjects.getEnum(request, property, validValues);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public <T extends Enum<?>> List<T> getOptionalEnumList(String property, Class<T> enumClass) {
        try {
            return JsonObjects.getEnumList(request, property, enumClass);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    @SafeVarargs
    public final <T extends Enum<?>> List<T> getOptionalEnumList(String property, T... validValues) {
        try {
            return JsonObjects.getEnumList(request, property, validValues);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }

    public <T> T getOptionalObjectByString(String property, Function<String, T> objectGetter) {
        if (hasNoProperty(property)) {
            return null;
        }
        return getRequiredObjectByString(property, objectGetter);
    }

    public <T> T getRequiredObjectByString(String property, Function<String, T> objectGetter) {
        String propertyValue = getRequiredString(property);
        T object = objectGetter.apply(propertyValue);
        if (object == null) {
            throw new NotFoundException("property " + property + " has an invalid value");
        }
        return object;
    }

    public <T> T getRequiredObjectByUUID(String property, Function<UUID, T> objectGetter) {
        UUID uuid = getRequiredUUID(property);
        T object = objectGetter.apply(uuid);
        if (object == null) {
            throw new NotFoundException("property " + property + " has an invalid value");
        }
        return object;
    }

    public boolean hasNoProperty(String property) {
        return !request.containsKey(property) ||
               request.getValue("/" + property).getValueType() == JsonValue.ValueType.NULL ||
               (request.getValue("/" + property).getValueType() == JsonValue.ValueType.STRING && Strings.isBlank(request.getString(property)));
    }

    public void requireProperty(String property) {
        if (hasNoProperty(property)) {
            throw new BadRequestException("property " + property + " is required");
        }
    }

    public void requireAtLeastOneProperty(String... properties) {
        for (String property : properties) {
            if (request.containsKey(property)) {
                return;
            }
        }
        throw new BadRequestException("at least one property required [" + String.join(",", properties) + "]");
    }

    public void assertIdempotentProperty(String expectedValue, String property) {
        String value = getOptionalString(property);
        if (!Objects.equals(expectedValue, value)) {
            throw new ClientErrorException("property " + property + " cannot be updated", Response.Status.CONFLICT);
        }
    }

    public void assertIdempotentProperty(BigDecimal expectedValue, String property) {
        BigDecimal value = getOptionalBigDecimal(property);
        if (!Objects.equals(expectedValue, value)) {
            throw new ClientErrorException("property " + property + " cannot be updated", Response.Status.CONFLICT);
        }
    }

    public void assertIdempotentProperty(LocalDateTime expectedValue, String property) {
        LocalDateTime value = getOptionalLocalDateTime(property);
        if (!Objects.equals(expectedValue, value)) {
            throw new ClientErrorException("property " + property + " cannot be updated", Response.Status.CONFLICT);
        }
    }

    public void assertIdempotentProperty(UUID expectedValue, String property) {
        UUID value = getOptionalUUID(property);
        if (!Objects.equals(expectedValue, value)) {
            throw new ClientErrorException("property " + property + " cannot be updated", Response.Status.CONFLICT);
        }
    }

    public <T extends Enum<?>> void assertIdempotentProperty(T expectedValue, String property, Class<T> enumClass) {
        T value = getOptionalEnum(property, enumClass);
        if (!Objects.equals(expectedValue, value)) {
            throw new ClientErrorException("property " + property + " cannot be updated", Response.Status.CONFLICT);
        }
    }

    private <T> T convertProperty(String property, BiFunction<JsonObject, String, T> conversionMapper) {
        try {
            return conversionMapper.apply(request, property);
        } catch (Exception e) {
            throw new BadRequestException("property " + property + " has an invalid value " + request.getValue("/" + property), e);
        }
    }
}
