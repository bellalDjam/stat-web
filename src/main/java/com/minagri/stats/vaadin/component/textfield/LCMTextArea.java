package com.minagri.stats.vaadin.component.textfield;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;

import java.util.HashMap;
import java.util.Map;

public class LCMTextArea extends TextArea implements
        ComponentFluent<LCMTextArea>,
        HasSizeFluent<LCMTextArea>,
        HasEnabledFluent<LCMTextArea>,
        FocusableFluent<LCMTextArea>,
        HasStyleFluent<LCMTextArea>,
        HasThemeFluent<LCMTextArea>,
        HasValueFluent<LCMTextArea, String>,
        HasValidationFluent<LCMTextArea>,
        HasValueChangeModeFluent<LCMTextArea>,
        HasPrefixAndSuffixFluent<LCMTextArea>,
        HasLabelFluent<LCMTextArea>,
        IsTextAreaFluent<LCMTextArea>,
        HasPlaceholderFluent<LCMTextArea> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public LCMTextArea getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    public LCMTextArea withSmallTheme() {
        addThemeVariants(TextAreaVariant.LUMO_SMALL);
        return getFluent();
    }

    public LCMTextArea setLocalizedValue(String valueNl, String valueFr) {
        addTranslationLinkedToMethod("setValue", valueNl, valueFr);
        setValue(Translator.localizedValue(valueNl, valueFr));
        return getFluent();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value == null ? "" : value);
    }
}
