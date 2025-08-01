package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.textfield.IntegerField;

import java.util.HashMap;
import java.util.Map;

public class LCMIntegerField extends IntegerField implements
        ComponentFluent<LCMIntegerField>,
        HasSizeFluent<LCMIntegerField>,
        HasEnabledFluent<LCMIntegerField>,
        FocusableFluent<LCMIntegerField>,
        HasStyleFluent<LCMIntegerField>,
        HasThemeFluent<LCMIntegerField>,
        HasValueFluent<LCMIntegerField, Integer>,
        HasValidationFluent<LCMIntegerField>,
        HasValueChangeModeFluent<LCMIntegerField>,
        HasPrefixAndSuffixFluent<LCMIntegerField>,
        HasLabelFluent<LCMIntegerField>,
        IsIntegerFieldFluent<LCMIntegerField>,
        HasPlaceholderFluent<LCMIntegerField> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMIntegerField getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }


    @Override
    public void clear() {
        super.clear();
        setInvalid(false);
    }

    @Override
    public void setMax(int max) {
        super.setMax(max);
        addBlurListener(e -> {
            if (getValue() != null && getValue() > max) {
                setValue(null);
            }
        });
    }

    @Override
    public void setMin(int min) {
        super.setMin(min);
        addBlurListener(e -> {
            if (getValue() != null && getValue() < min) {
                setValue(null);
            }
        });
    }
}
