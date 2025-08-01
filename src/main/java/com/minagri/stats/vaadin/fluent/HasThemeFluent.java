package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.HasTheme;

public interface HasThemeFluent<FLUENT extends HasTheme> {

    default FLUENT withThemeName(String themeName) {
        getFluent().addThemeName(themeName);
        return getFluent();
    }

    default FLUENT withThemeNames(String... themeNames) {
        getFluent().addThemeNames(themeNames);
        return getFluent();
    }

    default FLUENT withSingleThemeName(String themeName) {
        getFluent().setThemeName(themeName);
        return getFluent();
    }

    default FLUENT withRemoveThemeName(String themeName) {
        getFluent().removeThemeName(themeName);
        return getFluent();
    }

    default FLUENT withRemoveThemeNames(String... themNames) {
        getFluent().removeThemeNames(themNames);
        return getFluent();
    }


    FLUENT getFluent();
}
