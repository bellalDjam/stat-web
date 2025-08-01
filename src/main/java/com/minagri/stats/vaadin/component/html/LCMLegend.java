package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

import java.util.HashMap;
import java.util.Map;

@Tag("legend")
public class LCMLegend extends HtmlContainer implements ClickNotifier<LCMLegend>,
        ComponentFluent<LCMLegend>,
        HasSizeFluent<LCMLegend>,
        HasEnabledFluent<LCMLegend>,
        HasTextFluent<LCMLegend>,
        HasStyleFluent<LCMLegend>,
        HtmlComponentFluent<LCMLegend>,
        HasComponentsFluent<LCMLegend>,
        ClickNotifierFluent<LCMLegend> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMLegend getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
