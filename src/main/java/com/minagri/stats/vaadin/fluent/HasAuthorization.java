package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.authorization.AuthorizationValidator;
import com.minagri.stats.vaadin.authorization.model.Action;

import java.util.function.Consumer;

public interface HasAuthorization<FLUENT> {
	default FLUENT executeFor(Action action, Consumer<FLUENT> consumerIfAllowed, Consumer<FLUENT> consumerIfRefused) {
		if (AuthorizationValidator.isAllowed(action)) {
			consumerIfAllowed.accept(this.getFluent());
		} else if (consumerIfRefused != null) {
			consumerIfRefused.accept(this.getFluent());
		}

		return this.getFluent();
	}

	FLUENT getFluent();

	FLUENT setAction(Action action);

	Action getAction();
}
