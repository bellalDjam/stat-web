package com.minagri.stats.core.java;

import java.util.Optional;
import java.util.function.Function;

public interface Optionals {
    @SuppressWarnings("unchecked")
    static <T, S extends T> Optional<S> cast(Optional<T> optional) {
        return optional.map(item -> (S) item);
    }

    @SafeVarargs
    static <T> Optional<T> firstPresent(Optional<T>... optionals) {
        for (Optional<T> optional : optionals) {
            if (optional.isPresent()) {
                return optional;
            }
        }
        return Optional.empty();
    }

    static <V, R> R mapNullable(V value, Function<V, R> mapper) {
        return mapNullable(Optional.ofNullable(value), mapper);
    }

    static <V, R1> R1 mapOrNull(V value, Function<V, R1> mapper1) {
        return mapOrNull(Optional.ofNullable(value), mapper1);
    }

    static <V, R1, R2> R2 mapOrNull(V value, Function<V, R1> mapper1, Function<R1, R2> mapper2) {
        return mapOrNull(Optional.ofNullable(value), mapper1, mapper2);
    }

    static <V, R1, R2, R3> R3 mapOrNull(V value, Function<V, R1> mapper1, Function<R1, R2> mapper2, Function<R2, R3> mapper3) {
        return mapOrNull(Optional.ofNullable(value), mapper1, mapper2, mapper3);
    }

    static <V, R> R mapNullable(Optional<V> valueOptional, Function<V, R> mapper) {
        return valueOptional.map(mapper).orElse(null);
    }

    static <V, R1> R1 mapOrNull(Optional<V> valueOptional, Function<V, R1> mapper1) {
        return valueOptional
                .map(mapper1)
                .orElse(null);
    }

    static <V, R1, R2> R2 mapOrNull(Optional<V> valueOptional, Function<V, R1> mapper1, Function<R1, R2> mapper2) {
        return valueOptional
                .map(mapper1)
                .map(mapper2)
                .orElse(null);
    }

    static <V, R1, R2, R3> R3 mapOrNull(Optional<V> valueOptional, Function<V, R1> mapper1, Function<R1, R2> mapper2, Function<R2, R3> mapper3) {
        return valueOptional
                .map(mapper1)
                .map(mapper2)
                .map(mapper3)
                .orElse(null);
    }
}
