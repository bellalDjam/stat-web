package com.minagri.stats.core.jaxrs.control;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.core.java.Objects;
import com.minagri.stats.core.java.Strings;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.minagri.stats.core.enumeration.control.Enums.getFromCodeOrName;
import static com.minagri.stats.core.java.Dates.parseDate;
import static com.minagri.stats.core.java.Strings.*;
import static java.util.Collections.emptyList;

public interface UrlParameters {
    Pattern MATRIX_PARAM_PATTERN = Pattern.compile(";([a-zA-Z0-9_]+)=([^;/]+)");

    static String getStringParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        if (parameters == null) {
            return null;
        }
        List<String> parameterValues = parameters.get(parameterName);
        String firstParameter = Collections.getFirstOrNull(parameterValues);
        return blankToNull(firstParameter);
    }

    static Long getLongParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return toLong(getStringParameter(parameters, parameterName));
    }

    static Integer getIntegerParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return toInteger(getStringParameter(parameters, parameterName));
    }

    static BigDecimal getBigDecimalParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return toBigDecimal(getStringParameter(parameters, parameterName));
    }

    static LocalDate getDateParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return parseDate(getStringParameter(parameters, parameterName));
    }

    static Boolean getBooleanParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return Strings.toBoolean(getStringParameter(parameters, parameterName));
    }

    static LocalDate getDateParameter(MultivaluedMap<String, String> parameters, String parameterName, LocalDate defaultValue) {
        LocalDate dateParameter = getDateParameter(parameters, parameterName);
        return Objects.getValueOrDefault(dateParameter, defaultValue);
    }

    static <T extends Enum<?>> T getEnumParameter(MultivaluedMap<String, String> parameters, String parameterName, Class<T> enumClass) {
        return getFromCodeOrName(getStringParameter(parameters, parameterName), enumClass);
    }

    static List<String> getStringListParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        List<String> parameterValues = parameters.get(parameterName);
        return Collections.safeStream(parameterValues).filter(Strings::isNotBlank).toList();
    }

    static List<Integer> getIntegerListParameter(MultivaluedMap<String, String> parameters, String parameterName) {
        return toIntegerList(getStringListParameter(parameters, parameterName));
    }

    static boolean hasBlankValue(MultivaluedMap<String, String> parameters, String parameterName) {
        List<String> parameterValues = parameters.get(parameterName);
        return parameterValues.stream().anyMatch(Strings::isBlank);
    }

    static MultivaluedMap<String, String> getMatrixParameters(UriInfo uriInfo) {
        MultivaluedHashMap<String, String> matrixParameters = new MultivaluedHashMap<>();
        uriInfo.getMatchedURIs().forEach(uri -> {
            Matcher matrixParamMatcher = MATRIX_PARAM_PATTERN.matcher(uri);
            while (matrixParamMatcher.find()) {
                String parameterName = matrixParamMatcher.group(1);
                String parameterValue = URLDecoder.decode(matrixParamMatcher.group(2), Charset.defaultCharset());

                List<String> parameterValues = new ArrayList<>(matrixParameters.getOrDefault(parameterName, emptyList()));
                parameterValues.add(parameterValue);

                matrixParameters.put(parameterName, parameterValues);
            }
        });
        return matrixParameters;
    }

    static MultivaluedMap<String, String> getMatrixOrQueryParameters(UriInfo uriInfo) {
        MultivaluedMap<String, String> matrixParameters = getMatrixParameters(uriInfo);
        if (matrixParameters.isEmpty() && uriInfo.getQueryParameters() != null) {
            return uriInfo.getQueryParameters();
        }
        return matrixParameters;
    }
}
