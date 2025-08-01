package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.vaadin.fluent.HasTranslation;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.textfield.AbstractNumberField;

public interface IsNumberFieldFluent<FLUENT extends AbstractNumberField> extends HasTranslation {
    default FLUENT withAutofocus(boolean autofocus) {
        this.getFluent().setAutofocus(autofocus);
        return this.getFluent();
    }

    default FLUENT withAutofocus() {
        return this.withAutofocus(true);
    }

    default FLUENT withNoAutofocus() {
        return this.withAutofocus(false);
    }

    default FLUENT withAutoselect(boolean autoselect) {
        this.getFluent().setAutoselect(autoselect);
        return this.getFluent();
    }

    default FLUENT withAutoselect() {
        return this.withAutoselect(true);
    }

    default FLUENT withNoAutoselect() {
        return this.withAutoselect(false);
    }

    default FLUENT withClearButtonVisible(boolean clearButtonVisible) {
        this.getFluent().setClearButtonVisible(clearButtonVisible);
        return this.getFluent();
    }

    default FLUENT withClearButtonVisible() {
        return this.withClearButtonVisible(true);
    }

    default FLUENT withNoClearButtonVisible() {
        return this.withClearButtonVisible(false);
    }

    default FLUENT withRequired(boolean required) {
        this.getFluent().setRequiredIndicatorVisible(required);
        return this.getFluent();
    }

    default FLUENT withRequired() {
        return this.withRequired(true);
    }

    default FLUENT withNotRequired() {
        return this.withRequired(false);
    }

    default FLUENT withTitle(String title) {
        this.removeTranslationLinkedToMethod("setTitle");
        this.getFluent().setTitle(title);
        return this.getFluent();
    }

    default FLUENT withTitle(Enum<?> key, Object... messageParameters) {
        this.addTranslationLinkedToMethod("setTitle", key, messageParameters);
        this.getFluent().setTitle(Translator.translate(key, messageParameters));
        return this.getFluent();
    }

    default FLUENT withTitleKey(TranslationKey key, Object... messageParameters) {
        this.addTranslationKeyLinkedToMethod("setTitle", key, messageParameters);
        this.getFluent().setTitle(Translator.translateKey(key, messageParameters));
        return this.getFluent();
    }

    FLUENT getFluent();
}
