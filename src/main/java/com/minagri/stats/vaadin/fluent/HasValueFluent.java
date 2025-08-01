package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.shared.Registration;

import java.util.function.Consumer;

public interface HasValueFluent<FLUENT extends Component & HasValue<?, VALUECLASS>, VALUECLASS> {

    default FLUENT withReadOnly(boolean value) {
        getFluent().setReadOnly(value);
        return getFluent();
    }

    default FLUENT withReadOnly() {
        return withReadOnly(true);
    }

    default FLUENT withRequiredIndicatorVisible(boolean value) {
        getFluent().setRequiredIndicatorVisible(value);
        return getFluent();
    }

    default FLUENT withRequiredIndicatorVisible() {
        return withRequiredIndicatorVisible(true);
    }

    default FLUENT withRequiredIndicatorInvisible() {
        return withRequiredIndicatorVisible(false);
    }

    default FLUENT withValueChangeListener(HasValue.ValueChangeListener<HasValue.ValueChangeEvent<VALUECLASS>> listener) {
        withValueChangeListenerRemovable(listener);
        return getFluent();
    }

    default FLUENT withValueChangeListener(Runnable listener) {
        withValueChangeListenerRemovable(event -> listener.run());
        return getFluent();
    }

    default FLUENT withValueChangeConsumer(Consumer<VALUECLASS> consumer) {
        withValueChangeListenerRemovable(event -> consumer.accept(event.getValue()));
        return getFluent();
    }

    default Registration withValueChangeListenerRemovable(HasValue.ValueChangeListener<HasValue.ValueChangeEvent<VALUECLASS>> listener) {
        return getFluent().addValueChangeListener(listener);
    }

    default FLUENT withValue(VALUECLASS value) {
        getFluent().setValue(value);
        return getFluent();
    }

    FLUENT getFluent();

}
