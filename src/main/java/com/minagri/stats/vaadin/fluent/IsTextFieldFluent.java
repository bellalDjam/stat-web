package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.core.java.Objects;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.textfield.TextField;

public interface IsTextFieldFluent<FLUENT extends TextField> extends HasTranslation {

    default FLUENT withAutofocus(boolean autofocus) {
        getFluent().setAutofocus(autofocus);
        return getFluent();
    }

    default FLUENT withAutofocus() {
        return withAutofocus(true);
    }

    default FLUENT withNoAutofocus() {
        return withAutofocus(false);
    }

    default FLUENT withAutoselect(boolean autoselect) {
        getFluent().setAutoselect(autoselect);
        return getFluent();
    }

    default FLUENT withAutoselect() {
        return withAutoselect(true);
    }

    default FLUENT withNoAutoselect() {
        return withAutoselect(false);
    }

    default FLUENT withClearButtonVisible(boolean clearButtonVisible) {
        getFluent().setClearButtonVisible(clearButtonVisible);
        return getFluent();
    }

    default FLUENT withClearButtonVisible() {
        return withClearButtonVisible(true);
    }

    default FLUENT withNoClearButtonVisible() {
        return withClearButtonVisible(false);
    }

    default FLUENT withMaxLength(int maxLength) {
        getFluent().setMaxLength(maxLength);
        return getFluent();
    }

    default FLUENT withMinLength(int minLength) {
        getFluent().setMinLength(minLength);
        return getFluent();
    }

    default FLUENT withPattern(String pattern) {
        getFluent().setPattern(pattern);
        return getFluent();
    }

    default FLUENT withRequired(boolean required) {
        getFluent().setRequired(required);
        return getFluent();
    }

    default FLUENT withRequired() {
        return withRequired(true);
    }

    default FLUENT withNotRequired() {
        return withRequired(false);
    }

    default FLUENT withTitle(String title) {
        removeTranslationLinkedToMethod("setTitle");
        getFluent().setTitle(title);
        return getFluent();
    }

    default FLUENT withTitle(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setTitle", key, messageParameters);
        getFluent().setTitle(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withTitleKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setTitle", key, messageParameters);
        getFluent().setTitle(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withUpperCaseInput() {
        FLUENT fluent = getFluent();
        fluent.addValueChangeListener(event -> {
            String value = event.getValue();
            if (Strings.isBlank(value)) {
                return;
            }

            String upperCaseValue = value.toUpperCase();
            if (Objects.notEqualTo(value, upperCaseValue)) {
                fluent.setValue(upperCaseValue);
            }
        });
        return fluent;
    }

    FLUENT getFluent();

}
