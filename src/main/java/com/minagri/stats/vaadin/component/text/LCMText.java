package com.minagri.stats.vaadin.component.text;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasTextFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Text;

import java.util.HashMap;
import java.util.Map;

public class LCMText extends Text implements
        ComponentFluent<LCMText>,
        HasTextFluent<LCMText> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    public LCMText() {
        super("");
    }

    @Override
    public LCMText getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    
}
