package com.minagri.stats.vaadin.component.combobox;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;

import java.util.*;

public class LCMMultiSelectComboBox<VALUECLASS> extends MultiSelectComboBox<VALUECLASS> implements
        ComponentFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasSizeFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasEnabledFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        FocusableFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasStyleFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasValueFluent<LCMMultiSelectComboBox<VALUECLASS>, Set<VALUECLASS>>,
        HasThemeFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasClearButtonFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        HasLabelFluent<LCMMultiSelectComboBox<VALUECLASS>>,
        IsMultiSelectComboBoxFluent<LCMMultiSelectComboBox<VALUECLASS>, VALUECLASS>,
        HasPlaceholderFluent<LCMMultiSelectComboBox<VALUECLASS>> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMMultiSelectComboBox<VALUECLASS> getFluent() {
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
    
    public List<VALUECLASS> getValueAsList() {
        return List.copyOf(getValue());
    }

    @Override
    public ComboBoxListDataView<VALUECLASS> setItems(Collection<VALUECLASS> valueclasses) {
        Set<VALUECLASS> selectedItems = getSelectedItems();
        ComboBoxListDataView<VALUECLASS> dataView = super.setItems(valueclasses);
        select(selectedItems);
        return dataView;
    }
}
