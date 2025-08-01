package com.minagri.stats.core.java;

import com.minagri.stats.core.exception.Exceptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import static java.math.MathContext.DECIMAL128;

public interface BigDecimals {

    static boolean isEqualTo(BigDecimal value1, BigDecimal value2) {
        Objects.requireNonNull(value1);
        Objects.requireNonNull(value2);
        return value1.compareTo(value2) == 0;
    }

    static boolean isNotEqualTo(BigDecimal value1, BigDecimal value2) {
        return !isEqualTo(value1, value2);
    }

    static boolean isGreaterThan(BigDecimal value1, BigDecimal value2) {
        Objects.requireNonNull(value1);
        Objects.requireNonNull(value2);
        return value1.compareTo(value2) > 0;
    }

    static boolean isGreaterThanZero(BigDecimal value) {
        return isGreaterThan(value, BigDecimal.ZERO);
    }

    static boolean isLessThan(BigDecimal value1, BigDecimal value2) {
        Objects.requireNonNull(value1);
        Objects.requireNonNull(value2);
        return value1.compareTo(value2) < 0;
    }

    static boolean isNullOrZero(BigDecimal value) {
        return value == null || isEqualTo(value, BigDecimal.ZERO);
    }

    static boolean isNotNullOrZero(BigDecimal value) {
        return !isNullOrZero(value);
    }

    static BigDecimal inverse(BigDecimal value) {
        return value == null ? null : value.multiply(BigDecimal.valueOf(-1));
    }

