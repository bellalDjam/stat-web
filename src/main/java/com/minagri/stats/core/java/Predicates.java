package com.minagri.stats.core.java;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Predicates {

    static <T, P> Predicate<T> propertyEqualTo(Function<T, P> propertyMapper, P property) {
        return item -> Objects.equals(propertyMapper.apply(item), property);
    }

    static <T, P> Predicate<T> propertyNotEqualTo(Function<T, P> propertyMapper, P property) {
        return item -> !Objects.equals(propertyMapper.apply(item), property);
    }

    static <T, P1, P2> Predicate<T> propertyEqualTo(Function<T, P1> property1Mapper, Function<P1, P2> property2Mapper, P2 property) {
        return item -> Objects.equals(property2Mapper.apply(property1Mapper.apply(item)), property);
    }

    static <T, P1, P2> Predicate<T> propertyNotEqualTo(Function<T, P1> property1Mapper, Function<P1, P2> property2Mapper, P2 property) {
        return item -> !Objects.equals(property2Mapper.apply(property1Mapper.apply(item)), property);
    }
}
