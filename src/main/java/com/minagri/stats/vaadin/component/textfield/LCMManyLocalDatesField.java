package com.minagri.stats.vaadin.component.textfield;

import com.minagri.stats.vaadin.component.textfield.control.ManyLocalDatesConverter;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
public class LCMManyLocalDatesField extends TextArea implements
        ComponentFluent<LCMManyLocalDatesField>,
        HasSizeFluent<LCMManyLocalDatesField>,
        HasEnabledFluent<LCMManyLocalDatesField>,
        FocusableFluent<LCMManyLocalDatesField>,
        HasStyleFluent<LCMManyLocalDatesField>,
        HasThemeFluent<LCMManyLocalDatesField>,
        HasValueFluent<LCMManyLocalDatesField, String>,
        HasValidationFluent<LCMManyLocalDatesField>,
        HasValueChangeModeFluent<LCMManyLocalDatesField>,
        HasPrefixAndSuffixFluent<LCMManyLocalDatesField>,
        IsTextAreaFluent<LCMManyLocalDatesField>,
        HasPlaceholderFluent<LCMManyLocalDatesField> {

    private static final ManyLocalDatesConverter CONVERTER = new ManyLocalDatesConverter();

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private boolean avoidValueChangeListener = false;

    {
        addValueChangeListener(event -> {
            if (!event.isFromClient()) {
                return;
            }
            this.avoidValueChangeListener = true;
            try {
                setValue(CONVERTER.format(event.getValue()));
            } finally {
                this.avoidValueChangeListener = false;
            }
        });
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> initialListener) {
        return super.addValueChangeListener(e -> {
            if (this.avoidValueChangeListener) {
                return;
            }
            initialListener.valueChanged(e);
        });
    }

    public void setValueAsListOfLocalDate(List<LocalDate> dates) {
        super.setValue(CONVERTER.convertToPresentation(dates, null));
    }

    public List<LocalDate> getValueAsListOfLocalDate() {
        List<LocalDate> localDates = new ArrayList<>();
        CONVERTER.convertToModel(getValue(), null).ifOk(dates -> localDates.addAll(dates));
        return localDates;
    }

    public Optional<List<LocalDate>> getOptionalValueAsListOfLocalDate() {
        if (!getOptionalValue().isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(getValueAsListOfLocalDate());
    }

    @Override
    public LCMManyLocalDatesField getFluent() {
        return this;
    }
}
