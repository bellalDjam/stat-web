package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.data.event.SortEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.shared.Registration;

import java.util.function.Consumer;

public interface IsGridFluent<FLUENT extends Grid<VALUECLASS>, VALUECLASS> {

    default FLUENT withColumnReorderingAllowed(boolean value) {
        getFluent().setColumnReorderingAllowed(value);
        return getFluent();
    }

    default FLUENT withColumnReorderingAllowed() {
        return withColumnReorderingAllowed(true);
    }

    default FLUENT withColumnReorderingDisallowed() {
        return withColumnReorderingAllowed(false);
    }

    default FLUENT withPageSize(int value) {
        getFluent().setPageSize(value);
        return getFluent();
    }

    default FLUENT withMultisort(boolean value) {
        getFluent().setMultiSort(value);
        return getFluent();
    }

    default FLUENT withMultisort() {
        return withMultisort(true);
    }

    default FLUENT withSelectionMode(Grid.SelectionMode selectionMode) {
        getFluent().setSelectionMode(selectionMode);
        return getFluent();
    }

    default FLUENT withSingleSelection() {
        return withSelectionMode(Grid.SelectionMode.SINGLE);
    }

    default FLUENT withSingleSelection(Consumer<VALUECLASS> selectionListener) {
        withSelectionMode(Grid.SelectionMode.SINGLE);
        withSelectionListener(event -> selectionListener.accept(event.getFirstSelectedItem().orElse(null)));
        return getFluent();
    }

    default FLUENT withSingleNonEmptySelection(Consumer<VALUECLASS> selectionListener) {
        withSelectionMode(Grid.SelectionMode.SINGLE);
        AbstractGridSingleSelectionModel<VALUECLASS> selectionModel = (AbstractGridSingleSelectionModel<VALUECLASS>) getFluent().getSelectionModel();
        selectionModel.setDeselectAllowed(false);
        withSelectionListener(event -> event.getFirstSelectedItem().ifPresent(selectionListener));
        return getFluent();
    }

    default FLUENT withMultipleSelection() {
        return withSelectionMode(Grid.SelectionMode.MULTI);
    }

    default FLUENT withNoSelection() {
        return withSelectionMode(Grid.SelectionMode.NONE);
    }

    default FLUENT withThemeVariants(GridVariant... variants) {
        getFluent().addThemeVariants(variants);
        return getFluent();
    }

    default FLUENT withRemoveThemeVariants(GridVariant... variants) {
        getFluent().removeThemeVariants(variants);
        return getFluent();
    }

    default FLUENT withItemClickListener(ComponentEventListener<ItemClickEvent<VALUECLASS>> listener) {
        withItemClickListenerRemovable(listener);
        return getFluent();
    }

    default Registration withItemClickListenerRemovable(ComponentEventListener<ItemClickEvent<VALUECLASS>> listener) {
        return getFluent().addItemClickListener(listener);
    }

    default FLUENT withItemDoubleClickListener(ComponentEventListener<ItemDoubleClickEvent<VALUECLASS>> listener) {
        withItemDoubleClickListenerRemovable(listener);
        return getFluent();
    }

    default Registration withItemDoubleClickListenerRemovable(ComponentEventListener<ItemDoubleClickEvent<VALUECLASS>> listener) {
        return getFluent().addItemDoubleClickListener(listener);
    }

    default FLUENT withSelectionListener(SelectionListener<Grid<VALUECLASS>, VALUECLASS> listener) {
        withSelectionListenerRemovable(listener);
        return getFluent();
    }

    default FLUENT withSelectionListener(Runnable listener) {
        withSelectionListenerRemovable(event -> listener.run());
        return getFluent();
    }

    default Registration withSelectionListenerRemovable(SelectionListener<Grid<VALUECLASS>, VALUECLASS> listener) {
        return getFluent().addSelectionListener(listener);
    }

    default FLUENT withSortListener(ComponentEventListener<SortEvent<Grid<VALUECLASS>, GridSortOrder<VALUECLASS>>> listener) {
        withSortListenerRemovable(listener);
        return getFluent();
    }

    default Registration withSortListenerRemovable(ComponentEventListener<SortEvent<Grid<VALUECLASS>, GridSortOrder<VALUECLASS>>> listener) {
        return getFluent().addSortListener(listener);
    }

    FLUENT getFluent();
}
