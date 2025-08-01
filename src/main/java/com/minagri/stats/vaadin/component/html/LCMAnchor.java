package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasComponentsFluent;
import com.minagri.stats.vaadin.fluent.HasTextFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.streams.DownloadHandler;

import java.util.HashMap;
import java.util.Map;

public class LCMAnchor extends Anchor implements
        ComponentFluent<LCMAnchor>,
        HasTextFluent<LCMAnchor>,
        HasComponentsFluent<LCMAnchor>,
        LocaleChangeObserver {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();


    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }


    @Override
    public LCMAnchor getFluent() {
        return this;
    }

    public LCMAnchor withHref(String href) {
        setHref(href);
        return this;
    }

    // use withDownloadHandler
    @Deprecated(forRemoval = true)
    public LCMAnchor withHref(AbstractStreamResource href) {
        setHref(href);
        return this;
    }

    public LCMAnchor withDownloadHandler(DownloadHandler downloadHandler) {
        setHref(downloadHandler);
        return this;
    }

    public LCMAnchor withTarget(AnchorTarget target) {
        setTarget(target);
        return this;
    }
}