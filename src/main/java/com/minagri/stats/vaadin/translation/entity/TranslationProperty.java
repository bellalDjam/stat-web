package com.minagri.stats.vaadin.translation.entity;

public class TranslationProperty implements TranslationKey {
    private final String property;

    public TranslationProperty(String key) {
        this.property = key;
    }

    @Override
    public String getKey() {
        return property;
    }
    
    public static TranslationProperty of(String key) {
        return new TranslationProperty(key);
    }
}
