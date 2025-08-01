package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.Span;

import java.util.HashMap;
import java.util.Map;

public class LCMSpan extends Span implements
        ComponentFluent<LCMSpan>,
        HasSizeFluent<LCMSpan>,
        HasEnabledFluent<LCMSpan>,
        HasTextFluent<LCMSpan>,
        HasStyleFluent<LCMSpan>,
        HtmlComponentFluent<LCMSpan>,
        HasComponentsFluent<LCMSpan>,
        ClickNotifierFluent<LCMSpan>,
        BadgeThemableComponentFluent<LCMSpan>  {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMSpan getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
