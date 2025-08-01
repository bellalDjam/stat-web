package com.minagri.stats.vaadin.component.numeric;

public interface IsLongFieldFluent<FLUENT extends LCMLongField> extends IsNumberFieldFluent<FLUENT> {
    default FLUENT withMax(long maxLength) {
        this.getFluent().setMax(maxLength);
        return this.getFluent();
    }

    default FLUENT withMin(long minLength) {
        this.getFluent().setMin(minLength);
        return this.getFluent();
    }
}