    static BigDecimal inverseNullZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value.multiply(BigDecimal.valueOf(-1));
    }

    static BigDecimal add(BigDecimal... values) {
        return add(Arrays.asList(values));
    }

    static BigDecimal add(Collection<BigDecimal> values) {
        return values.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static <T> BigDecimal add(Collection<T> list, Function<T, BigDecimal> valueGetter) {
        return add(list.stream().map(valueGetter).toList());
    }

    static BigDecimal centToCoinAmount(BigDecimal centAmount) {
        if (centAmount == null) {
            return null;
        }

        BigDecimal coinAmount = centAmount.movePointLeft(2);
        coinAmount = coinAmount.setScale(2, RoundingMode.DOWN);
        return coinAmount;
    }

    static BigDecimal coinToCentAmount(BigDecimal coinAmount) {
        if (coinAmount == null) {
            return null;
        }

        BigDecimal centAmount = coinAmount.movePointRight(2);
        centAmount = centAmount.setScale(0, RoundingMode.DOWN);
        return centAmount;
    }

    static Long coinToCentAmountAsLong(BigDecimal coinAmount) {
        BigDecimal centAmount = coinToCentAmount(coinAmount);
        return centAmount == null ? null : centAmount.longValue();
    }

    static String toString(BigDecimal value) {
        return value != null ? value.toPlainString() : null;
    }

    static String toString(BigDecimal value, int precision) {
        return value != null ? value.setScale(precision, RoundingMode.DOWN).toPlainString() : null;
    }

    static String toString(BigDecimal value, int precision, boolean stripTrailingZeroes) {
        if (value == null) {
            return null;
        }
        value = value.setScale(precision, RoundingMode.DOWN);
        if (stripTrailingZeroes) {
            value = value.stripTrailingZeros();
        }
        return value.toPlainString();
    }

    static String getSign(BigDecimal value) {
        if (value == null) {
            return null;
        }

        return value.signum() < 0 ? "-" : "+";
    }

    static String toSignedString(BigDecimal value, Integer length) {
        if (value == null) {
            return null;
        }

        String stringValue = value.toPlainString();

        boolean negative = stringValue.startsWith("-");
        if (negative) {
            stringValue = Strings.substring(stringValue, 1);
        }

        if (stringValue.length() > length - 1) {
            throw Exceptions.invalidValueException(value);
        }

        String sign = negative ? "-" : "+";
        return sign + Strings.leftPad(stringValue, length - 1, '0');
    }

    static String toUnsignedString(BigDecimal value, Integer length) {
        if (value == null) {
            return null;
        }

        String stringValue = value.abs().toPlainString();
        return Strings.leftPadZero(stringValue, length);
    }

    static String toStringWithPrecision(BigDecimal value, Integer precision) {
        if (value == null) {
            return null;
        }
        BigDecimal scaled = value.setScale(precision, RoundingMode.DOWN);
        return scaled.toPlainString();
    }

    static BigDecimal getValueOrZeroWhenNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    static String formatAmount(BigDecimal value, boolean absolute) {
        if (value == null) {
            return null;
        } else {
            DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(Locale.FRENCH);
            formatSymbols.setGroupingSeparator('.');

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(true);
            df.setDecimalFormatSymbols(formatSymbols);
            return df.format(absolute ? value.abs() : value);
        }
    }

    static String formatAmount(BigDecimal value) {
        return formatAmount(value, false);
    }

    static String formatAmountWithEuroSign(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return formatAmount(value) + " €";
    }

    static String centToCoinString(BigDecimal centAmount) {
        BigDecimal coinAmount = centToCoinAmount(centAmount);
        return toString(coinAmount);
    }

    static String centToUnsignedCoinString(BigDecimal centAmount, Integer length) {
        BigDecimal coinAmount = centToCoinAmount(centAmount);
        return toUnsignedString(coinAmount, length);
    }

    static String centToSignedCoinString(BigDecimal centAmount, Integer length) {
        BigDecimal coinAmount = centToCoinAmount(centAmount);
        return toSignedString(coinAmount, length);
    }

    static String coinToCentString(BigDecimal coinAmount) {
        BigDecimal centAmount = coinToCentAmount(coinAmount);
        return toString(centAmount);
    }

    static String coinToUnsignedCentString(BigDecimal coinAmount, Integer length) {
        BigDecimal centAmount = coinToCentAmount(coinAmount);
        return toUnsignedString(centAmount, length);
    }

    static String coinToSignedCentString(BigDecimal coinAmount, Integer length) {
        BigDecimal centAmount = coinToCentAmount(coinAmount);
        return toSignedString(centAmount, length);
    }

    /**
     * Returns the given value as a double.
     */
    static Double toDouble(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.doubleValue();
    }

    static BigDecimal getPercentage(Number value, Number totalValue) {
        return getRate(value, totalValue, 100, 2);
    }

    static BigDecimal getPercentageOrMinWhenDenominatorZero(Number value, Number totalValue) {
        return getRateOrMinWhenDenominatorZero(value, totalValue, 100, 2);
    }

    static BigDecimal getPercentageOrMaxWhenDenominatorZero(Number value, Number totalValue) {
        return getRateOrMaxWhenDenominatorZero(value, totalValue, 100, 2);
    }

    static BigDecimal getRate(Number value, Number totalValue, int base, int precision) {
        BigDecimal valueBD = toBigDecimal(value);
        BigDecimal totalValueBD = toBigDecimal(totalValue);
        if (Double.doubleToRawLongBits(totalValueBD.doubleValue()) != 0) {
            BigDecimal percentage = valueBD.divide(totalValueBD, DECIMAL128).multiply(new BigDecimal(base)).abs();
            return percentage.setScale(precision, RoundingMode.HALF_UP);
        }
        return null;
    }

    static BigDecimal getRateOrMinWhenDenominatorZero(Number value, Number totalValue, int base, int precision) {
        BigDecimal rate = getRate(value, totalValue, base, precision);
        return rate != null ? rate : BigDecimal.ZERO.setScale(precision, RoundingMode.HALF_UP);
    }

    static BigDecimal getRateOrMaxWhenDenominatorZero(Number value, Number totalValue, int base, int precision) {
        BigDecimal rate = getRate(value, totalValue, base, precision);
        return rate != null ? rate : BigDecimal.valueOf(base).setScale(precision, RoundingMode.HALF_UP);
    }

    static BigDecimal toBigDecimal(Number number) {
        BigDecimal bigDecimalValue = number != null ? new BigDecimal(number.toString()) : null;
        return BigDecimals.getValueOrZeroWhenNull(bigDecimalValue);
    }

    static String formatPercentage(BigDecimal percentage) {
        return formatPercentage(percentage, 2, null);
    }

    static String formatPercentage(BigDecimal percentage, int precision) {
        return formatPercentage(percentage, precision, null);
    }

    static String formatPercentage(BigDecimal percentage, int precision, String defaultValue) {
        if (percentage == null) {
            return defaultValue;
        } else {
            return toStringWithPrecision(percentage, precision) + "%";
        }
    }

    static Integer toInteger(BigDecimal value) {
        return value != null ? value.intValueExact() : null;
    }
}
