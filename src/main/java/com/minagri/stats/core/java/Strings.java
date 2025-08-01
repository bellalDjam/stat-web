package com.minagri.stats.core.java;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.interval.entity.Span;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public interface Strings {
    String EMPTY = "";
    String SPACE = " ";
    String PIPE = "|";
    String UNDERSCORE = "_";
    String DASH = "-";
    String COLON = ":";
    String SEMICOLON = ";";
    String ZERO = "0";
    String ONE = "1";
    String DOT = ".";
    String COMMA = ",";
    String NEW_LINE = "\n";

    static String escapeCharacters(String value, String escapeChars, Character... escapeValues) {
        int i = 0;

        StringBuilder sb = new StringBuilder();
        while (i < value.length()) {
            char c = value.charAt(i);
            if (Arrays.asList(escapeValues).contains(c)) {
                sb.append(escapeChars);
            }
            sb.append(c);
            ++i;
        }
        return sb.toString();
    }

    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    static void validateNotBlank(String value, @NonNull String message) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    static boolean isAnyBlank(String... values) {
        return Arrays.stream(values).anyMatch(Strings::isBlank);
    }

    static boolean equalsIgnoreCase(String value, String anotherString) {
        return value != null && value.equalsIgnoreCase(anotherString);
    }

    static boolean equals(String value, String anotherString) {
        return value != null && value.equals(anotherString);
    }

    static List<String> split(String value, String separator) {
        return Arrays.asList(value.split(Pattern.quote(separator), -1));
    }

    static String nullToEmpty(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Returns the given value as a string if the value is not empty.
     */
    static String emptyToNull(String value) {
        if (Strings.isEmpty(value)) {
            return null;
        }
        return value;
    }

    static String blankToNull(String value) {
        return isBlank(value) ? null : value;
    }

    static String blankToDefault(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }

    static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static boolean isNotNumeric(String value) {
        return !isNumeric(value);
    }

    static boolean isSignedNumeric(String value) {
        return startsWithAny(value, "+", "-") && isNumeric(value);
    }

    static boolean isUnsignedNumeric(String value) {
        return !startsWithAny(value, "+", "-") && isNumeric(value);
    }

    static boolean isNotUnsignedNumeric(String value) {
        return !isUnsignedNumeric(value);
    }

    static boolean isInteger(String value) {
        if (value == null) {
            return false;
        }
        try {
            new BigInteger(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static boolean isNotInteger(String value) {
        return !isInteger(value);
    }

    static boolean isSignedInteger(String value) {
        return startsWithAny(value, "+", "-") && isInteger(value);
    }

    static boolean isUnsignedInteger(String value) {
        return !startsWithAny(value, "+", "-") && isInteger(value);
    }

    static boolean isNotUnsignedInteger(String value) {
        return !isUnsignedInteger(value);
    }

    static int length(String value) {
        return value == null ? 0 : value.length();
    }

    static boolean lengthEqualsTo(String value, int length) {
        return length(value) == length;
    }

    static boolean lengthEqualsToAny(String value, int... lengths) {
        int length = length(value);
        return Arrays.stream(lengths).anyMatch(l -> l == length);
    }

    static boolean lengthNotEqualsToAny(String value, int... lengths) {
        return !lengthEqualsToAny(value, lengths);
    }

    static boolean lengthNotEqualsTo(String value, int length) {
        return !lengthEqualsTo(value, length);
    }

    static String remove(String value, String removeValue) {
        return value == null ? null : value.replace(removeValue, "");
    }

    static String removeAny(String value, String... removeValues) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(removeValues).reduce(value, Strings::remove);
    }

    static String removeSpaces(String value) {
        return remove(value, SPACE);
    }

    static String joinWithSpace(String... values) {
        return String.join(SPACE, values);
    }

    static String joinWithSpace(List<String> values) {
        return String.join(SPACE, values);
    }

    static String joinWithUnderscore(String... values) {
        return String.join(UNDERSCORE, values);
    }

    static String joinWithUnderscore(List<String> values) {
        return String.join(UNDERSCORE, values);
    }

    static String joinWithNewline(List<String> values) {
        return String.join(NEW_LINE, values);
    }

    static String joinWithPipe(List<String> values) {
        return String.join(PIPE, values);
    }

    static String joinWithDot(String... values) {
        return String.join(DOT, values);
    }

    static String joinWithComma(List<String> values) {
        return String.join(COMMA, values);
    }

    static String joinWithComma(String... values) {
        return String.join(COMMA, values);
    }

    static String concat(String... values) {
        return String.join("", values);
    }

    static boolean containsIgnoreCase(String value, String containValue) {
        if (value == null) {
            return false;
        }
        return value.toLowerCase().contains(containValue.toLowerCase());
    }

    static boolean containsOnlySpacesOrZeros(String value) {
        return containsOnly(value, ' ', '0');
    }

    static boolean containsOnlyZeros(String value) {
        return containsOnly(value, '0');
    }

    static boolean containsOnlySpaces(String value) {
        return containsOnly(value, ' ');
    }

    static boolean containsOnly(String value, Character... containChars) {
        if (value == null) {
            return false;
        }

        return value.chars().allMatch(c -> Arrays.stream(containChars).anyMatch(containChar -> containChar == c));
    }

    static boolean containsAny(String value, Character... containChars) {
        if (value == null) {
            return false;
        }

        return value.chars().anyMatch(c -> Arrays.stream(containChars).anyMatch(containChar -> containChar == c));
    }

    static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    static String right(String value, int length) {
        if (value == null) {
            return null;
        } else if (length < 0) {
            return "";
        } else {
            return value.length() <= length ? value : value.substring(value.length() - length);
        }
    }

    static String left(String value, int length) {
        if (value == null) {
            return null;
        } else if (length < 0) {
            return "";
        } else {
            return value.length() <= length ? value : value.substring(0, length);
        }
    }

    static String incrementNumericString(String numericString, Integer increment) {
        Long numericValue = Long.valueOf(numericString);
        return String.valueOf(numericValue + increment);
    }

    static String leftPad(String value, int length, char paddingChar) {
        if (value == null) {
            return null;
        }

        int paddingLength = length - value.length();
        if (paddingLength > 0) {
            return String.valueOf(paddingChar).repeat(paddingLength) + value;
        } else {
            return value;
        }
    }

    static String leftPadZero(String value, int length) {
        return leftPad(value, length, '0');
    }

    static String leftPadSpace(String value, int length) {
        return leftPad(value, length, ' ');
    }

    static String rightPad(String value, int length, char paddingChar) {
        if (value == null) {
            return null;
        }

        int paddingLength = length - value.length();
        if (paddingLength > 0) {
            return value + String.valueOf(paddingChar).repeat(paddingLength);
        } else {
            return value;
        }
    }

    static String rightPadZero(String value, int length) {
        return rightPad(value, length, '0');
    }

    static String rightPadSpace(String value, int length) {
        return rightPad(value, length, ' ');
    }

    static String leftPadZeroBlankNull(String value, int length) {
        if (Strings.isBlank(value)) {
            return null;
        }
        return leftPad(value, length, '0');
    }

    static String substring(String value, int start, int end) {
        if (value == null) {
            return null;
        }
        int length = length(value);
        if (end < 0) {
            end += value.length();
        }
        if (start >= length) {
            return "";
        }
        if (end > length) {
            return value.substring(start);
        }
        return value.substring(start, end);
    }

    static String substring(String value, int start) {
        if (value == null) {
            return null;
        }
        int length = length(value);
        if (start >= length) {
            return "";
        }
        return value.substring(start);
    }

    static String getSpan(String value, Span span) {
        return Strings.substring(value, span.getBegin() - 1, span.getEnd());
    }

    static Integer getSpanAsInteger(String value, Span span) {
        return Strings.toInteger(getSpan(value, span));
    }

    static Long getSpanAsLong(String value, Span span) {
        return Strings.toLong(getSpan(value, span));
    }

    static <T extends Enum<?>> T getSpanAsEnum(String value, Span span, Class<T> enumClass) {
        return Enums.getFromCodeOrName(getSpan(value, span), enumClass);
    }

    static BigDecimal getSpanAsBigDecimal(String value, Span span) {
        return Strings.toBigDecimal(getSpan(value, span));
    }

    static BigDecimal getSpanAsAmountFromCentString(String value, Span span) {
        return Strings.centStringToCoinAmount(getSpan(value, span));
    }

    static String setSpan(String value, Span span, String spanValue, BiFunction<String, Integer, String> fillerFunction) {
        if (spanValue == null) {
            return value;
        }

        Integer spanLength = span.getLength();

        if (value.length() < span.getEnd()) {
            throw new RuntimeException("The value '%s' is shorter than the span end %d".formatted(value, span.getEnd()));
        }

        if (spanValue.length() > spanLength) {
            throw new RuntimeException("The span value '%s' is longer than the expected length %d".formatted(spanValue, spanLength));
        }

        if (spanValue.length() < spanLength) {
            spanValue = fillerFunction.apply(spanValue, spanLength);
            if (spanValue.length() != spanLength) {
                throw new RuntimeException("The filled span value '%s' does not have the expected length %d".formatted(spanValue, spanLength));
            }
        }

        return Strings.substring(value, 0, span.getBegin() - 1) + spanValue + Strings.substring(value, span.getEnd());
    }

    static String setSpan(String value, Span span, String spanValue) {
        return setSpan(value, span, spanValue, Strings::leftPadSpace);
    }

    static String setSpan(String value, Span span, Integer spanValue) {
        return setSpan(value, span, Integers.toString(spanValue, span.getLength()));
    }

    static String setSpan(String value, Span span, Long spanValue) {
        return setSpan(value, span, Longs.toString(spanValue, span.getLength()));
    }

    static String setSpan(String value, Span span, LocalDate spanValue, DateTimeFormatter formatter) {
        return setSpan(value, span, Dates.format(spanValue, formatter));
    }

    static String setSpan(String value, Span span, LocalDateTime spanValue, DateTimeFormatter formatter) {
        return setSpan(value, span, Dates.format(spanValue, formatter));
    }

    static String setSpanAsSignedCentStringFromAmount(String value, Span span, BigDecimal amount) {
        return setSpan(value, span, BigDecimals.coinToSignedCentString(amount, span.getLength()));
    }

    static boolean startsWith(String value, String startValue) {
        if (value == null) {
            return false;
        }
        return value.startsWith(startValue);
    }

    static boolean startsWithAny(String value, String... startValues) {
        if (value == null) {
            return false;
        }
        return Arrays.stream(startValues).anyMatch(value::startsWith);
    }

    static boolean endsWith(String value, String endValue) {
        if (value == null) {
            return false;
        }
        return value.endsWith(endValue);
    }

    static boolean endsWithAny(String value, String... endValues) {
        if (value == null) {
            return false;
        }
        return Arrays.stream(endValues).anyMatch(value::endsWith);
    }

    static boolean contains(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return indexOf(seq, searchSeq, 0) >= 0;
    }

    static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }

    static List<Integer> toIntegerList(List<String> values) {
        return values.stream().map(Strings::toInteger).toList();
    }

    static Integer toInteger(String value) {
        try {
            BigDecimal bigDecimalValue = toBigDecimal(value);
            return bigDecimalValue != null ? bigDecimalValue.intValue() : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Integer toIntegerIgnoreZero(String value) {
        if (Strings.containsOnlyZeros(value)) {
            return null;
        }
        return toInteger(value);
    }

    static Long toLong(String value) {
        try {
            BigDecimal bigDecimalValue = toBigDecimal(value);
            return bigDecimalValue != null ? bigDecimalValue.longValue() : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Returns the given value as a long or null if the value contains only zero's.
     */
    static Long toLongIgnoreZero(String value) {
        if (Strings.containsOnlyZeros(value)) {
            return null;
        }
        return toLong(value);
    }

    /**
     * Returns the given value as a long or zero if the value cannot be converted.
     */
    static Long toLongDefaultZero(String value) {
        return value == null ? 0L : toLong(value);
    }

    static List<Long> toLongList(List<String> stringList) {
        List<Long> longList = new ArrayList<>();
        for (String s : stringList) {
            longList.add(toLong(s));
        }
        return longList;
    }

    /**
     * Returns the given value as a double.
     */
    static Double toDouble(String value) {
        try {
            BigDecimal bigDecimalValue = value == null ? null : new BigDecimal(value);
            return bigDecimalValue != null ? bigDecimalValue.doubleValue() : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static BigDecimal toBigDecimal(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static BigDecimal toBigDecimal(String value, BigDecimal defaultValue) {
        BigDecimal result = toBigDecimal(value);
        return result == null ? defaultValue : result;
    }
    
    static BigInteger toBigInteger(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    static boolean toBoolean(String value) {
        return Strings.equalsIgnoreCase(value, "true");
    }

    static boolean isTrue(String value) {
        return toBoolean(value);
    }

    static boolean isTrueOrNull(String value) {
        return value == null || isTrue(value);
    }

    static boolean isFalse(String value) {
        return !toBoolean(value);
    }

    static boolean isFalseOrNull(String value) {
        return value == null || isFalse(value);
    }

    static String flattenToAscii(String value) {
        if (value == null) {
            return null;
        }
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    static String removeLeadingZeros(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("^0+(?=\\d+$)", "");
    }

    static String removeWhitespaces(String value) {
        return removeAny(value, " ", "\t", "\n");
    }

    static void ifNotBlank(String value, Consumer<String> consumer) {
        if (isNotBlank(value)) {
            consumer.accept(value);
        }
    }

    static void ifNotBlankOrZero(String value, Consumer<String> consumer) {
        if (isNotBlank(value) && !containsOnlySpacesOrZeros(value)) {
            consumer.accept(value);
        }
    }

    static boolean notEqualTo(String value1, String value2) {
        return Objects.notEqualTo(value1, value2);
    }

    static boolean notEqualToAny(String value, String... otherValues) {
        return Objects.notEqualToAny(value, otherValues);
    }

    static boolean equalTo(String value1, String value2) {
        return Objects.equalTo(value1, value2);
    }

    static boolean equalToAny(String value, String... otherValues) {
        return Objects.equalToAny(value, otherValues);
    }

    static boolean isValidDate(String value) {
        return Dates.safeParseDate(value) != null;
    }

    static boolean isValidDate(String value, DateTimeFormatter formatter) {
        return Dates.safeParseDate(value, formatter) != null;
    }

    static BigDecimal centStringToCoinAmount(String centString) {
        BigDecimal centAmount = toBigDecimal(centString);
        return BigDecimals.centToCoinAmount(centAmount);
    }

    static String replace(String original, int start, int end, String replacement) {
        if (original == null) {
            return null;
        }
        return original.substring(0, start) + replacement + original.substring(end);
    }

    static boolean isPartiallyFilled(String... values){
        boolean hasEmpty = false;
        boolean hasFilled = false;

        for (String value : values){
            if (isEmpty(value)) {
                hasEmpty = true;
            } else {
                hasFilled = true;
            }

            if (hasEmpty && hasFilled) {
                return true;
            }
        }

        return false;
    }
}
