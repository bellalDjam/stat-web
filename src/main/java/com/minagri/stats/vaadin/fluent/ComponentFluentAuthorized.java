package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.authorization.AuthorizationValidator;
import com.minagri.stats.vaadin.authorization.model.Action;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

import java.util.Objects;
import java.util.function.Consumer;

public interface ComponentFluentAuthorized<FLUENT extends Component> extends HasAuthorization<FLUENT> {

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

	FLUENT getFluent();


	default FLUENT withVisibleFor(Action action) {
		return executeFor(action, obj -> obj.setVisible(true), obj -> obj.setVisible(false));
	}

	default FLUENT withInvisibleFor(Action action) {
		return executeFor(action, obj -> obj.setVisible(false), obj -> obj.setVisible(true));
	}

	default FLUENT withVisibleForAction(boolean visible) {
		return visible ? this.withVisibleForAction() : this.withInvisible();
	}

	default FLUENT withVisibleForAction() {
		Objects.requireNonNull(getAction(), "getAction() returned null. withVisibleForAction() must be called after an action has been set.");
		return withVisibleFor(getAction());
	}

	default FLUENT withInvisibleForAction() {
		return withInvisibleFor(getAction());
	}


	default FLUENT withEnableForAction() {
		getFluent().getElement().setProperty("disabled", !AuthorizationValidator.isAllowed(getAction()));
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
}