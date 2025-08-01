package com.minagri.stats.vaadin.translation.entity;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.translation.control.Translator;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author t51sagbse Benny Segers
 * @version %PR%
 */
@UtilityClass
public class Formatter {
    public static <T extends Enum<?>> String formatEnumTranslate(T enumValue) {
        Objects.requireNonNull(enumValue);
        String translate = Translator.translate(formatTranslationKeyEnum(enumValue));
        if (Strings.startsWith(translate, "[NO TRANSLATION FOR ")) {
            return enumValue.name();
        }
        return translate;
    }

    public static <T extends Enum<?>> String formatTranslationKeyEnum(T enumValue) {
        Objects.requireNonNull(enumValue);
        return enumValue.getClass().getSimpleName() + "." + enumValue.name();
    }

    public static <T extends Enum<?>> List<String> formatEnumList(List<T> enumValues) {
        return enumValues.stream().map(Formatter::formatEnumTranslate).collect(Collectors.toList());
    }
}
