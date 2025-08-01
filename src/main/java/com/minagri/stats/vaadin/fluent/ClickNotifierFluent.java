package com.minagri.stats.vaadin.fluent;

import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;

public interface ClickNotifierFluent<E extends Component & ClickNotifier<? super E>> {

    default E withClickListener(ComponentEventListener<ClickEvent<E>> listener) {
        withClickListenerRemovable(listener);
        return getFluent();
    }

    default E withClickListener(Runnable listener) {
        withClickListenerRemovable(event -> listener.run());
        return getFluent();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default Registration withClickListenerRemovable(ComponentEventListener<ClickEvent<E>> listener) {
        return getFluent().addClickListener((ComponentEventListener) listener);
    }

    default E withClickShortcut(Key key, KeyModifier... keyModifiers) {
        getFluent().addClickShortcut(key, keyModifiers);
        return getFluent();
    }

    default E withClickShortcutOnEnter() {
        return withClickShortcut(Key.ENTER);
    }

    default E withClickShortcutOnEscape() {
        return withClickShortcut(Key.ESCAPE);
    }

    E getFluent();
}
