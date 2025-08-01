package com.minagri.stats.vaadin.component.icon;

import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class LcmIcon extends Icon implements HasStyleFluent<LcmIcon> {

    public LcmIcon(VaadinIcon vaadinIcon) {
        super(vaadinIcon);
    }

    public LcmIcon withSizeSmall() {
        setSize("var(--lumo-icon-size-s)");
        return this;
    }

    public LcmIcon withSizeMedium() {
        setSize("var(--lumo-icon-size-m)");
        return this;
    }

    public LcmIcon withSizeLarge() {
        setSize("var(--lumo-icon-size-l)");
        return this;
    }

    @Override
    public LcmIcon getFluent() {
        return this;
    }
}
