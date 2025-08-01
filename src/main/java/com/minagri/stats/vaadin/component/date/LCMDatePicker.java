package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.control.LCMI18NProvider;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePickerVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LCMDatePicker extends DatePicker implements
        ComponentFluent<LCMDatePicker>,
        HasSizeFluent<LCMDatePicker>,
        HasValidationFluent<LCMDatePicker>,
        HasThemeFluent<LCMDatePicker>,
        HasStyleFluent<LCMDatePicker>,
        HasLabelFluent<LCMDatePicker>,
        HasValueFluent<LCMDatePicker, LocalDate>,
        HasPlaceholderFluent<LCMDatePicker> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private DatePicker.DatePickerI18n dutchI18n = new DatePickerI18n()
            .setMonthNames(List.of("januari", "februari", "maart", "april", "mei", "juni",
                    "juli", "augustus", "september", "oktober", "november", "december"))
            .setWeekdaysShort(List.of("zo", "ma", "di", "wo", "do", "vr", "za"))
            .setToday("Vandaag")
            .setCancel("Annuleren");
    private DatePicker.DatePickerI18n frenchI18n = new DatePickerI18n()
            .setMonthNames(List.of("janvier", "février", "mars", "avril", "mai", "juin",
                    "juillet", "août", "septembre", "octobre", "novembre", "décembre"))
            .setWeekdaysShort(List.of("dim", "lun", "mar", "mer", "jeu", "ven", "sam"))
            .setToday("Aujourd’hui")
            .setCancel("Annuler");

    public LCMDatePicker() {
        Locale currentLocale = VaadinSession.getCurrent().getLocale();
        if (LCMI18NProvider.LOCALE_FR.equals(currentLocale)) {
            setI18n(frenchI18n);
        } else {
            setI18n(dutchI18n);
        }

        addBlurListener(event -> {
            LocalDate dateValue = event.getSource().getValue();
            if (dateValue == null) {
                setValue(null);
            }
        });
    }

    @Override
    public LCMDatePicker getFluent() {
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
            setI18n(frenchI18n);
        } else {
            setI18n(dutchI18n);
        }
    }

    public LCMDatePicker withSmallTheme() {
        addThemeVariants(DatePickerVariant.LUMO_SMALL);
        return getFluent();
    }

    public LCMDatePicker withDashedFormat() {
        frenchI18n.setDateFormat("dd-MM-yyyy");
        dutchI18n.setDateFormat("dd-MM-yyyy");
        return this;
    }
}
