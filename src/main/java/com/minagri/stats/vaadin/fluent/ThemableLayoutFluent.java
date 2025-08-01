package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;

public interface ThemableLayoutFluent<FLUENT extends ThemableLayout> {

    default FLUENT withMargin(boolean margin) {
        getFluent().setMargin(margin);
        return getFluent();
    }

    default FLUENT withMargin() {
        return withMargin(true);
    }

    default FLUENT withNoMargin() {
        return withMargin(false);
    }

    default FLUENT withPadding(boolean padding) {
        getFluent().setPadding(padding);
        return getFluent();
    }

    default FLUENT withPadding() {
        return withPadding(true);
    }

    default FLUENT withNoPadding() {
        return withPadding(false);
    }

    default FLUENT withSpacing(boolean spacing) {
        getFluent().setSpacing(spacing);
        return getFluent();
    }

    default FLUENT withSpacing() {
        return withSpacing(true);
    }

    default FLUENT withNoSpacing() {
        return withSpacing(false);
    }

    default FLUENT withBoxSizing(BoxSizing boxSizing) {
        getFluent().setBoxSizing(boxSizing);
        return getFluent();
    }

    default FLUENT withTheme(String theme) {
        getFluent().getThemeList().add(theme);
        return getFluent();
    }

    FLUENT getFluent();
}
