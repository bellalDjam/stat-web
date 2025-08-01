package com.minagri.stats.vaadin.binder;

import com.minagri.stats.core.java.BigDecimals;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.Translation;

import java.math.BigDecimal;

public interface LcmValidators {

    static <T> LcmValidator<T> required() {
        LcmValidator<T> validator = new LcmValidator<>();
        validator.setPredicate(value -> value != null && Strings.isNotBlank(value.toString()));
        return validator;
    }

    static <T> LcmValidator<T> positiveNumber() {
        LcmValidator<T> validator = new LcmValidator<>();
        validator.setMessageTranslation(Translator.createTranslation(CommonMessage.INVALID_VALUE));
        validator.setPredicate(v -> {
            if (v == null) {
                return true;
            }

            String stringValue = v.toString();
            if (Strings.isNotNumeric(stringValue)) {
                return false;
            }

            BigDecimal bigDecimalValue = Strings.toBigDecimal(stringValue);
            return BigDecimals.isGreaterThan(bigDecimalValue, BigDecimal.ZERO);
        });
        return validator;
    }

    static <T extends Comparable<T>> LcmValidator<T> range(T lowerBound, T upperBound) {
        return range(lowerBound, upperBound, CommonMessage.INVALID_VALUE);
    }

    static <T extends Comparable<T>> LcmValidator<T> range(T lowerBound, T upperBound, Enum<?> messageKey) {
        return range(lowerBound, upperBound, Translator.createTranslation(messageKey));
    }
    
    static <T extends Comparable<T>> LcmValidator<T> range(T lowerBound, T upperBound, Translation messageTranslation) {
        LcmValidator<T> validator = new LcmValidator<>();
        validator.setMessageTranslation(messageTranslation);
        validator.setPredicate(v -> {
            if (v == null) {
                return true;
            }

            boolean lowerBoundOk = lowerBound == null || lowerBound.compareTo(v) <= 0;
            boolean upperBoundOk = upperBound == null ||  v.compareTo(upperBound) <= 0;
            return lowerBoundOk && upperBoundOk;
        });
        return validator;
    }


    static <T extends Comparable<T>> LcmValidator<T> max(T maxValue, Enum<?> messageKey) {
        return range(null, maxValue, messageKey);
    }

    static <T extends Comparable<T>> LcmValidator<T> max(T maxValue) {
        return range(null, maxValue);
    }

    static LcmValidator<String> matching(String regex) {
        return matching(regex, Translator.createTranslation(CommonMessage.INVALID_VALUE));
    }

    static LcmValidator<String> matching(String regex, Translation messageTranslation) {
        LcmValidator<String> validator = new LcmValidator<>();
        validator.setPredicate(value -> Strings.isBlank(value) || value.matches(regex));
        validator.setMessageTranslation(messageTranslation);
        return validator;
    }
}
