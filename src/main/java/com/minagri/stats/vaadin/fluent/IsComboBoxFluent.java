package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.vaadin.component.combobox.LCMComboBox;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBoxVariant;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.Renderer;

import java.util.List;

public interface IsComboBoxFluent<FLUENT extends LCMComboBox<VALUECLASS>, VALUECLASS> extends HasTranslation {

    default FLUENT withRequired(boolean value) {
        getFluent().setRequired(value);
        return getFluent();
    }

    default FLUENT withRequired() {
        return withRequired(true);
    }

    default FLUENT withPageSize(int value) {
        getFluent().setPageSize(value);
        return getFluent();
    }

    default FLUENT withInvalid(boolean value) {
        getFluent().setInvalid(value);
        return getFluent();
    }

    default FLUENT withInvalid() {
        return withInvalid(true);
    }

    default FLUENT withValid() {
        return withInvalid(false);
    }

    default FLUENT withItemLabelGenerator(ItemLabelGenerator<VALUECLASS> itemLabelGenerator) {
        getFluent().setItemLabelGenerator(itemLabelGenerator);
        return getFluent();
    }

    default <T extends Enum<?>> FLUENT withItemLabelFromBundle(T bundle) {
        return withItemLabelFromBundle(Enums.getName(bundle));
    }

    default <T extends Enum<?>> FLUENT withItemLabelFromBundle(String bundle) {
        getFluent().setItemLabelGenerator(item -> Translator.translateFromBundle(bundle, item));
        return getFluent();
    }

    default FLUENT withSortByLabel() {
        return withSortByLabel(SortDirection.ASCENDING);
    }

    default FLUENT withSortByLabel(SortDirection sortDirection) {
        getFluent().getListDataView().setSortOrder(item -> getFluent().getItemLabelGenerator().apply(item), sortDirection);
        return getFluent();
    }

    default FLUENT withFirstItemSelected() {
        getFluent().selectFirstItem();
        return getFluent();
    }

    default FLUENT withErrorMessage(String errorMessage) {
        removeTranslationLinkedToMethod("setErrorMessage");
        getFluent().setErrorMessage(errorMessage);
        return getFluent();
    }

    default FLUENT withErrorMessage(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setErrorMessage", key, messageParameters);
        getFluent().setErrorMessage(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withErrorMessageKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setErrorMessage", key, messageParameters);
        getFluent().setErrorMessage(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withRenderer(Renderer<VALUECLASS> renderer) {
        getFluent().setRenderer(renderer);
        return getFluent();
    }

    @SuppressWarnings({"unchecked", "varargs"})
    default FLUENT withItems(VALUECLASS... items) {
        getFluent().setItems(items);
        return getFluent();
    }

    default FLUENT withItems(List<VALUECLASS> items) {
        getFluent().setItems(items);
        return getFluent();
    }

    default FLUENT withSmallTheme() {
        getFluent().addThemeVariants(ComboBoxVariant.LUMO_SMALL);
        return getFluent();
    }

    FLUENT getFluent();
}
