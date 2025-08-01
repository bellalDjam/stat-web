package com.minagri.stats.core.enumeration.entity;

import com.minagri.stats.core.enumeration.control.Enums;

public interface EnumerationCoded<T extends Enum<?>> {

    @SuppressWarnings("unchecked")
    default String getCode() {
        T enumValue = (T) this;
        return Enums.getCode(enumValue);
    }

    @SuppressWarnings("unchecked")
    default Integer getCodeAsInteger() {
        T enumValue = (T) this;
        return Enums.getCodeAsInteger(enumValue);
    }

    @SuppressWarnings("unchecked")
    default boolean codeEquals(String code) {
        T fromCode = Enums.getFromCode(code, (Class<T>) getClass());
        return this == fromCode;
    }

    @SuppressWarnings("unchecked")
    default boolean codeEquals(Integer code) {
        T fromCode = Enums.getFromCode(code, (Class<T>) getClass());
        return this == fromCode;
    }

    default boolean codeNotEquals(String code) {
        return !codeEquals(code);
    }

    default boolean codeNotEquals(Integer code) {
        return !codeEquals(code);
    }
}
