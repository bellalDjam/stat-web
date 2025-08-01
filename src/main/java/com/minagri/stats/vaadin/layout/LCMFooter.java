package com.minagri.stats.vaadin.layout;

import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.css.LCMCss;
import com.vaadin.flow.component.html.Footer;
import lombok.Getter;

@Getter
public class LCMFooter extends Footer {
    private LCMSpan version;
    private LCMSpan environment;
    private LCMSpan screenName;

    public LCMFooter() {
        version = new LCMSpan()
                .withText("")
                .withClassName(LCMCss.INFO_OF_FIELD)
                .withStyle("margin", "0 var(--lumo-space-m)");

        environment = new LCMSpan()
                .withId("app-footer-env")
                .withText("");

        screenName = new LCMSpan()
                .withStyle("margin", "0 0 0 var(--lumo-space-m)");

        setId("app-footer");
        add(version);
        add(environment);
        add(screenName);
    }
}
