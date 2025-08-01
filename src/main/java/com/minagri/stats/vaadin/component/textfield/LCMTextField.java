package com.minagri.stats.vaadin.component.textfield;

import com.minagri.stats.core.java.Dates;
import com.minagri.stats.core.java.Integers;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LCMTextField extends TextField implements
        ComponentFluent<LCMTextField>,
        HasSizeFluent<LCMTextField>,
        HasEnabledFluent<LCMTextField>,
        FocusableFluent<LCMTextField>,
        HasStyleFluent<LCMTextField>,
        HasThemeFluent<LCMTextField>,
        HasValueFluent<LCMTextField, String>,
        HasValidationFluent<LCMTextField>,
        HasValueChangeModeFluent<LCMTextField>,
        HasPrefixAndSuffixFluent<LCMTextField>,
        HasLabelFluent<LCMTextField>,
        IsTextFieldFluent<LCMTextField>,
        HasPlaceholderFluent<LCMTextField> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public LCMTextField getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    public LCMTextField withSmallTheme() {
        addThemeVariants(TextFieldVariant.LUMO_SMALL);
        return getFluent();
    }

    @Override
    public void clear() {
        super.clear();
        setInvalid(false);
    }

    @Override
    public void setValue(String value) {
        super.setValue(Objects.requireNonNullElse(value, ""));
    }
    
    public void setIntegerValue(Integer value) {
        setValue(Integers.toString(value));
    }

    public void setYearMonthValue(YearMonth value) {
        setValue(Dates.format(value, Dates.MMYYYY_SLASHED));
    }

    public void setDateValue(LocalDate value) {
        setValue(Dates.format(value, Dates.DDMMYYYY_SLASHED));
    }
    
    public LCMTextField setTranslationValue(String valueNl, String valueFr) {
        addTranslationLinkedToMethod("setValue", valueNl, valueFr);
        getFluent().setValue(Translator.localizedValue(valueNl, valueFr));
        return getFluent();
    }

    public LCMTextField setTranslationValue(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setValue", key, messageParameters);
        getFluent().setValue(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    public LCMTextField setTranslationValue(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setValue", key, messageParameters);
        getFluent().setValue(Translator.translate(key, messageParameters));
        return getFluent();
    }
}
