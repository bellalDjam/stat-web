package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.shared.HasClearButton;

public interface HasClearButtonFluent<FLUENT extends HasClearButton> {
    default FLUENT withClearButtonVisible(boolean bool) {
        getFluent().setClearButtonVisible(bool);
        return getFluent();
    }

    FLUENT getFluent();
}
