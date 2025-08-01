package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;

public interface HasPrefixAndSuffixFluent<FLUENT extends HasPrefix & HasSuffix> {

    default FLUENT withPrefixComponent(Component component) {
        getFluent().setPrefixComponent(component);
        return getFluent();
    }

    default FLUENT withPrefix(String text) {
        getFluent().setPrefixComponent(new Span(text));
        return getFluent();
    }

    default FLUENT withSuffixComponent(Component component) {
        getFluent().setSuffixComponent(component);
        return getFluent();
    }

    default FLUENT withSuffix(String text) {
        getFluent().setSuffixComponent(new Span(text));
        return getFluent();
    }

    FLUENT getFluent();
}
