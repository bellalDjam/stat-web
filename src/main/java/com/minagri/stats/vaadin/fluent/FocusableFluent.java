package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Focusable;

public interface FocusableFluent<FLUENT extends Focusable> {

    default FLUENT withTabIndex(int tabIndex) {
        getFluent().setTabIndex(tabIndex);
        return getFluent();
    }

    default FLUENT withFocus() {
        getFluent().focus();
        return getFluent();
    }

    default FLUENT withBlur() {
        getFluent().blur();
        return getFluent();
    }

    FLUENT getFluent();
}
