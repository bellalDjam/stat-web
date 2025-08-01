package com.minagri.stats.vaadin.component.text;

import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.component.icon.LcmIcon;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.minagri.stats.vaadin.fluent.HasTextFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.HashMap;
import java.util.Map;

public class LCMInfoText extends HorizontalLayout implements
        HasStyleFluent<LCMInfoText>,
        HasText,
        HasTextFluent<LCMInfoText>,
        ComponentFluent<LCMInfoText> {
    
    private LcmIcon icon;
    private LCMSpan textSpan;
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMInfoText() {
        icon = new LcmIcon(VaadinIcon.INFO_CIRCLE_O);
        textSpan = new LCMSpan();
        add(icon, textSpan);
    }

    public LCMInfoText withIcon(VaadinIcon vaadinIcon) {
        icon.setIcon(vaadinIcon);
        return this;
    }

    @Override
    public void setText(String text) {
        textSpan.setText(text);
    }

    @Override
    public String getText() {
        return textSpan.getText();
    }

    @Override
    public LCMInfoText getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

}
