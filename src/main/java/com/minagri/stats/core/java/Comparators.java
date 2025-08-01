package com.minagri.stats.core.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

public interface Comparators {

    static <T, P extends Comparable<? super P>> Comparator<T> of(Function<T, P> propertyGetter) {
        return Comparator.comparing(propertyGetter);
    }

    static <T, P1, P2 extends Comparable<? super P2>> Comparator<T> of(Function<T, P1> propertyGetter1, Function<P1, P2> propertyGetter2) {
        return Comparator.comparing(t -> propertyGetter2.apply(propertyGetter1.apply(t)));
    }
    
    static <T, P1, P2, P3 extends Comparable<? super P3>> Comparator<T> of(Function<T, P1> propertyGetter1, Function<P1, P2> propertyGetter2, Function<P2, P3> propertyGetter3) {
        return Comparator.comparing(t -> propertyGetter3.apply(propertyGetter2.apply(propertyGetter1.apply(t))));
    }
    
    @SafeVarargs
    static <T> Comparator<T> combine(Comparator<T>... comparators) {
        return Arrays.stream(comparators).reduce(Comparator::thenComparing).orElseThrow();
    }
}
