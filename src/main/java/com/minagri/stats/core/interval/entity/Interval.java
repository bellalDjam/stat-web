package com.minagri.stats.core.interval.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Interval<T extends Comparable<?>> {
    private T begin;
    private T end;
    private boolean exclusive;
}
