package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.HasSize;

public interface HasSizeFluent<FLUENT extends HasSize> {

    default FLUENT withWidth(String width) {
        getFluent().setWidth(width);
        return getFluent();
    }

    default FLUENT withHeight(String height) {
        getFluent().setHeight(height);
        return getFluent();
    }

    default FLUENT withWidthFull() {
        getFluent().setWidthFull();
        return getFluent();
    }

    default FLUENT withHeightFull() {
        getFluent().setHeightFull();
        return getFluent();
    }

    default FLUENT withWidthUndefined() {
        return withWidth(null);
    }

    default FLUENT withHeightUndefined() {
        return withHeight(null);
    }

    default FLUENT withHeightUnset() {
        return withHeight("unset");
    }

    default FLUENT withSizeFull() {
        getFluent().setSizeFull();
        return getFluent();
    }

    default FLUENT withSizeUndefined() {
        getFluent().setSizeUndefined();
        return getFluent();
    }

    default FLUENT withWidthInPixels(Integer pixels) {
        return withWidth(pixels + "px");
    }

    FLUENT getFluent();
}
