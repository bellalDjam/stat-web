package com.minagri.stats.vaadin.component.textfield.control;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.textfield.entity.SimpleResult;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ManyLocalDatesConverter implements Converter<String, List<LocalDate>> {

	private static final String DELIMITER = "; ";
	private static final LocalDateConverter localDateConverter = new LocalDateConverter();

	@Override
	public SimpleResult<List<LocalDate>> convertToModel(String value, ValueContext context) {
		if (Strings.isBlank(value)) {
			return new SimpleResult<>(Collections.emptyList(), null);
		}
		List<LocalDate> localDates = new ArrayList<>();
		StringBuilder invalidDateBuilder = new StringBuilder();
		for (String dateAsString : splitValue(value)) {
			if (Strings.isBlank(dateAsString)) {
				invalidDateBuilder
						.append("\"")
						.append(dateAsString)
						.append("\" ");
				continue;
			}
			Result<LocalDate> result = localDateConverter.convertToModel(dateAsString, null);
			result.ifOk(date -> localDates.add(date));
			if (result.isError()) {
				invalidDateBuilder
						.append("\"")
						.append(result.getMessage().get())
						.append("\" ");
			}
		}

		return new SimpleResult<>(invalidDateBuilder.length() > 0 ? null : localDates, invalidDateBuilder.length() > 0 ? invalidDateBuilder.toString().trim(): null);
	}

	/**
	 * If your list of LocalDate contains a null object, the response will be an empty string ("").
	 *
	 * @param dates
	 * @param context
	 * @return
	 */
	@Override
	public String convertToPresentation(List<LocalDate> dates, ValueContext context) {
		if (dates == null || dates.isEmpty() || dates.contains(null)) {
			return "";
		}
		return dates.stream().map(date -> localDateConverter.convertToPresentation(date, null)).collect(Collectors.joining(DELIMITER));
	}

	/**
	 * Format the string to transform 010101 to 01/01/2001.
	 * This method will keep as well invalid date (not parsable): azerty => azerty
	 * @param value
	 * @return
	 */
	public String format(String value) {
		if (value ==  null) {
			return "";
		}

		String result = splitValue(value).stream()
				.map(s -> {
					String response = localDateConverter.convertToPresentation(localDateConverter.convertToModel(s, null).getValue().orElse(null), null);
					return Strings.isBlank(response) ? s : response;

				}).collect(Collectors.joining(DELIMITER));

		if (value.endsWith(DELIMITER)) {
			result += DELIMITER;
		}
		return result;
	}

	private List<String> splitValue(String value) {
		if (Strings.isBlank(value)) {
			return Collections.emptyList();
		}
		return Arrays.asList(value.split(DELIMITER));
	}

}
