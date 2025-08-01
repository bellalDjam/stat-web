package com.minagri.stats.core.interval.entity;

public class Span extends Interval<Integer> {

    private Span() {
    }

    public Integer getLength() {
        return getEnd() - getBegin() + 1;
    }

    public static Span of(int begin, int end) {
        if (begin > end || begin <= 0) {
            throw new IllegalArgumentException("Invalid span: " + begin + " - " + end);
        }

        Span range = new Span();
        range.setBegin(begin);
        range.setEnd(end);
        return range;
    }
    
    public static Span fromTo(Span fromSpan, Span toSpan) {
        Integer begin = fromSpan.getBegin();
        Integer end = toSpan.getEnd();
        return of(begin, end);
    }
}
