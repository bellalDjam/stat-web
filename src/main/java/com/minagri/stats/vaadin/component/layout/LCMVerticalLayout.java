package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.fluent.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LCMVerticalLayout extends VerticalLayout implements
        ComponentFluentAuthorized<LCMVerticalLayout>,
        HasSizeFluent<LCMVerticalLayout>,
        HasEnabledFluent<LCMVerticalLayout>,
        HasStyleFluent<LCMVerticalLayout>,
        HasComponentsFluent<LCMVerticalLayout>,
        ThemableLayoutFluent<LCMVerticalLayout>,
        HasOrderedComponentsFluent<LCMVerticalLayout>,
        FlexComponentFluent<LCMVerticalLayout> {

    private Action action;

    @Override
    public LCMVerticalLayout getFluent() {
        return this;
    }

    @Override
    public LCMVerticalLayout setAction(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public Action getAction() {
        return this.action;
    }
}
