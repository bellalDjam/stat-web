package com.minagri.stats.core.enumeration.control;

import com.minagri.stats.core.enumeration.entity.EnumerationCode;
import com.minagri.stats.core.exception.Exceptions;
import com.minagri.stats.core.java.Integers;
import com.minagri.stats.core.java.Strings;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * @author t51sagbse Benny Segers
 * @version %PR%
 */
public interface Enums {

    /**
     * Returns a list of all the enum names of the given class.
     */
    static <T extends Enum<?>> List<String> listNames(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(Enums::getName).toList();
    }

    /**
     * Returns an enumeration value of the given enum class matching the given name.
     */
    static <T extends Enum<?>> T getFromName(String name, Class<T> enumClass) {
        if (Strings.isBlank(name)) {
            return null;
        }
        for (T enumValue : enumClass.getEnumConstants()) {
            if (Strings.equalsIgnoreCase(name, enumValue.name())) {
                return enumValue;
            }
        }
        return null;
    }

    /**
     * Returns an enumeration value of the given enum class matching the given code or name.
     */
    static <T extends Enum<?>> T getFromCodeOrName(String codeOrName, Class<T> enumClass) {
        T enumFromCode = getFromCode(codeOrName, enumClass);
        return enumFromCode != null ? enumFromCode : getFromName(codeOrName, enumClass);
    }

    /**
     * Returns an enumeration value of the given enum class matching the given code.
     */
    static <T extends Enum<?>> T getFromCode(Integer code, Class<T> enumClass) {
        return getFromCode(Integers.toString(code), enumClass);
    }

    /**
     * Returns an enumeration value of the given enum class matching the given code.
     * Finds matching codes even when the zero padding is not the same, ie 1 = 01 = 001
     */
    static <T extends Enum<?>> T getFromCode(String code, Class<T> enumClass) {
        if (Strings.isBlank(code)) {
            return null;
        }

        String lookupCode = getLookupCode(code);

        for (T enumValue : enumClass.getEnumConstants()) {
            EnumerationCode enumerationCode = getField(enumValue).getAnnotation(EnumerationCode.class);
            if (enumerationCode != null) {
                if (Strings.equalsIgnoreCase(enumerationCode.value(), lookupCode) || Objects.equals(code, enumerationCode.value())) {
                    return enumValue;
                }
            }
        }
        return null;
    }

    static <T extends Enum<?>> Optional<T> getFromCodeOptional(Integer code, Class<T> enumClass) {
        return Optional.ofNullable(getFromCode(code, enumClass));
    }

    static <T extends Enum<?>> Optional<T> getFromCodeOptional(String code, Class<T> enumClass) {
        return Optional.ofNullable(getFromCode(code, enumClass));
    }

    /**
     * Returns the code of the given enum value.
     */
    static String getCode(Enum<?> enumValue) {
        if (enumValue == null) {
            return null;
        }
        EnumerationCode enumerationCode = getField(enumValue).getAnnotation(EnumerationCode.class);
        return enumerationCode != null ? enumerationCode.value() : null;
    }

    /**
     * Returns the name of the given enum value.
     */
    static String getName(Enum<?> enumValue) {
        return enumValue != null ? enumValue.name() : null;
    }

    /**
     * Returns the code of the given enum value as an integer.
     */
    static Integer getCodeAsInteger(Enum<?> enumValue) {
        return Strings.toInteger(getCode(enumValue));
    }

    /**
     * Returns true if the given code equals any of the given enum values
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean codeEqualsAny(String code, T... enumValues) {
        return codeEqualsAny(code, asList(enumValues));
    }

    /**
     * Returns true if the given code equals any of the given enum values
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean codeEqualsAny(Integer code, T... enumValues) {
        return codeEqualsAny(code, asList(enumValues));
    }

    /**
     * Returns true if the given code equals any of the given enum values
     */
    static <T extends Enum<?>> boolean codeEqualsAny(String code, Collection<T> enumValues) {
        String lookupCode = getLookupCode(code);

        for (T enumValue : enumValues) {
            String enumCode = getCode(enumValue);
            if (Objects.equals(enumCode, lookupCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given code equals any of the given enum values
     */
    static <T extends Enum<?>> boolean codeEqualsAny(Integer code, Collection<T> enumValues) {
        for (T enumValue : enumValues) {
            Integer enumCode = getCodeAsInteger(enumValue);
            if (Objects.equals(enumCode, code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given code equals none of the given enum values
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean codeEqualsNone(String code, T... enumValues) {
        return !codeEqualsAny(code, asList(enumValues));
    }

    /**
     * Returns true if the given code equals none of the given enum values
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean codeEqualsNone(Integer code, T... enumValues) {
        return !codeEqualsAny(code, asList(enumValues));
    }

    /**
     * Returns true if the given code equals none of the given enum values
     */
    static <T extends Enum<?>> boolean codeEqualsNone(String code, Collection<T> enumValues) {
        return !codeEqualsAny(code, enumValues);
    }

    /**
     * Returns true if the given code equals none of the given enum values
     */
    static <T extends Enum<?>> boolean codeEqualsNone(Integer code, Collection<T> enumValues) {
        return !codeEqualsAny(code, enumValues);
    }

    /**
     * Checks if the given code is valid for the given enum class.
     */
    static <T extends Enum<?>> boolean isCodeValid(String code, Class<T> enumClass) {
        return getFromCode(code, enumClass) != null;
    }

    /**
     * Checks if the given code is valid for the given enum class.
     */
    static <T extends Enum<?>> boolean isCodeValid(Integer code, Class<T> enumClass) {
        return getFromCode(code, enumClass) != null;
    }

    /**
     * Checks if the given code is invalid for the given enum class.
     */
    static <T extends Enum<?>> boolean isCodeInvalid(String code, Class<T> enumClass) {
        return Strings.isNotBlank(code) && getFromCode(code, enumClass) == null;
    }

    /**
     * Checks if the given code is invalid for the given enum class.
     */
    static <T extends Enum<?>> boolean isCodeInvalid(Integer code, Class<T> enumClass) {
        return code != null && getFromCode(code, enumClass) == null;
    }

    /**
     * Checks if the given enum value is equal to any of the other enum values.
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean equalsAny(T enumValue, T... otherEnumValues) {
        return Arrays.asList(otherEnumValues).contains(enumValue);
    }

    /**
     * Checks if the given enum value is not equal to any of the other enum values.
     */
    @SafeVarargs
    static <T extends Enum<?>> boolean equalsNone(T enumValue, T... otherEnumValues) {
        return !equalsAny(enumValue, otherEnumValues);
    }

    static String getLookupCode(String code) {
        String lookupCode = code;
        if (Strings.length(code) > 1 && Strings.isNumeric(code)) {
            lookupCode = Integers.toString(Strings.toInteger(code));
        }
        return lookupCode;
    }

    static <T extends Enum<?>> Field getField(T enumValue) {
        try {
            return enumValue.getDeclaringClass().getDeclaredField(enumValue.name());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    static <T extends Enum<?>> T toEnum(Class<T> targetEnumClass, Enum<?> sourceEnum) {
        if (sourceEnum == null)
            return null;
        for (T enumValue : targetEnumClass.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(sourceEnum.name()))
                return enumValue;
        }
        throw Exceptions.simpleException(null, "No matching enum", sourceEnum, "for", targetEnumClass);
    }
}
