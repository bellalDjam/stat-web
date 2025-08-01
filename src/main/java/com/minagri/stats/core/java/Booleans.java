package com.minagri.stats.core.java;

import java.util.Arrays;
import java.util.List;

public interface Booleans {

    static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    static boolean isTrueOrNull(Boolean value) {
        return value == null || isTrue(value);
    }

    static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    static boolean isFalseOrNull(Boolean value) {
        return value == null || isFalse(value);
    }

    static boolean hasOneTrueValue(Boolean... values) {
        return hasOneTrueValue(Arrays.asList(values));
    }

    static boolean hasOneTrueValue(List<Boolean> values) {
        return values.stream().filter(Booleans::isTrue).count() == 1;
    }

    static Integer toBinary(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? 1 : 0;
    }

    static String toBinaryString(Boolean value) {
        return Integers.toString(toBinary(value));
    }
}
