package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public interface FlexComponentFluent<FLUENT extends FlexComponent> {

    default FLUENT withExpand(Component... components) {
        getFluent().expand(components);
        return getFluent();
    }

    default FLUENT withAlignItems(FlexComponent.Alignment alignment) {
        getFluent().setAlignItems(alignment);
        return getFluent();
    }

    default FLUENT withAlignItemsCenter() {
        return withAlignItems(FlexComponent.Alignment.CENTER);
    }

    default FLUENT withAlignItemsStart() {
        return withAlignItems(FlexComponent.Alignment.START);
    }

    default FLUENT withAlignItemsEnd() {
        return withAlignItems(FlexComponent.Alignment.END);
    }

    default FLUENT withAlignItemsAuto() {
        return withAlignItems(FlexComponent.Alignment.AUTO);
    }

    default FLUENT withAlignItemsBaseline() {
        return withAlignItems(FlexComponent.Alignment.BASELINE);
    }

    default FLUENT withAlignItemsStretch() {
        return withAlignItems(FlexComponent.Alignment.STRETCH);
    }

    default FLUENT withJustifyContentMode(FlexComponent.JustifyContentMode justifyContentMode) {
        getFluent().setJustifyContentMode(justifyContentMode);
        return getFluent();
    }

    default FLUENT withJustifyContentModeCenter() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    default FLUENT withJustifyContentModeStart() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.START);
    }

    default FLUENT withJustifyContentModeEnd() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.END);
    }

    default FLUENT withJustifyContentModeBetween() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    }

    default FLUENT withJustifyContentModeAround() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
    }

    default FLUENT withJustifyContentModeEvenly() {
        return withJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
    }

    default FLUENT withFlexGrow(double flexGrow, HasElement... elementContainers) {
        FLUENT fluent = getFluent();
        fluent.setFlexGrow(flexGrow, elementContainers);
        return fluent;
    }

    FLUENT getFluent();
}
