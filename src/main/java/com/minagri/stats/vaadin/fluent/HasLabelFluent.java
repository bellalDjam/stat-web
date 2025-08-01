package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.HasLabel;

public interface HasLabelFluent<FLUENT extends HasLabel> extends HasTranslation {

    default FLUENT withLabel(String label) {
        removeTranslationLinkedToMethod("setLabel");
        getFluent().setLabel(label);
        return getFluent();
    }

    default FLUENT withLabelKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setLabel", key, messageParameters);
        getFluent().setLabel(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withLabel(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setLabel", key, messageParameters);
        getFluent().setLabel(Translator.translate(key, messageParameters));
        return getFluent();
    }

    FLUENT getFluent();
}
