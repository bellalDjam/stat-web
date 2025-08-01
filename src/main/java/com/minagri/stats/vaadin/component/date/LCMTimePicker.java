package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.timepicker.TimePickerVariant;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class LCMTimePicker extends TimePicker implements
        HasSizeFluent<LCMTimePicker>,
        HasValidationFluent<LCMTimePicker>,
        HasThemeFluent<LCMTimePicker>,
        HasStyleFluent<LCMTimePicker>,
        HasValueFluent<LCMTimePicker, LocalTime> {
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMTimePicker getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

    public LCMTimePicker withSmallTheme() {
        addThemeVariants(TimePickerVariant.LUMO_SMALL);
        return getFluent();
    }
}
