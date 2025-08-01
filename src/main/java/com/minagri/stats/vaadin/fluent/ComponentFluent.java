package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

import java.util.function.Consumer;

public interface ComponentFluent<FLUENT extends Component> {

    default FLUENT withVisible() {
        return withVisible(true);
    }

    default FLUENT withInvisible() {
        return withVisible(false);
    }

    default FLUENT withVisible(boolean value) {
        getFluent().setVisible(value);
        return getFluent();
    }

    default FLUENT withId(String id) {
        getFluent().setId(id);
        return getFluent();
    }

    default FLUENT addTo(HasComponents parent) {
        FLUENT fluent = getFluent();
        parent.add(fluent);
        return fluent;
    }

    default FLUENT execute(Consumer<FLUENT> consumer) {
        FLUENT fluent = getFluent();
        consumer.accept(fluent);
        return fluent;
    }

    default FLUENT setTextAlignRight() {
        FLUENT fluent = getFluent();
        fluent.getElement().getStyle().set("text-align", "right");
        return fluent;
    }

    default FLUENT setFontWeightBold() {
        FLUENT fluent = getFluent();
        fluent.getElement().getStyle().set("font-weight", "bold");
        return fluent;
    }

    FLUENT getFluent();
}
