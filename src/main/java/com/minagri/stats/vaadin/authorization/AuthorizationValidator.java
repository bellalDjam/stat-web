package com.minagri.stats.vaadin.authorization;

import com.minagri.stats.vaadin.authorization.model.Action;

import java.util.Optional;
import java.util.function.Predicate;


public class AuthorizationValidator {
    public static final Predicate<Action> DEFAULT_PREDICATE = (Action) -> {
        return false;
    };
    public static Predicate<Action> predicate = null;

    public AuthorizationValidator() {
    }

    public static boolean isAllowed(Action action) {
        return ((Predicate) Optional.ofNullable(predicate).orElse(DEFAULT_PREDICATE)).test(action);
    }
}
