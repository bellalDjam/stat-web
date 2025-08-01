package com.minagri.stats.vaadin.component.dialog;


import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.shared.Registration;

public interface IsDialogFluent<FLUENT extends Dialog> {

    default FLUENT withCloseOnEsc(boolean value) {
        getFluent().setCloseOnEsc(value);
        return getFluent();
    }

    default FLUENT withCloseOnEsc() {
        return withCloseOnEsc(true);
    }

    default FLUENT withCloseOnOutsideClick(boolean value) {
        getFluent().setCloseOnOutsideClick(value);
        return getFluent();
    }

    default FLUENT withCloseOnOutsideClick() {
        return withCloseOnOutsideClick(true);
    }

    default Registration withDialogCloseActionListenerRemovable(ComponentEventListener<Dialog.DialogCloseActionEvent> listener) {
        return getFluent().addDialogCloseActionListener(listener);
    }

    default FLUENT withDialogCloseActionListener(ComponentEventListener<Dialog.DialogCloseActionEvent> listener) {
        withDialogCloseActionListenerRemovable(listener);
        return getFluent();
    }

    default Registration withOpenedChangeListenerRemovable(ComponentEventListener<Dialog.OpenedChangeEvent> listener) {
        return getFluent().addOpenedChangeListener(listener);
    }

    default FLUENT withOpenedChangeListener(ComponentEventListener<Dialog.OpenedChangeEvent> listener) {
        withOpenedChangeListenerRemovable(listener);
        return getFluent();
    }

    default FLUENT withHeaderTitle(String title) {
        FLUENT fluent = getFluent();
        fluent.setHeaderTitle(title);
        return fluent;
    }

    default FLUENT withHeaderTitle(Enum<?> key) {
        FLUENT fluent = getFluent();
        fluent.setHeaderTitle(Translator.translate(key));
        return fluent;
    }

    default FLUENT withHeaderTitle(TranslationKey key) {
        FLUENT fluent = getFluent();
        fluent.setHeaderTitle(Translator.translateKey(key));
        return fluent;
    }

    default FLUENT withMinWidthInPixels(int minWidth) {
        FLUENT fluent = getFluent();
        fluent.setMinWidth(minWidth + "px");
        return fluent;
    }
    
    default FLUENT withMinHeightInPixels(int minHeight) {
        FLUENT fluent = getFluent();
        fluent.setMinHeight(minHeight + "px");
        return fluent;
    }

    default FLUENT withMinDimensionsInPixels(int minWidth, int minHeight) {
        FLUENT fluent = getFluent();
        fluent.setMinWidth(minWidth + "px");
        fluent.setMinHeight(minHeight + "px");
        return fluent;
    }

    FLUENT getFluent();

}
