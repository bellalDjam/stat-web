package com.minagri.stats.vaadin.translation.entity;

import com.minagri.stats.vaadin.fluent.HasTranslation;
import lombok.Data;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Data
public class Translation {
    private String key;
    private Object[] messageParameters;
    private Set<HasTranslation> observers;
    private Map<Locale, String> explicitValues;

    public Set<HasTranslation> getObservers() {
        if (this.observers == null) {
            this.observers = new HashSet<>();
        }
        return observers;
    }

    public Translation parameters(Object... parameters) {
        this.setMessageParameters(parameters);
        notifyObservers();
        return this;
    }

    private void notifyObservers() {
        getObservers().forEach(observer -> observer.onParameterChangeInConditionalTranslation(this));
    }

    public void addParameterChangeObserver(HasTranslation observer) {
        getObservers().add(observer);
    }

    public void removeParameterChangeObserver(HasTranslation observer) {
        getObservers().remove(observer);
    }

}
