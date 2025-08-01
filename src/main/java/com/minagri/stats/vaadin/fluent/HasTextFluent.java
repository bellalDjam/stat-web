package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.core.java.BigDecimals;
import com.minagri.stats.core.java.Integers;
import com.minagri.stats.core.java.Longs;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.HasText;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public interface HasTextFluent<FLUENT extends HasText> extends HasTranslation {

    default FLUENT withText(String text) {
        removeTranslationLinkedToMethod("setText");
        getFluent().setText(text);
        return getFluent();
    }

    default FLUENT withText(Enum<?> key, Object... messageParameters) {
        FLUENT fluent = getFluent();
        if (key != null) {
            addTranslationLinkedToMethod("setText", key, messageParameters);
            fluent.setText(Translator.translate(key, messageParameters));
        } else {
            fluent.setText(null);
        }
        return fluent;
    }

    default FLUENT withTextKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setText", key, messageParameters);
        getFluent().setText(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withText(Translation translation) {
        addTranslationLinkedToMethod("setText", translation);
        getFluent().setText(Translator.translate(translation));
        return getFluent();
    }

    default FLUENT withTextKey(String key, Object... messageParameters) {
        addTranslationLinkedToMethod("setText", key, messageParameters);
        getFluent().setText(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT setLocalizedText(String valueNl, String valueFr) {
        addTranslationLinkedToMethod("setText", valueNl, valueFr);
        getFluent().setText(Translator.localizedValue(valueNl, valueFr));
        return getFluent();
    }

    default FLUENT withText(Number value) {
        return withText(value != null ? value.toString() : null);
    }

    default FLUENT withText(Boolean value) {
        CommonMessage yesNoKey = value != null ? value ? CommonMessage.YES : CommonMessage.NO : null;
        return withText(yesNoKey);
    }

    default FLUENT withDate(Temporal temporal, DateTimeFormatter formatter) {
        return withText(formatter.format(temporal));
    }

    default FLUENT setEuroAmountValue(BigDecimal amount) {
        return withText(BigDecimals.formatAmountWithEuroSign(amount));
    }

    default FLUENT setPercentageValue(BigDecimal percentage) {
        return setPercentageValue(percentage, 2, "");
    }

    default FLUENT setPercentageValue(BigDecimal percentage, String defaultValue) {
        return setPercentageValue(percentage, 2, defaultValue);
    }

    default FLUENT setPercentageValue(BigDecimal percentage, int precision, String defaultValue) {
        return withText(BigDecimals.formatPercentage(percentage, precision, defaultValue));
    }

    default FLUENT setValue(Long value) {
        return withText(Longs.toString(value));
    }

    default FLUENT setValue(Integer value) {
        return withText(Integers.toString(value));
    }

    default FLUENT setValue(BigDecimal value, int precision) {
        return withText(BigDecimals.toString(value, precision));
    }

    FLUENT getFluent();
}
