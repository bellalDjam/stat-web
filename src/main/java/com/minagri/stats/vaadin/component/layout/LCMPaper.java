package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.css.LCMCss;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class LCMPaper extends Div {
    public LCMPaper() {
        setClassName(LCMCss.PAPER);
    }

    public LCMPaper(Component... components) {
        super(components);
        setClassName(LCMCss.PAPER);
    }
}
