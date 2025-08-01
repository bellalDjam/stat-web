package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.textfield.TextArea;

public interface IsTextAreaFluent<FLUENT extends TextArea> extends HasTranslation {

    default FLUENT withAutofocus(boolean autofocus) {
        getFluent().setAutofocus(autofocus);
        return getFluent();
    }

    default FLUENT withAutofocus() {
        return withAutofocus(true);
    }

    default FLUENT withNoAutofocus() {
        return withAutofocus(false);
    }

    default FLUENT withAutoselect(boolean autoselect) {
        getFluent().setAutoselect(autoselect);
        return getFluent();
    }

    default FLUENT withAutoselect() {
        return withAutoselect(true);
    }

    default FLUENT withNoAutoselect() {
        return withAutoselect(false);
    }

    default FLUENT withClearButtonVisible(boolean clearButtonVisible) {
        getFluent().setClearButtonVisible(clearButtonVisible);
        return getFluent();
    }

    default FLUENT withClearButtonVisible() {
        return withClearButtonVisible(true);
    }

    default FLUENT withNoClearButtonVisible() {
        return withClearButtonVisible(false);
    }

    default FLUENT withMaxLength(int maxLength) {
        getFluent().setMaxLength(maxLength);
        return getFluent();
    }

    default FLUENT withMinLength(int minLength) {
        getFluent().setMinLength(minLength);
        return getFluent();
    }

    default FLUENT withRequired(boolean required) {
        getFluent().setRequired(required);
        return getFluent();
    }

    default FLUENT withRequired() {
        return withRequired(true);
    }

    default FLUENT withNotRequired() {
        return withRequired(false);
    }

    FLUENT getFluent();

}
