package com.minagri.stats.core.java;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Doubles {

	static String toString(Double value) {
		return value == null ? null : value.toString();
	}

	static String toStringNullEmpty(Double value) {
		return value == null ? "" : value.toString();
	}

	static String toString(Double value, int length) {
		return Strings.leftPadZero(toString(value), length);
	}

	/**
	 * Returns the given value as a string, considering null as a blank.
	 */
	static String toStringNullBlank(Double value) {
		return Strings.nullToEmpty(value);
	}

	/**
	 * Returns the given value as a string of the given length padded with zeros, considering null as a blank.g}
	 */
	static String toStringZeroPaddedNullBlank(Double value, int length) {
		String stringValue = toStringNullBlank(value);
		return Strings.leftPad(stringValue, length, '0');
	}

	/**
	 * Returns the given value as an integer.
	 */
	static Integer toInteger(Double value) {
		if (value == null) {
			return null;
		}
		return value.intValue();
	}


	static String toStringWithSign(Double value, Integer length) {
		if (value == null || Strings.isBlank(value.toString())) {
			return "+" + Strings.leftPad("", length - 1, '0');
		}

		BigDecimal bigDecimalValue;
		try {
			bigDecimalValue = new BigDecimal(value.toString());
		} catch (Exception e) {
			throw new RuntimeException("Value " + value + " is not numeric", e);
		}

		bigDecimalValue = bigDecimalValue.setScale(0, RoundingMode.DOWN);
		String stringValue = bigDecimalValue.toPlainString();

		boolean negative = stringValue.startsWith("-");
		if (stringValue.startsWith("-") || stringValue.startsWith("+")) {
			stringValue = Strings.substring(stringValue, 1);
		}

		if (stringValue.length() > length - 1) {
			throw new RuntimeException("Value " + value + " too large for amount field of length " + length);
		}

		String sign = negative ? "-" : "+";
		return sign + Strings.leftPad(stringValue, length - 1, '0');
	}

	static Long concatLong(Double... numbers) {
		StringBuilder concatenatedString = new StringBuilder();
		for (Double number : numbers) {
			if (number != null) {
				concatenatedString.append(number);
			}
		}
		return Strings.toLong(concatenatedString.toString());
	}
}
