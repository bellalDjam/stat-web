package com.minagri.stats.vaadin.component.date;

import com.minagri.stats.core.interval.control.Intervals;
import com.minagri.stats.core.interval.entity.Interval;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.fluent.HasValueFluent;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.customfield.CustomField;

import java.time.LocalDate;

public class LCMDateRangePicker extends CustomField<Interval<LocalDate>> implements
        HasSizeFluent<LCMDateRangePicker>,
        HasValueFluent<LCMDateRangePicker, Interval<LocalDate>> {

    private LCMDatePicker fromPicker;
    private LCMDatePicker toPicker;
    private Interval<LocalDate> emptyValue = Intervals.of(null, null);

    public LCMDateRangePicker() {
        fromPicker = new LCMDatePicker()
                .withPlaceHolder(CommonMessage.FROM)
                .withStyle("flex", "1");

        toPicker = new LCMDatePicker()
                .withPlaceHolder(CommonMessage.TO)
                .withStyle("flex", "1");

        fromPicker.setManualValidation(true);
        toPicker.setManualValidation(true);

        add(fromPicker, new LCMSpan().withText("-").setMarginLeftRight("8px"), toPicker);
        setValue(emptyValue);
    }

    public void setValue(LocalDate fromDate, LocalDate toDate) {
        fromPicker.setValue(fromDate);
        toPicker.setValue(toDate);
    }

    public LCMDateRangePicker withEmptyValue(LocalDate fromDate, LocalDate toDate) {
        return withEmptyValue(Intervals.of(fromDate, toDate));
    }

    public LCMDateRangePicker withEmptyValue(Interval<LocalDate> emptyValue) {
        boolean isCurrentlyEmpty = isEmpty();
        this.emptyValue = emptyValue;
        if (isCurrentlyEmpty) {
            setValue(emptyValue);
        }
        return this;
    }
    
    public LocalDate getFromValue() {
        return fromPicker.getValue();
    }
    
    public LocalDate getToValue() {
        return toPicker.getValue();
    }

    @Override
    protected Interval<LocalDate> generateModelValue() {
        return Intervals.of(fromPicker.getValue(), toPicker.getValue());
    }

    @Override
    protected void setPresentationValue(Interval<LocalDate> localDateRange) {
        fromPicker.setValue(localDateRange.getBegin());
        toPicker.setValue(localDateRange.getEnd());
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
        fromPicker.setInvalid(invalid);
        toPicker.setInvalid(invalid);
    }

    @Override
    public LCMDateRangePicker getFluent() {
        return this;
    }

    @Override
    public Interval<LocalDate> getEmptyValue() {
        return emptyValue;
    }
}
