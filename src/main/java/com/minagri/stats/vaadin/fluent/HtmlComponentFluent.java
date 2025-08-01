package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.HtmlComponent;

public interface HtmlComponentFluent<FLUENT extends HtmlComponent> extends HasTranslation {

    default FLUENT withTitle(String text) {
        removeTranslationLinkedToMethod("setTitle");
        getFluent().setTitle(text);
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

    FLUENT getFluent();
}
