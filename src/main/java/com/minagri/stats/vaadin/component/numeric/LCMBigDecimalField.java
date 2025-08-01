package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.core.java.BigDecimals;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.LCMTextField;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasPlaceholder;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class LCMBigDecimalField extends CustomField<BigDecimal> implements ComponentFluent<LCMBigDecimalField>,
        HasSizeFluent<LCMBigDecimalField>,
        HasEnabledFluent<LCMBigDecimalField>,
        FocusableFluent<LCMBigDecimalField>,
        HasStyleFluent<LCMBigDecimalField>,
        HasThemeFluent<LCMBigDecimalField>,
        HasValueFluent<LCMBigDecimalField, BigDecimal>,
        HasValidationFluent<LCMBigDecimalField>,
        HasLabelFluent<LCMBigDecimalField>,
        HasPlaceholder,
        HasPlaceholderFluent<LCMBigDecimalField>,
        HasSuffix,
        HasPrefix,
        HasPrefixAndSuffixFluent<LCMBigDecimalField> {

    private Integer scale = 2;
    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    private LCMTextField textField = new LCMTextField()
            .withStyle("flex", "1");

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMBigDecimalField() {
        add(textField);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.withValueChangeListener(() -> {
            BigDecimal modelValue = generateModelValue();
            setModelValue(modelValue, true);
            setInvalid(Strings.isNotBlank(textField.getValue()) && modelValue == null);
        });
    }

    @Override
    protected void updateValue() {
        BigDecimal modelValue = this.generateModelValue();
        this.setModelValue(modelValue, true);
        setPresentationValue(modelValue);
    }

    @Override
    protected BigDecimal generateModelValue() {
        String inputValue = textField.getValue();
        inputValue = Strings.nullToEmpty(inputValue);
        inputValue = inputValue.replace(",", ".");

        BigDecimal modelValue = Strings.toBigDecimal(inputValue);
        if (modelValue == null) {
            return null;
        }
        
        modelValue = modelValue.setScale(scale, roundingMode);
        return modelValue;
    }

    @Override
    protected void setPresentationValue(BigDecimal modelValue) {
        if (modelValue == null) {
            textField.setValue(null);
        } else {
            modelValue = modelValue.setScale(scale, roundingMode);
            textField.setValue(BigDecimals.toString(modelValue).replace(Strings.DOT, Strings.COMMA));
        }
    }

    public LCMBigDecimalField withScale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public LCMBigDecimalField withRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }

    @Override
    public LCMBigDecimalField getFluent() {
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

    @Override
    public Component getSuffixComponent() {
        return textField.getSuffixComponent();
    }

    @Override
    public void setSuffixComponent(Component component) {
        textField.setSuffixComponent(component);
    }

    @Override
    public Component getPrefixComponent() {
        return textField.getPrefixComponent();
    }
    
    @Override
    public void setPrefixComponent(Component component) {
        textField.setPrefixComponent(component);
    }
}
