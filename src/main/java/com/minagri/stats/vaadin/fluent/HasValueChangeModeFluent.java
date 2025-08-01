package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;

public interface HasValueChangeModeFluent<FLUENT extends HasValueChangeMode> {

    default FLUENT withValueChangeMode(ValueChangeMode valueChangeMode) {
        getFluent().setValueChangeMode(valueChangeMode);
        return getFluent();
    }

    FLUENT getFluent();
}
