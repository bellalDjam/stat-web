package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface HasTranslation extends LocaleChangeObserver {

    Map<String, Translation> getTranslationsLinkedToMethodsMap();

    @Override
    default void localeChange(LocaleChangeEvent event) {
        if (getTranslationsLinkedToMethodsMap() == null || getTranslationsLinkedToMethodsMap().isEmpty()) {
            return;
        }

        for (String methodName : getTranslationsLinkedToMethodsMap().keySet()) {
            try {
                getClass().getMethod(methodName, String.class).invoke(this, Translator.translate(getTranslationsLinkedToMethodsMap().get(methodName)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                // Silently fail
            }
        }
    }

    default void addTranslationLinkedToMethod(String methodName, String key, Object... messageParameters) {
        Translation translation = Translator.createTranslation(key, messageParameters);
        addTranslationLinkedToMethod(methodName, translation);
    }

    default void addTranslationLinkedToMethod(String methodName, Enum<?> key, Object... messageParameters) {
        Translation translation = Translator.createTranslation(key, messageParameters);
        addTranslationLinkedToMethod(methodName, translation);
    }

    default void addTranslationKeyLinkedToMethod(String methodName, TranslationKey key, Object... messageParameters) {
        Translation translation = Translator.createTranslation(key, messageParameters);
        addTranslationLinkedToMethod(methodName, translation);
    }

    default void addTranslationLinkedToMethod(String methodName, String explicitValueNl, String explicitValueFr) {
        Translation translation = Translator.createTranslation(explicitValueNl, explicitValueFr);
        addTranslationLinkedToMethod(methodName, translation);
    }

    default void addTranslationLinkedToMethod(String methodName, Translation translation) {
        if (getTranslationsLinkedToMethodsMap() == null) {
            return;
        }

        getTranslationsLinkedToMethodsMap().put(methodName, translation);
        if (translation != null) {
            translation.addParameterChangeObserver(this);
        }
    }

    default void removeTranslationLinkedToMethod(String methodName) {
        if (getTranslationsLinkedToMethodsMap() == null) {
            return;
        }

        Optional.ofNullable(getTranslationsLinkedToMethodsMap().get(methodName)).ifPresent(translation -> translation.removeParameterChangeObserver(this));
        getTranslationsLinkedToMethodsMap().remove(methodName);
    }

    default void onParameterChangeInConditionalTranslation(Translation Translation) {
        if (getTranslationsLinkedToMethodsMap() == null) {
            return;
        }

        Objects.requireNonNull(Translation);

        getTranslationsLinkedToMethodsMap().entrySet().stream().filter(entry -> Translation.equals(entry.getValue())).forEach(entry -> {
            try {
                getClass().getMethod(entry.getKey(), String.class).invoke(this, Translator.translate(Translation.getKey(), Translation.getMessageParameters()));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                // Silently fail
            }
        });
    }
}
