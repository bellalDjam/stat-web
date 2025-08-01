package com.minagri.stats.vaadin.component.id;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.LCMTextField;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.HashMap;
import java.util.Map;

public class LCMNihdiIdField extends CustomField<String> implements
        ComponentFluent<LCMNihdiIdField>,
        HasSizeFluent<LCMNihdiIdField>,
        HasEnabledFluent<LCMNihdiIdField>,
        FocusableFluent<LCMNihdiIdField>,
        HasStyleFluent<LCMNihdiIdField>,
        HasValueFluent<LCMNihdiIdField, String>,
        HasValidationFluent<LCMNihdiIdField>,
        HasLabelFluent<LCMNihdiIdField> {

    private LCMTextField textField = new LCMTextField()
            .withPlaceHolder("xxxxxx-xx-xxx")
            .withStyle("flex", "1");

    private boolean optionalQualification = false;

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMNihdiIdField() {
        add(textField);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.withValueChangeListener(() -> {
            String modelValue = generateModelValue();
            setModelValue(modelValue, true);
            setInvalid(Strings.isNotBlank(textField.getValue()) && modelValue == null);
        });
    }

    public LCMNihdiIdField withOptionalQualification() {
        optionalQualification = true;
        textField.withPlaceHolder("xxxxxx-xx-(xxx)");
        return this;
    }

    @Override
    protected void updateValue() {
        String modelValue = this.generateModelValue();
        this.setModelValue(modelValue, true);
        setPresentationValue(modelValue);
    }

    @Override
    protected String generateModelValue() {
        String modelValue = textField.getValue();
        modelValue = Strings.removeAny(modelValue, "-", " ");

        if (Strings.isBlank(modelValue) || Strings.isNotUnsignedInteger(modelValue)) {
            return null;
        }

        if (Strings.lengthEqualsTo(modelValue, 11) || optionalQualification && Strings.lengthEqualsTo(modelValue, 8)) {
            return modelValue;
        }

        if (Strings.startsWith(modelValue, "0") && (Strings.lengthEqualsTo(modelValue, 12) || optionalQualification && Strings.lengthEqualsTo(modelValue, 9))) {
            return modelValue.substring(1);
        }

        if (Strings.length(modelValue) > 11) {
            return modelValue.substring(0, 11);
        }

        return null;
    }

    @Override
    protected void setPresentationValue(String modelValue) {
        if (Strings.isBlank(modelValue)) {
            textField.setValue(null);
        } else if (Strings.lengthEqualsTo(modelValue, 8)) {
            textField.setValue(modelValue.substring(0, 6) + "-" + modelValue.substring(6, 8) + "-");
        } else if (Strings.lengthEqualsTo(modelValue, 9)) {
            textField.setValue(modelValue.substring(1, 7) + "-" + modelValue.substring(7, 9) + "-");
        } else if (Strings.lengthEqualsTo(modelValue, 11)) {
            textField.setValue(modelValue.substring(0, 6) + "-" + modelValue.substring(6, 8) + "-" + modelValue.substring(8));
        } else if (Strings.lengthEqualsTo(modelValue, 12)) {
            textField.setValue(modelValue.substring(1, 7) + "-" + modelValue.substring(7, 9) + "-" + modelValue.substring(9));
        }
    }

    @Override
    public LCMNihdiIdField getFluent() {
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
