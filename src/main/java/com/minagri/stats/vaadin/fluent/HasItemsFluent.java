package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.data.binder.HasItems;

import java.util.Collection;
import java.util.stream.Stream;

public interface HasItemsFluent<FLUENT extends HasItems<VALUECLASS>, VALUECLASS> {

    @SuppressWarnings({"unchecked", "varargs"})
    default FLUENT withItems(VALUECLASS... items) {
        getFluent().setItems(items);
        return getFluent();
    }

    default FLUENT withItems(Collection<VALUECLASS> items) {
        getFluent().setItems(items);
        return getFluent();
    }

    default FLUENT withItems(Stream<VALUECLASS> items) {
        getFluent().setItems(items);
        return getFluent();
    }

    FLUENT getFluent();
}
