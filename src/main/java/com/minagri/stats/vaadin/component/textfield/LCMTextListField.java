package com.minagri.stats.vaadin.component.textfield;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LCMTextListField extends CustomField<List<String>> implements
        ComponentFluent<LCMTextListField>,
        HasSizeFluent<LCMTextListField>,
        HasEnabledFluent<LCMTextListField>,
        FocusableFluent<LCMTextListField>,
        HasStyleFluent<LCMTextListField>,
        HasValueFluent<LCMTextListField, List<String>>,
        HasValidationFluent<LCMTextListField>,
        HasLabelFluent<LCMTextListField> {

    private LCMTextField textField = new LCMTextField()
            .withStyle("flex", "1");

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMTextListField() {
        add(textField);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.withValueChangeListener(() -> {
            List<String> modelValue = generateModelValue();
            setModelValue(modelValue, true);
            setInvalid(Strings.isNotBlank(textField.getValue()) && modelValue == null);
        });
    }

    @Override
    protected void updateValue() {
        List<String> modelValue = this.generateModelValue();
        this.setModelValue(modelValue, true);
        setPresentationValue(modelValue);
    }

    @Override
    protected List<String> generateModelValue() {
        String inputValue = textField.getValue();
        
        if (Strings.isBlank(inputValue)) {
            return new ArrayList<>();
        }

        return Strings.split(inputValue.replace(Strings.SPACE, Strings.COMMA), Strings.COMMA)
                .stream()
                .map(String::trim)
                .filter(Strings::isNotBlank)
                .toList();
    }

    @Override
    protected void setPresentationValue(List<String> modelValue) {
        if (Collections.nullOrEmpty(modelValue)) {
            textField.setValue(null);
        } else {
            textField.setValue(String.join(", ", modelValue));
        }
    }

    @Override
    public LCMTextListField getFluent() {
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
