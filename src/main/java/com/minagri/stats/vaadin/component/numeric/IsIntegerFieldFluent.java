package com.minagri.stats.vaadin.component.numeric;

import com.vaadin.flow.component.textfield.IntegerField;

public interface IsIntegerFieldFluent<FLUENT extends IntegerField> extends IsNumberFieldFluent<FLUENT> {
    default FLUENT withMax(int maxLength) {
        this.getFluent().setMax(maxLength);
        return this.getFluent();
    }

    default FLUENT withMin(int minLength) {
        this.getFluent().setMin(minLength);
        return this.getFluent();
    }
    
    default FLUENT withStep(int step) {
        this.getFluent().setStep(step);
        return this.getFluent();
    }
    
    default FLUENT withStepButtons() {
        this.getFluent().setStepButtonsVisible(true);
        return this.getFluent();
    }
}
