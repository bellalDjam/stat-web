package com.minagri.stats.vaadin.binder;

import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.function.SerializablePredicate;
import lombok.Data;

@Data
public class LcmValidator<T> {
    private SerializablePredicate<T> predicate;
    private Translation messageTranslation;
}
