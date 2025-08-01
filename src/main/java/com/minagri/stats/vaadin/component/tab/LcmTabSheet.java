package com.minagri.stats.vaadin.component.tab;

import com.minagri.stats.vaadin.css.LCMCss;
import com.minagri.stats.vaadin.fluent.HasPrefixAndSuffixFluent;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.minagri.stats.vaadin.fluent.HasThemeFluent;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.TabSheet;

import java.util.function.Consumer;

public class LcmTabSheet extends TabSheet implements
        HasSizeFluent<LcmTabSheet>,
        HasStyleFluent<LcmTabSheet>,
        HasThemeFluent<LcmTabSheet>,
        HasPrefixAndSuffixFluent<LcmTabSheet> {

    @Override
    public LcmTabSheet getFluent() {
        return this;
    }

    public LcmTabSheet setPaperClass() {
        withClassName(LCMCss.PAPER);
        return this;
    }

    public LcmTabSheet addTabSelectionListener(Runnable listener) {
        addSelectedChangeListener(event -> listener.run());
        return this;
    }

    public LcmTabSheet addTabSelectionListener(Consumer<LcmTab> listener) {
        addSelectedChangeListener(event -> listener.accept((LcmTab) getSelectedTab()));
        return this;
    }

    public LcmTab addTab(Component tabComponent, Enum<?> labelKey, Object... messageParameters) {
        LcmTab tab = new LcmTab(this).withLabel(labelKey, messageParameters);
        add(tab, tabComponent);
        return tab;
    }

    public LcmTab addTab(Component tabComponent, TranslationKey labelKey, Object... messageParameters) {
        LcmTab tab = new LcmTab(this).withLabelKey(labelKey, messageParameters);
        add(tab, tabComponent);
        return tab;
    }

    public LcmTab addTab(Component tabComponent) {
        LcmTab tab = new LcmTab(this).withLabel("");
        add(tab, tabComponent);
        return tab;
    }
}
