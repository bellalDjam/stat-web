package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.HasPlaceholder;

public interface HasPlaceholderFluent<FLUENT extends HasPlaceholder> extends HasTranslation {
    FLUENT getFluent();

    default FLUENT withPlaceHolder(String placeHolder) {
        removeTranslationLinkedToMethod("setPlaceholder");
        getFluent().setPlaceholder(placeHolder);
        return getFluent();
    }

    default FLUENT withPlaceHolder(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setPlaceholder", key, messageParameters);
        getFluent().setPlaceholder(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withPlaceHolderKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setPlaceholder", key, messageParameters);
        getFluent().setPlaceholder(Translator.translateKey(key, messageParameters));
        return getFluent();
    }
}
