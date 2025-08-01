package com.minagri.stats.vaadin.authorization.model;


import lombok.Data;

import java.util.*;

@Data
public class Action {

    private final List<String> allowedRoles;

    public Action(Action action) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(action.getAllowedRoles());

        this.allowedRoles = new ArrayList<>(action.getAllowedRoles());
    }

    public Action(String role) {
        Objects.requireNonNull(role);

        this.allowedRoles = Arrays.asList(role);
    }

    public Action(List<String> roles) {
        Objects.requireNonNull(roles);

        this.allowedRoles = new ArrayList<>(roles);
    }

    public Action(String... roles) {
        this.allowedRoles = Arrays.asList(roles);
    }

    public List<String> getAllowedRoles() {
        return Collections.unmodifiableList(allowedRoles);
    }
}
