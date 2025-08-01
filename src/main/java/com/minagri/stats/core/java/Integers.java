package com.minagri.stats.core.java;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface Integers {

    static String toString(Integer value) {
        return value == null ? null : value.toString();
    }

    static String leftToString(Integer value, Integer length) {
        return Strings.left(toString(value), length);
    }

    static String toStringNullEmpty(Integer value) {
        return value == null ? "" : value.toString();
    }

    static String toString(Integer value, int length) {
        if (value == null) {
            return null;
        }

        String stringValue = toString(value);
        return Strings.leftPad(stringValue, length, '0');
    }

    static String toStringNullEmpty(Integer value, int length) {
        String stringValue = toStringNullEmpty(value);
        return Strings.leftPad(stringValue, length, '0');
    }

    static Long toLong(Integer value) {
        return value == null ? null : value.longValue();
    }

    static boolean isNullOrZero(Integer value) {
        return value == null || value == 0;
    }

    static boolean isNotNullOrZero(Integer value) {
        return !isNullOrZero(value);
    }

    static Integer inverse(Integer value) {
        return value == null ? null : value * -1;
    }

    static boolean isEqualTo(Integer value1, Integer value2) {
        return Objects.equals(value1, value2);
    }

    static boolean isNotEqualTo(Integer value1, Integer value2) {
        return !Objects.equals(value1, value2);
    }

    static boolean isEqualToAny(Integer value, Integer... referenceValues) {
        return Arrays.asList(referenceValues).contains(value);
    }

    static boolean isNotEqualToAny(Integer value, Integer... referenceValues) {
        return !isEqualToAny(value, referenceValues);
    }

    static void ifEqualTo(Integer value, Integer referenceValue, Runnable runnable) {
        if (Objects.equals(value, referenceValue)) {
            runnable.run();
        }
    }

    static boolean isBetween(Integer value, Integer min, Integer max) {
        return value != null && value >= min && value <= max;
    }

    static Integer concatInteger(Integer... numbers) {
        StringBuilder concatenatedString = new StringBuilder();
        for (Integer number : numbers) {
            if (number != null) {
                concatenatedString.append(number);
            }
        }
        return Strings.toInteger(concatenatedString.toString());
    }

    static Integer sum(Integer... values) {
        return sum(Arrays.asList(values));
    }

    static Integer sum(List<Integer> values) {
        return values.stream().reduce(0, (result, value) -> value != null ? result + value : result);
    }

    static Boolean toBoolean(Integer value) {
        return value != null ? value != 0 : null;
    }
}
