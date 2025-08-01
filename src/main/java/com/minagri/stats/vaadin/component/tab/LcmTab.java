package com.minagri.stats.vaadin.component.tab;

import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.tabs.Tab;

import java.util.HashMap;
import java.util.Map;

public class LcmTab extends Tab implements
        ComponentFluent<LcmTab>,
        HasEnabledFluent<LcmTab>,
        HasStyleFluent<LcmTab>,
        HasThemeFluent<LcmTab>,
        HasLabelFluent<LcmTab> {

    private LcmTabSheet tabSheet;
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LcmTab(LcmTabSheet tabSheet) {
        this.tabSheet = tabSheet;
    }

    @Override
    public LcmTab getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
    
    public LcmTab select() {
        tabSheet.setSelectedTab(null);
        tabSheet.setSelectedTab(this);
        return this;
    }

    public LcmTab addSelectionListener(Runnable listener) {
        tabSheet.addTabSelectionListener(selectedTab -> {
            if (selectedTab == this) {
                listener.run();
            }
        });
        return this;
    }
}
