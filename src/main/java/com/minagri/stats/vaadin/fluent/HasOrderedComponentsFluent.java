package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasOrderedComponents;

public interface HasOrderedComponentsFluent<FLUENT extends HasOrderedComponents> {

    default FLUENT withReplace(Component oldComponent, Component newComponent) {
        getFluent().replace(oldComponent, newComponent);
        return getFluent();
    }

    FLUENT getFluent();
}
