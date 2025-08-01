package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.HasListDataView;

import java.util.List;

public interface HasListDataViewFluent<FLUENT extends Component & HasListDataView<VALUECLASS, ?>, VALUECLASS> {
    FLUENT getFluent();

    @SuppressWarnings({"unchecked", "varargs"})
    default FLUENT withItems(VALUECLASS... items) {
        getFluent().setItems(items);
        return getFluent();
    }

    default FLUENT withItems(List<VALUECLASS> items) {
        getFluent().setItems(items);
        return getFluent();
    }
}