package com.minagri.stats.vaadin.component.combobox;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.HashMap;
import java.util.Map;

public class LCMComboBox<VALUECLASS> extends ComboBox<VALUECLASS> implements
        ComponentFluent<LCMComboBox<VALUECLASS>>,
        HasSizeFluent<LCMComboBox<VALUECLASS>>,
        HasEnabledFluent<LCMComboBox<VALUECLASS>>,
        FocusableFluent<LCMComboBox<VALUECLASS>>,
        HasStyleFluent<LCMComboBox<VALUECLASS>>,
        HasValueFluent<LCMComboBox<VALUECLASS>, VALUECLASS>,
        HasThemeFluent<LCMComboBox<VALUECLASS>>,
        HasClearButtonFluent<LCMComboBox<VALUECLASS>>,
        HasLabelFluent<LCMComboBox<VALUECLASS>>,
        IsComboBoxFluent<LCMComboBox<VALUECLASS>, VALUECLASS>,
        HasPlaceholderFluent<LCMComboBox<VALUECLASS>> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMComboBox<VALUECLASS> getFluent() {
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
}
