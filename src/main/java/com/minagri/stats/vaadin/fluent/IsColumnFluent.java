package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrderBuilder;
import com.vaadin.flow.component.grid.SortOrderProvider;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

import java.util.Comparator;

public interface IsColumnFluent<FLUENT extends Grid.Column<VALUECLASS>, VALUECLASS> extends HasTranslation {

    default FLUENT withComparator(Comparator<VALUECLASS> comparator) {
        getFluent().setComparator(comparator);
        return getFluent();
    }

    default <V extends Comparable<? super V>> FLUENT withComparator(ValueProvider<VALUECLASS, V> keyExtractor) {
        getFluent().setComparator(keyExtractor);
        return getFluent();
    }

    default FLUENT withEditorComponent(Component editorComponent) {
        getFluent().setEditorComponent(editorComponent);
        return getFluent();
    }

    default FLUENT withEditorComponent(SerializableFunction<VALUECLASS, ? extends Component> componentCallback) {
        getFluent().setEditorComponent(componentCallback);
        return getFluent();
    }

    default FLUENT withFlexGrow(int flexGrow) {
        getFluent().setFlexGrow(flexGrow);
        return getFluent();
    }

    default FLUENT withFooter(String labelText) {
        removeTranslationLinkedToMethod("setFooter");
        getFluent().setFooter(labelText);
        return getFluent();
    }

    default FLUENT withFooter(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setFooter", key, messageParameters);
        getFluent().setFooter(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withFooterKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setFooter", key, messageParameters);
        getFluent().setFooter(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withFooterKey(Translation translation) {
        addTranslationLinkedToMethod("setFooter", translation);
        getFluent().setFooter(Translator.translate(translation.getKey(), translation.getMessageParameters()));
        return getFluent();
    }

    default FLUENT withFooter(Component footerComponent) {
        getFluent().setFooter(footerComponent);
        return getFluent();
    }

    default FLUENT withFrozen(boolean value) {
        getFluent().setFrozen(value);
        return getFluent();
    }

    default FLUENT withFrozen() {
        return withFrozen(true);
    }

    default FLUENT withHeader(String labelText) {
        removeTranslationLinkedToMethod("setHeader");
        getFluent().setHeader(labelText);
        return getFluent();
    }

    default FLUENT withHeader(Component headerComponent) {
        getFluent().setHeader(headerComponent);
        return getFluent();
    }

    default FLUENT withHeader(Enum<?> key, Object... messageParameters) {
        addTranslationLinkedToMethod("setHeader", key, messageParameters);
        getFluent().setHeader(Translator.translate(key, messageParameters));
        return getFluent();
    }

    default FLUENT withHeaderKey(TranslationKey key, Object... messageParameters) {
        addTranslationKeyLinkedToMethod("setHeader", key, messageParameters);
        getFluent().setHeader(Translator.translateKey(key, messageParameters));
        return getFluent();
    }

    default FLUENT withKey(String key) {
        getFluent().setKey(key);
        return getFluent();
    }

    default FLUENT withResizable(boolean value) {
        getFluent().setResizable(value);
        return getFluent();
    }

    default FLUENT withResizable() {
        return withResizable(true);
    }

    default FLUENT withSortable(boolean value) {
        getFluent().setSortable(value);
        return getFluent();
    }

    default FLUENT withSortable() {
        return withSortable(true);
    }

    @SuppressWarnings("unchecked")
    default FLUENT withDefaultSortable() {
        FLUENT column = getFluent();
        Grid<VALUECLASS> grid = (Grid<VALUECLASS>) column.getGrid();
        grid.sort(new GridSortOrderBuilder<VALUECLASS>().thenAsc(column).build());
        return column;
    }

    default FLUENT withDefaultDescendingSortable() {
        FLUENT column = getFluent();
        Grid<VALUECLASS> grid = (Grid<VALUECLASS>) column.getGrid();
        grid.sort(new GridSortOrderBuilder<VALUECLASS>().thenDesc(column).build());
        return column;
    }

    default FLUENT withSortOrderProvider(SortOrderProvider provider) {
        getFluent().setSortOrderProvider(provider);
        return getFluent();
    }

    default FLUENT withSortProperty(String... properties) {
        getFluent().setSortProperty(properties);
        return getFluent();
    }

    @SuppressWarnings("unchecked")
    default FLUENT withDefaultAscendingSortProperty(String... properties) {
        FLUENT column = getFluent();
        Grid<VALUECLASS> grid = (Grid<VALUECLASS>) column.getGrid();
        
        column.setSortProperty(properties);
        grid.sort(new GridSortOrderBuilder<VALUECLASS>().thenAsc(column).build());
        return column;
    }

    @SuppressWarnings("unchecked")
    default FLUENT withDefaultDescendingSortProperty(String... properties) {
        FLUENT column = getFluent();
        Grid<VALUECLASS> grid = (Grid<VALUECLASS>) column.getGrid();

        column.setSortProperty(properties);
        grid.sort(new GridSortOrderBuilder<VALUECLASS>().thenDesc(column).build());
        return column;
    }
    
    default FLUENT withTextAlign(ColumnTextAlign textAlign) {
        getFluent().setTextAlign(textAlign);
        return getFluent();
    }

    default FLUENT withTextAlignCenter() {
        return withTextAlign(ColumnTextAlign.CENTER);
    }

    default FLUENT withTextAlignStart() {
        return withTextAlign(ColumnTextAlign.START);
    }

    default FLUENT withTextAlignEnd() {
        return withTextAlign(ColumnTextAlign.END);
    }

    default FLUENT withWidth(String width) {
        getFluent().setWidth(width);
        return getFluent();
    }

    default FLUENT withWidthInPixels(Integer pixels) {
        return withWidth(pixels + "px");
    }

    default FLUENT withAutoWidth() {
        return withAutoWidth(true);
    }

    default FLUENT withAutoWidth(boolean autoWidth) {
        getFluent().setAutoWidth(autoWidth);
        return getFluent();
    }

    FLUENT getFluent();
}
