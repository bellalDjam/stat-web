package com.minagri.stats.core.java;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface Longs {
    static String toString(Long value) {
        return value == null ? null : value.toString();
    }

    static String toString(Long value, int length) {
        if (value == null) {
            return null;
        }

        String stringValue = toString(value);
        return Strings.leftPad(stringValue, length, '0');
    }

    static boolean isNullOrZero(Long value) {
        return value == null || value == 0;
    }

    static Integer toInteger(Long value) {
        return value != null ? Math.toIntExact(value) : null;
    }

    static Long sum(Long... values) {
        return sum(Arrays.asList(values));
    }

    static Long sum(List<Long> values) {
        return values.stream().reduce(0L, (result, value) -> value != null ? result + value : result);
    }

    static <T> Long sum(List<T> list, Function<T, Long> valueGetter) {
        return sum(list.stream().map(valueGetter).toList());
    }

    static boolean isEqualTo(Long value1, Long value2) {
        return Objects.equals(value1, value2);
    }

    static boolean isEqualToAny(Long value, Long... referenceValues) {
        return Arrays.asList(referenceValues).contains(value);
    }

    /**
     * Returns the given value as a double.
     */
    static Double toDouble(Long value) {
        if (value == null) {
            return null;
        }
        return value.doubleValue();
    }

    static Long getValueOrZeroWhenNull(Long value) {
        if (value == null) {
            return 0L;
        }
        return value;
    }

    static BigDecimal toBigDecimal(Long value) {
        if (value == null) {
            return null;
        }
        return BigDecimal.valueOf(value);
    }

    static BigDecimal centToEuroAmount(Long value) {
        BigDecimal bigDecimalValue = toBigDecimal(value);
        return BigDecimals.centToCoinAmount(bigDecimalValue);
    }
}
