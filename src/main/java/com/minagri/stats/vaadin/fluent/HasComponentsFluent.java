package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

public interface HasComponentsFluent<FLUENT extends HasComponents> {

    default FLUENT withRemove(Component... components) {
        getFluent().remove(components);
        return getFluent();
    }

    default FLUENT withAdd(Component... components) {
        getFluent().add(components);
        return getFluent();
    }

    default FLUENT withAddComponentAsFirst(Component component) {
        getFluent().addComponentAsFirst(component);
        return getFluent();
    }

    default FLUENT withAddComponentAtIndex(int index, Component component) {
        getFluent().addComponentAtIndex(index, component);
        return getFluent();
    }

    default FLUENT withRemoveAll() {
        getFluent().removeAll();
        return getFluent();
    }

    FLUENT getFluent();
}
