package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.core.java.Dates;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.LCMTextField;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class LCMMonthYearField extends CustomField<YearMonth> implements
        ComponentFluent<LCMMonthYearField>,
        HasSizeFluent<LCMMonthYearField>,
        HasEnabledFluent<LCMMonthYearField>,
        FocusableFluent<LCMMonthYearField>,
        HasStyleFluent<LCMMonthYearField>,
        HasValueFluent<LCMMonthYearField, YearMonth>,
        HasValidationFluent<LCMMonthYearField>,
        HasLabelFluent<LCMMonthYearField> {

    private LCMTextField textField = new LCMTextField()
            .withPlaceHolder("mm/yyyy")
            .withStyle("flex", "1");

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMMonthYearField() {
        add(textField);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.withValueChangeListener(() -> {
            YearMonth modelValue = generateModelValue();
            setModelValue(modelValue, true);
            setInvalid(Strings.isNotBlank(textField.getValue()) && modelValue == null);
        });
    }

    @Override
    protected void updateValue() {
        YearMonth modelValue = this.generateModelValue();
        this.setModelValue(modelValue, true);
        setPresentationValue(modelValue);
    }

    @Override
    protected YearMonth generateModelValue() {
        String inputValue = textField.getValue();
        inputValue = Strings.nullToEmpty(inputValue);
        inputValue = inputValue.replace("-", "/");

        YearMonth modelValue = Dates.safeParseYearMonth(Strings.leftPadZero(inputValue, 6), Dates.MMYYYY);
        if (modelValue == null) {
            modelValue = Dates.safeParseYearMonth(Strings.leftPadZero(inputValue, 7), Dates.MMYYYY_SLASHED);
        }

        return modelValue;
    }

    @Override
    protected void setPresentationValue(YearMonth modelValue) {
        textField.setValue(Dates.safeFormat(modelValue, Dates.MMYYYY_SLASHED));
    }

    @Override
    public LCMMonthYearField getFluent() {
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
