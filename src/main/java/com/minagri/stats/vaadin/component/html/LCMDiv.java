package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.Div;

import java.util.HashMap;
import java.util.Map;

public class LCMDiv extends Div implements
        ComponentFluent<LCMDiv>,
        HasSizeFluent<LCMDiv>,
        HasEnabledFluent<LCMDiv>,
        HasTextFluent<LCMDiv>,
        HasStyleFluent<LCMDiv>,
        HtmlComponentFluent<LCMDiv>,
        HasComponentsFluent<LCMDiv>,
        ClickNotifierFluent<LCMDiv> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public LCMDiv getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
