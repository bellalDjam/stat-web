package com.minagri.stats.vaadin.component.textfield.control;

import com.minagri.stats.core.java.Dates;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.entity.SimpleResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;

public class LocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public SimpleResult<LocalDate> convertToModel(String value, ValueContext context) {
		if (Strings.isBlank(value)) {
			return new SimpleResult<>(null, null);
		}

		LocalDate localDate = null;
		String s = value.replaceAll("[/-]", "");
		if (s.length() > 5) {
			if (s.length() < 8) {
				localDate = Dates.safeParseDate(s,Dates.DDMMYY);
			} else {
				localDate = Dates.safeParseDate(s,Dates.DDMMYYYY);
			}
		}
		return new SimpleResult<>(localDate, localDate == null ? value : null);
	}

	@Override
	public String convertToPresentation(LocalDate value, ValueContext context) {
		return Dates.format(value,Dates.DDMMYYYY_SLASHED);
	}
}
