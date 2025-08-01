package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.textfield.IntegerField;

public interface IsIntegerFieldFluent<FLUENT extends IntegerField> extends HasTranslation {
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

    default FLUENT withLabel(String label) {
        this.removeTranslationLinkedToMethod("setLabel");
        this.getFluent().setLabel(label);
        return this.getFluent();
    }

    default FLUENT withLabel(TranslationKey translationKey, Object... messageParameters) {
        this.addTranslationLinkedToMethod("setLabel", translationKey.getKey(), messageParameters);
        this.getFluent().setLabel(Translator.translateKey(translationKey, messageParameters));
        return this.getFluent();
    }

    default FLUENT withLabel(Translation translation) {
        this.addTranslationLinkedToMethod("setLabel", translation);
        this.getFluent().setLabel(Translator.translate(translation.getKey(), translation.getMessageParameters()));
        return this.getFluent();
    }

    default FLUENT withMax(int maxLength) {
        this.getFluent().setMax(maxLength);
        return this.getFluent();
    }

    default FLUENT withMin(int minLength) {
        this.getFluent().setMin(minLength);
        return this.getFluent();
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

    default FLUENT withTitle(TranslationKey translationKey, Object... messageParameters) {
        this.addTranslationLinkedToMethod("setTitle", translationKey.getKey(), messageParameters);
        this.getFluent().setTitle(Translator.translateKey(translationKey, messageParameters));
        return this.getFluent();
    }

    default FLUENT withTitle(Translation translation) {
        this.addTranslationLinkedToMethod("setTitle", translation);
        this.getFluent().setTitle(Translator.translate(translation.getKey(), translation.getMessageParameters()));
        return this.getFluent();
    }

    FLUENT getFluent();
}
