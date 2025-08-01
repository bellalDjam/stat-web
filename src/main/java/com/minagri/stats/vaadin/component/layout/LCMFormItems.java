package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.fluent.HasStyleFluent;

import java.util.function.Consumer;

public interface LCMFormItems {
    
    static Consumer<LCMFormItem> withJustifySelfStart() {
        return HasStyleFluent::withJustifySelfStart;
    }

    static Consumer<LCMFormItem> withAlignSelfStart() {
        return HasStyleFluent::withAlignSelfStart;
    }
}
