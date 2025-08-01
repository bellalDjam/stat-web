package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.core.interval.control.Intervals;
import com.minagri.stats.core.interval.entity.Interval;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.vaadin.flow.component.customfield.CustomField;

import java.time.YearMonth;

public class LCMMonthYearRangeField extends CustomField<Interval<YearMonth>> implements HasSizeFluent<LCMMonthYearRangeField> {
    private LCMMonthYearField startPicker;
    private LCMMonthYearField endPicker;

    public LCMMonthYearRangeField() {
        startPicker = new LCMMonthYearField()
                .withStyle("flex", "1");
        endPicker = new LCMMonthYearField()
                .withStyle("flex", "1");

        startPicker.setManualValidation(true);
        endPicker.setManualValidation(true);

        add(startPicker, new LCMSpan().withText("-").setMarginLeftRight("8px"), endPicker);
        setValue(getEmptyValue());
    }

    @Override
    protected Interval<YearMonth> generateModelValue() {
        return Intervals.of(startPicker.getValue(), endPicker.getValue());
    }

    @Override
    protected void setPresentationValue(Interval<YearMonth> localDateRange) {
        startPicker.setValue(localDateRange.getBegin());
        endPicker.setValue(localDateRange.getEnd());
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        startPicker.setInvalid(invalid);
        endPicker.setInvalid(invalid);
    }

    @Override
    public LCMMonthYearRangeField getFluent() {
        return this;
    }

    @Override
    public Interval<YearMonth> getEmptyValue() {
        return Intervals.of(null, null);
    }
}
