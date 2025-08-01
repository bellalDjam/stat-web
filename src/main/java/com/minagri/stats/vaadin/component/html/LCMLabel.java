package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.NativeLabel;

import java.util.HashMap;
import java.util.Map;

public class LCMLabel extends NativeLabel implements
        ComponentFluent<LCMLabel>,
        HasSizeFluent<LCMLabel>,
        HasEnabledFluent<LCMLabel>,
        HasTextFluent<LCMLabel>,
        HasStyleFluent<LCMLabel>,
        HtmlComponentFluent<LCMLabel>,
        HasComponentsFluent<LCMLabel> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public LCMLabel getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
