package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.vaadin.component.combobox.LCMSelect;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.select.SelectVariant;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface IsSelectFluent<FLUENT extends LCMSelect<VALUECLASS>, VALUECLASS> extends HasTranslation {

    default FLUENT withEmptySelectionAllowed(boolean emptySelectionAllowed) {
        getFluent().setEmptySelectionAllowed(emptySelectionAllowed);
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

    default FLUENT withLevelRenderer(Function<VALUECLASS, Integer> levelProvider) {
        FLUENT fluent = getFluent();
        fluent.setRenderer(new ComponentRenderer<Component, VALUECLASS>(item -> {
            ItemLabelGenerator<VALUECLASS> itemLabelGenerator = fluent.getItemLabelGenerator();
            String label = itemLabelGenerator != null ? itemLabelGenerator.apply(item) : Objects.toString(item);
            Integer level = levelProvider.apply(item);
            
            return new LCMSpan()
                    .withText(label)
                    .setPaddingLeft(level + "em");
        }));
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

    default FLUENT withRenderer(ComponentRenderer<? extends Component, VALUECLASS> renderer) {
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
        getFluent().addThemeVariants(SelectVariant.LUMO_SMALL);
        return getFluent();
    }

    default FLUENT withFirstItemSelected() {
        getFluent().selectFirstItem();
        return getFluent();
    }

    FLUENT getFluent();
}
