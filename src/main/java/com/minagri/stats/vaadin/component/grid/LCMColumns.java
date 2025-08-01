package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.vaadin.fluent.IsColumnFluent;

import java.util.function.Consumer;
import java.util.function.Function;

public interface LCMColumns {

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withTextAlignEnd() {
        return IsColumnFluent::withTextAlignEnd;
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withSortProperty(String sortProperty) {
        return column -> column.withSortProperty(sortProperty);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withDefaultAscendingSortProperty(String... properties) {
        return column -> column.withDefaultAscendingSortProperty(properties);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withDefaultDescendingSortProperty(String... properties) {
        return column -> column.withDefaultDescendingSortProperty(properties);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withSortable() {
        return IsColumnFluent::withSortable;
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withDefaultSortable() {
        return IsColumnFluent::withDefaultSortable;
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withDefaultDescendingSortable() {
        return IsColumnFluent::withDefaultDescendingSortable;
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withTooltip(Function<VALUECLASS, String> tooltipGenerator) {
        return column -> column.setTooltipGenerator(tooltipGenerator::apply);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withWidthInPixels(Integer pixels) {
        return column -> column.withWidthInPixels(pixels);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withFlexGrow(Integer flexGrow) {
        return column -> column.withFlexGrow(flexGrow);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withFlexGrow0() {
        return withFlexGrow(0);
    }

    static <VALUECLASS> Consumer<LCMColumn<VALUECLASS>> withFlexGrow1() {
        return withFlexGrow(1);
    }
}
