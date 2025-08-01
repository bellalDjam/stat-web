package com.minagri.stats.vaadin.fluent;


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

	FLUENT getFluent();

}
