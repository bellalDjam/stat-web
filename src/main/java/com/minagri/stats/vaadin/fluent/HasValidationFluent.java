package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.HasValidation;

public interface HasValidationFluent<FLUENT extends HasValidation> extends HasTranslation {

    default FLUENT withInvalid(boolean invalid) {
        getFluent().setInvalid(invalid);
        return getFluent();
    }

    default FLUENT withInvalid() {
        return withInvalid(true);
    }

    default FLUENT withValid() {
        return withInvalid(false);
    }

    default FLUENT withErrorMessage(String errorMessage) {
        removeTranslationLinkedToMethod("setErrorMessage");
        getFluent().setErrorMessage(errorMessage);
        return getFluent();
    }

    default FLUENT withErrorMessage(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setErrorMessage", key, messageParameters);
        getFluent().setErrorMessage(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withErrorMessageKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setErrorMessage", key, messageParameters);
        getFluent().setErrorMessage(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    FLUENT getFluent();
}
