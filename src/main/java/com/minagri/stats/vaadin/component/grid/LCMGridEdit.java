package com.minagri.stats.vaadin.component.grid;

import lombok.Data;

import java.util.List;

@Data
public class LCMGridEdit<T> {
    private List<T> editedItems;
    private boolean valid;

    public boolean isInvalid() {
        return !valid;
    }

    public boolean isEmptyOrInvalid() {
        return editedItems.isEmpty() || !valid;
    }
}
