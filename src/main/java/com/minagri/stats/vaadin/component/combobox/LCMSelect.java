package com.minagri.stats.vaadin.component.combobox;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.LocaleChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class LCMSelect<VALUECLASS> extends Select<VALUECLASS> implements
        ComponentFluent<LCMSelect<VALUECLASS>>,
        HasSizeFluent<LCMSelect<VALUECLASS>>,
        HasEnabledFluent<LCMSelect<VALUECLASS>>,
        FocusableFluent<LCMSelect<VALUECLASS>>,
        HasStyleFluent<LCMSelect<VALUECLASS>>,
        HasValueFluent<LCMSelect<VALUECLASS>, VALUECLASS>,
        HasThemeFluent<LCMSelect<VALUECLASS>>,
        HasLabelFluent<LCMSelect<VALUECLASS>>,
        IsSelectFluent<LCMSelect<VALUECLASS>, VALUECLASS>,
        HasPlaceholderFluent<LCMSelect<VALUECLASS>> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMSelect<VALUECLASS> getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    public void selectFirstItem() {
        if (getListDataView().getItemCount() > 0) {
            setValue(getFluent().getListDataView().getItem(0));
        }
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        HasLabelFluent.super.localeChange(event);
        setItemLabelGenerator(getItemLabelGenerator());
    }

    @Override
    public void clear() {
        super.clear();
        setInvalid(false);
    }
}
