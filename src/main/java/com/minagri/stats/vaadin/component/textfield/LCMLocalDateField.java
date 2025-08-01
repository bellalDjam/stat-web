package com.minagri.stats.vaadin.component.textfield;

import com.minagri.stats.vaadin.component.textfield.control.LocalDateConverter;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
public class LCMLocalDateField extends TextField implements
        ComponentFluent<LCMLocalDateField>,
        HasSizeFluent<LCMLocalDateField>,
        HasEnabledFluent<LCMLocalDateField>,
        FocusableFluent<LCMLocalDateField>,
        HasStyleFluent<LCMLocalDateField>,
        HasThemeFluent<LCMLocalDateField>,
        HasValueFluent<LCMLocalDateField, String>,
        HasValidationFluent<LCMLocalDateField>,
        HasValueChangeModeFluent<LCMLocalDateField>,
        HasPrefixAndSuffixFluent<LCMLocalDateField>,
        IsTextFieldFluent<LCMLocalDateField>,
        HasPlaceholderFluent<LCMLocalDateField> {

    private static final LocalDateConverter CONVERTER = new LocalDateConverter();
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    private boolean avoidValueChangeListener = false;

    {
        addValueChangeListener(event -> {
            if (!event.isFromClient()) {
                return;
            }
            LocalDate newValue = parseSafe(event.getValue());
            if (newValue != null) {
                this.avoidValueChangeListener = true;
                try {
                    setValueAsLocalDate(newValue);
                } finally {
                    this.avoidValueChangeListener = false;
                }
            }
        });
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> initialListener) {
        return super.addValueChangeListener(e -> {
            if (this.avoidValueChangeListener) {
                return;
            }
            initialListener.valueChanged(e);
        });
    }

    public void setValueAsLocalDate(LocalDate value) {
        super.setValue(CONVERTER.convertToPresentation(value, null));
    }

    public LocalDate getValueAsLocalDate() {
        return parseSafe(getValue());
    }

    private LocalDate parseSafe(String value) {
        return CONVERTER.convertToModel(getValue(), null).getValue().orElse(null);
    }

    public Optional<LocalDate> getOptionalValueAsLocalDate() {
        if (!getOptionalValue().isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(getValueAsLocalDate());
    }

    @Override
    public LCMLocalDateField getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }
}

