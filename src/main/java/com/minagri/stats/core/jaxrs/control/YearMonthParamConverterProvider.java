package com.minagri.stats.core.jaxrs.control;

import com.minagri.stats.core.java.Dates;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.YearMonth;

@Provider
public class YearMonthParamConverterProvider implements ParamConverterProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(YearMonth.class)) {
            return (ParamConverter<T>) new YearMonthParamConverter();
        }
        return null;
    }

    public static class YearMonthParamConverter implements ParamConverter<YearMonth> {

        @Override
        public YearMonth fromString(String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            return Dates.safeParseYearMonth(value);
        }

        @Override
        public String toString(YearMonth value) {
            if (value == null) {
                return null;
            }
            return Dates.format(value, Dates.YYYYMM);
        }
    }
}