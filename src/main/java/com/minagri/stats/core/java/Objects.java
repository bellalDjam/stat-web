package com.minagri.stats.core.java;

import com.minagri.stats.core.exception.Exceptions;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Objects {

    static <T> boolean equalTo(T object, T otherObject) {
        return java.util.Objects.equals(object, otherObject);
    }

    static <T> boolean notEqualTo(T object, T otherObject) {
        return !equalTo(object, otherObject);
    }

    @SafeVarargs
    static <T> boolean equalToAny(T object, T... otherObjects) {
        return Arrays.stream(otherObjects).anyMatch(otherObject -> equalTo(object, otherObject));
    }

    @SafeVarargs
    static <T> boolean notEqualToAny(T object, T... otherObjects) {
        return !equalToAny(object, otherObjects);
    }

    static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    static boolean allNull(Object... objects) {
        return Arrays.stream(objects).allMatch(java.util.Objects::isNull);
    }

    static boolean anyNotNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(java.util.Objects::nonNull);
    }

    @SafeVarargs
    static <B, W> boolean areEqualByProperties(B object1, W object2, BiFunction<B, W, Boolean>... equalsFunctions) {
        return Arrays.stream(equalsFunctions).allMatch(func -> func.apply(object1, object2));
    }

    static void ifNull(Object object, Runnable runnable) {
        if (object == null) {
            runnable.run();
        }
    }

    static <T> T cast(Object object) {
        try {
            return (T) object;
        } catch (Exception e) {
            throw Exceptions.simpleException(e, "Unable to cast object", object);
        }
    }

    static <T, R> R getProperty(T value, Function<T, R> propertyGetter) {
        return value != null ? propertyGetter.apply(value) : null;
    }

    static <T, R> R getProperty(T value, Function<T, R> propertyGetter, R defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        R property = propertyGetter.apply(value);
        return property != null ? property : defaultValue;
    }

    /**
     * Casts the given value to the target class or returns null of the cast cannot be done.
     */
    @SuppressWarnings("unchecked")
    static <T> T safeCast(Object value, Class<T> targetClass) {
        if (value == null || !targetClass.isAssignableFrom(value.getClass())) {
            return null;
        }
        return (T) value;
    }

}
