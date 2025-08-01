package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.core.interval.control.Intervals;
import com.minagri.stats.core.interval.entity.Interval;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.customfield.CustomField;

public class LCMIntegerRangeField extends CustomField<Interval<Integer>> implements HasSizeFluent<LCMIntegerRangeField> {
    private LCMIntegerField startField;
    private LCMIntegerField endField;

    public LCMIntegerRangeField() {
        startField = new LCMIntegerField()
                .withPlaceHolder(CommonMessage.FROM)
                .withStyle("flex", "1");

        endField = new LCMIntegerField()
                .withPlaceHolder(CommonMessage.TO)
                .withStyle("flex", "1");

        startField.setManualValidation(true);
        endField.setManualValidation(true);

        add(startField, new LCMSpan().withText("-").setMarginLeftRight("8px"), endField);
        setValue(getEmptyValue());
    }

    @Override
    protected Interval<Integer> generateModelValue() {
        return Intervals.of(startField.getValue(), endField.getValue());
    }

    @Override
    protected void setPresentationValue(Interval<Integer> interval) {
        startField.setValue(interval.getBegin());
        endField.setValue(interval.getEnd());
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        startField.setInvalid(invalid);
        endField.setInvalid(invalid);
    }

    @Override
    public LCMIntegerRangeField getFluent() {
        return this;
    }

    @Override
    public Interval<Integer> getEmptyValue() {
        return Intervals.of(null, null);
    }
}
