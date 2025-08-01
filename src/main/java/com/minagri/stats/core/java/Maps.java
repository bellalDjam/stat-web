package com.minagri.stats.core.java;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Maps {
    static <K, V, T> Map<K, T> transformValues(Map<K, V> map, Function<V, T> valueTransformer) {
        Map<K, T> transformedMap = new HashMap<>();
        map.forEach((key, value) -> transformedMap.put(key, valueTransformer.apply(value)));
        return transformedMap;
    }

    static <K, V, T> List<T> transformToList(Map<K, V> map, BiFunction<K, V, T> entryTransformer) {
        List<T> transformedList = new ArrayList<>();
        map.forEach((key, value) -> transformedList.add(entryTransformer.apply(key, value)));
        return transformedList;
    }

    static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    static <K> void incrementValue(Map<K, BigDecimal> map, K key, BigDecimal value) {
        if (value == null) {
            return;
        }

        if (!map.containsKey(key)) {
            map.put(key, value);
        } else {
            map.put(key, BigDecimals.add(map.get(key), value));
        }
    }

    static <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> keyPredicate) {
        Map<K, V> filteredMap = new HashMap<>();
        map.forEach((key, value) -> {
            if (keyPredicate.test(key)) {
                filteredMap.put(key, value);
            }
        });
        return filteredMap;
    }

    static <K, V> Map<K, V> filterByKey(Map<K, V> map, List<K> keys) {
        return filterByKey(map, keys::contains);
    }
}
