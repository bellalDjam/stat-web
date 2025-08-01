package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.component.textfield.AbstractNumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.function.SerializableFunction;

import java.util.HashMap;
import java.util.Map;

@Tag("vaadin-integer-field")
@NpmPackage(value = "@vaadin/polymer-legacy-adapter", version = "24.8.3") // keep in line with vaadin IntegerField version
@NpmPackage(value = "@vaadin/integer-field", version = "24.8.3") // keep in line with vaadin IntegerField version
@JsModule("@vaadin/polymer-legacy-adapter/style-modules.js")
@JsModule("@vaadin/integer-field/src/vaadin-integer-field.js")
public class LCMLongField extends AbstractNumberField<LCMLongField, Long> implements ComponentFluent<LCMLongField>,
        HasSizeFluent<LCMLongField>,
        HasEnabledFluent<LCMLongField>,
        FocusableFluent<LCMLongField>,
        HasStyleFluent<LCMLongField>,
        HasThemeVariant<TextFieldVariant>,
        HasThemeFluent<LCMLongField>,
        HasValueFluent<LCMLongField, Long>,
        HasValidationFluent<LCMLongField>,
        HasValueChangeModeFluent<LCMLongField>,
        HasPrefixAndSuffixFluent<LCMLongField>,
        HasLabelFluent<LCMLongField>,
        IsLongFieldFluent<LCMLongField>,
        HasPlaceholderFluent<LCMLongField> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    private static final SerializableFunction<String, Long> PARSER = valueFormClient -> {
        if (valueFormClient == null || valueFormClient.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(valueFormClient);
        } catch (NumberFormatException e) {
            return null;
        }
    };

    private static final SerializableFunction<Long, String> FORMATTER = valueFromModel -> valueFromModel == null
            ? ""
            : valueFromModel.toString();

    public LCMLongField() {
        super(PARSER, FORMATTER, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Override
    public LCMLongField getFluent() {
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

    public void setMin(long min) {
        super.setMin(min);
    }

    public long getMin() {
        return (long) getMinDouble();
    }

    public void setMax(long max) {
        super.setMax(max);
    }

    public long getMax() {
        return (long) getMaxDouble();
    }
}
