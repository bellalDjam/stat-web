package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.splitlayout.SplitLayout;

public class LCMSplitLayout extends SplitLayout implements
        ComponentFluent<LCMSplitLayout>,
        HasSizeFluent<LCMSplitLayout>,
        HasStyleFluent<LCMSplitLayout> {

    @Override
    public LCMSplitLayout getFluent() {
        return this;
    }

    public LCMSplitLayout withPrimary(Component component) {
        addToPrimary(component);
        return this;
    }

    public LCMSplitLayout withSecondary(Component component) {
        addToSecondary(component);
        return this;
    }
}
