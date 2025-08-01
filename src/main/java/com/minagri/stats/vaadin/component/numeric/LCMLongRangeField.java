package com.minagri.stats.vaadin.component.numeric;

import com.minagri.stats.core.interval.control.Intervals;
import com.minagri.stats.core.interval.entity.Interval;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.customfield.CustomField;

public class LCMLongRangeField extends CustomField<Interval<Long>> implements HasSizeFluent<LCMLongRangeField> {
    private LCMLongField startField;
    private LCMLongField endField;

    public LCMLongRangeField() {
        startField = new LCMLongField()
                .withPlaceHolder(CommonMessage.FROM)
                .withStyle("flex", "1");

        endField = new LCMLongField()
                .withPlaceHolder(CommonMessage.TO)
                .withStyle("flex", "1");

        startField.setManualValidation(true);
        endField.setManualValidation(true);

        add(startField, new LCMSpan().withText("-").setMarginLeftRight("8px"), endField);
        setValue(getEmptyValue());
    }

    @Override
    protected Interval<Long> generateModelValue() {
        return Intervals.of(startField.getValue(), endField.getValue());
    }

    @Override
    protected void setPresentationValue(Interval<Long> interval) {
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
    public LCMLongRangeField getFluent() {
        return this;
    }

    @Override
    public Interval<Long> getEmptyValue() {
        return Intervals.of(null, null);
    }
}
