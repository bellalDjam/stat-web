package com.minagri.stats.vaadin.component.checkbox;

import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.checkbox.Checkbox;

import java.util.HashMap;
import java.util.Map;

public class LCMCheckbox extends Checkbox implements ComponentFluentAuthorized<LCMCheckbox>, HasSizeFluent<LCMCheckbox>, HasEnabledFluent<LCMCheckbox>, FocusableFluent<LCMCheckbox>, HasStyleFluent<LCMCheckbox>, ClickNotifierFluent<LCMCheckbox>, HasValueFluent<LCMCheckbox, Boolean>, HasLabelFluent<LCMCheckbox>, HasTranslation {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private Action action;

    public LCMCheckbox autoFocus(boolean value) {
        setAutofocus(value);
        return getFluent();
    }

    public LCMCheckbox autoFocus() {
        return autoFocus(true);
    }

    public LCMCheckbox indeterminate(boolean value) {
        setIndeterminate(value);
        return getFluent();
    }

    public LCMCheckbox indeterminate() {
        return indeterminate(true);
    }

    public LCMCheckbox withValue(Boolean value) {
        setValue(value);
        return getFluent();
    }

    public LCMCheckbox check() {
        return withValue(true);
    }

    public LCMCheckbox uncheck() {
        return withValue(false);
    }

    @Override
    public LCMCheckbox getFluent() {
        return this;
    }

    @Override
    public LCMCheckbox setAction(Action action) {
        this.action = action;
        return getFluent();
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
