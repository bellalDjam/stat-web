package com.minagri.stats.vaadin.component.id;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.LCMTextField;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LCMCbeNumberField extends CustomField<String> implements
        ComponentFluent<LCMCbeNumberField>,
        HasSizeFluent<LCMCbeNumberField>,
        HasEnabledFluent<LCMCbeNumberField>,
        FocusableFluent<LCMCbeNumberField>,
        HasStyleFluent<LCMCbeNumberField>,
        HasValueFluent<LCMCbeNumberField, String>,
        HasValidationFluent<LCMCbeNumberField>,
        HasLabelFluent<LCMCbeNumberField> {

    private LCMTextField textField = new LCMTextField()
            .withPlaceHolder("zxxx.xxx.xxx")
            .withStyle("flex", "1");

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMCbeNumberField() {
        add(textField);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.withValueChangeListener(() -> {
            String modelValue = generateModelValue();
            setModelValue(modelValue, true);
            setInvalid(Strings.isNotBlank(textField.getValue()) && modelValue == null);
        });
    }

    @Override
    protected void updateValue() {
        String modelValue = this.generateModelValue();
        this.setModelValue(modelValue, true);
        setPresentationValue(modelValue);
    }

    @Override
    protected String generateModelValue() {
        String modelValue = Strings.removeAny(textField.getValue(), "-", " ", ".");

        if (Strings.isNotUnsignedInteger(modelValue)) {
            return null;
        }
        
        if (Strings.lengthEqualsTo(modelValue, 9) && !Strings.startsWith(modelValue, "0")) {
            modelValue = "0" + modelValue;
        }

        if (Strings.length(modelValue) > 10) {
            return modelValue.substring(0, 10);
        }
        
        if (Strings.lengthNotEqualsTo(modelValue, 10)) {
            return null;
        }
        
        return modelValue;
    }

    @Override
    protected void setPresentationValue(String modelValue) {
        if (Strings.isBlank(modelValue)) {
            textField.setValue(null);
        } else if (Strings.length(modelValue) != 10) {
            textField.setValue(null);
        } else {
            String part1 = Strings.substring(modelValue, 0, 4);
            String part2 = Strings.substring(modelValue, 4, 7);
            String part3 = Strings.substring(modelValue, 7, 10);
            textField.setValue(part1 + "." + part2 + "." + part3);
        }
    }

    @Override
    public LCMCbeNumberField getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    @Override
    public void clear() {
        super.clear();
        textField.clear();
        textField.setInvalid(false);
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        textField.setInvalid(invalid);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        textField.setReadOnly(readOnly);
    }
}
