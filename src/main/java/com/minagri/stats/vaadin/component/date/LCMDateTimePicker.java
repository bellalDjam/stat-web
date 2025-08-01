package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.control.LCMI18NProvider;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LCMDateTimePicker extends DateTimePicker implements
        ComponentFluent<LCMDateTimePicker>,
        HasSizeFluent<LCMDateTimePicker>,
        HasValidationFluent<LCMDateTimePicker>,
        HasThemeFluent<LCMDateTimePicker>,
        HasStyleFluent<LCMDateTimePicker>,
        HasLabelFluent<LCMDateTimePicker>,
        HasValueFluent<LCMDateTimePicker, LocalDateTime> {
    
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private DatePicker.DatePickerI18n dutchI18n = new DatePicker.DatePickerI18n()
            .setMonthNames(List.of("januari", "februari", "maart", "april", "mei", "juni",
                    "juli", "augustus", "september", "oktober", "november", "december"))
            .setWeekdaysShort(List.of("zo", "ma", "di", "wo", "do", "vr", "za"))
            .setToday("Vandaag")
            .setCancel("Annuleren");
    private DatePicker.DatePickerI18n frenchI18n = new DatePicker.DatePickerI18n()
            .setMonthNames(List.of("janvier", "février", "mars", "avril", "mai", "juin",
                    "juillet", "août", "septembre", "octobre", "novembre", "décembre"))
            .setWeekdaysShort(List.of("dim", "lun", "mar", "mer", "jeu", "ven", "sam"))
            .setToday("Aujourd’hui")
            .setCancel("Annuler");

    public LCMDateTimePicker() {
        Locale currentLocale = VaadinSession.getCurrent().getLocale();
        if (LCMI18NProvider.LOCALE_FR.equals(currentLocale)) {
            setDatePickerI18n(frenchI18n);
        } else {
            setDatePickerI18n(dutchI18n);
        }
    }

    @Override
    public LCMDateTimePicker getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        HasValidationFluent.super.localeChange(event);

        if (event.getLocale() == LCMI18NProvider.LOCALE_FR) {
            setDatePickerI18n(frenchI18n);
        } else {
            setDatePickerI18n(dutchI18n);
        }
    }

    public LCMDateTimePicker withSmallTheme() {
        addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
        return getFluent();
    }

    public LCMDateTimePicker withDashedFormat() {
        frenchI18n.setDateFormat("dd-MM-yyyy");
        dutchI18n.setDateFormat("dd-MM-yyyy");
        return this;
    }
}
