package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.component.grid.LCMGrid;
import com.vaadin.flow.component.HasEnabled;

public interface HasEnabledFluent<FLUENT extends HasEnabled> {

    default FLUENT withEnabled(boolean value) {
        getFluent().setEnabled(value);
        return getFluent();
    }

    default FLUENT withEnabled() {
        return withEnabled(true);
    }

    default FLUENT withDisabled() {
        return withEnabled(false);
    }

    default FLUENT withDisabledOnEmptyGridSelection(LCMGrid<?> grid) {
        getFluent().setEnabled(!grid.getSelectedItems().isEmpty());
        grid.addSelectionListener(event -> getFluent().setEnabled(!grid.getSelectedItems().isEmpty()));
        return getFluent();
    }

    FLUENT getFluent();
}
