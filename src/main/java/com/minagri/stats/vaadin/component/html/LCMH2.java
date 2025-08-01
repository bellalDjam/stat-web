package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.H2;

import java.util.HashMap;
import java.util.Map;

public class LCMH2 extends H2 implements
        ComponentFluent<LCMH2>,
        HasSizeFluent<LCMH2>,
        HasEnabledFluent<LCMH2>,
        HasTextFluent<LCMH2>,
        HasStyleFluent<LCMH2>,
        HtmlComponentFluent<LCMH2>,
        HasComponentsFluent<LCMH2>,
        ClickNotifierFluent<LCMH2> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public LCMH2 getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
