package com.minagri.stats.vaadin.translation.entity;

public interface TranslationKey {

    default String getKey() {
        if (this instanceof Enum<?> enumValue) {
            return this.getClass().getSimpleName() + "." + enumValue.name();
        }
        throw new RuntimeException("getKey() not implemented for " + this.getClass().getName());
    }
}
