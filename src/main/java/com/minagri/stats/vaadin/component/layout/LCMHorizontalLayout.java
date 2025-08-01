package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.fluent.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class LCMHorizontalLayout extends HorizontalLayout implements
        ComponentFluentAuthorized<LCMHorizontalLayout>,
        HasSizeFluent<LCMHorizontalLayout>,
        HasEnabledFluent<LCMHorizontalLayout>,
        HasStyleFluent<LCMHorizontalLayout>,
        HasComponentsFluent<LCMHorizontalLayout>,
        ThemableLayoutFluent<LCMHorizontalLayout>,
        HasOrderedComponentsFluent<LCMHorizontalLayout>,
        FlexComponentFluent<LCMHorizontalLayout> {

    private Action action;

    @Override
    public LCMHorizontalLayout getFluent() {
        return this;
    }

    @Override
    public LCMHorizontalLayout setAction(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public Action getAction() {
        return this.action;
    }
}
