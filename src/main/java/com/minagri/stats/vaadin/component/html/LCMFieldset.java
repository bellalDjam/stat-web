package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

import java.util.HashMap;
import java.util.Map;

@Tag("fieldset")
public class LCMFieldset extends HtmlContainer implements 
        ClickNotifier<LCMFieldset>,
        ComponentFluent<LCMFieldset>,
        HasSizeFluent<LCMFieldset>,
        HasEnabledFluent<LCMFieldset>,
        HasTextFluent<LCMFieldset>,
        HasStyleFluent<LCMFieldset>,
        HtmlComponentFluent<LCMFieldset>,
        HasComponentsFluent<LCMFieldset>,
        ClickNotifierFluent<LCMFieldset> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMFieldset withLegend(LCMLegend legend) {
        add(legend);
        return getFluent();
    }

    @Override
    public LCMFieldset getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
