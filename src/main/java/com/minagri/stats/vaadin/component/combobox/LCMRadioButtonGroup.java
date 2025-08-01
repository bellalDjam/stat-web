package com.minagri.stats.vaadin.component.combobox;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.i18n.LocaleChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class LCMRadioButtonGroup<T> extends RadioButtonGroup<T> implements
        ComponentFluent<LCMRadioButtonGroup<T>>,
        HasSizeFluent<LCMRadioButtonGroup<T>>,
        HasEnabledFluent<LCMRadioButtonGroup<T>>,
        HasStyleFluent<LCMRadioButtonGroup<T>>,
        HasValueFluent<LCMRadioButtonGroup<T>, T>,
        HasThemeFluent<LCMRadioButtonGroup<T>>,
        HasLabelFluent<LCMRadioButtonGroup<T>>,
        HasListDataViewFluent<LCMRadioButtonGroup<T>, T> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMRadioButtonGroup<T> getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        HasLabelFluent.super.localeChange(event);
        setItemLabelGenerator(getItemLabelGenerator());
    }

    public LCMRadioButtonGroup<T> withItemLabelGenerator(ItemLabelGenerator<T> itemLabelGenerator) {
        setItemLabelGenerator(itemLabelGenerator);
        return this;
    }

    public LCMRadioButtonGroup<T> withFirstItemSelected() {
        if (getListDataView().getItemCount() > 0) {
            setValue(getFluent().getListDataView().getItem(0));
        }
        return this;
    }
}
