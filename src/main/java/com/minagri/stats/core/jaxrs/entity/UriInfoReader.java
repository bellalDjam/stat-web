package com.minagri.stats.core.jaxrs.entity;

import com.minagri.stats.core.java.Dates;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.UriInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class UriInfoReader {
    private UriInfo uriInfo;

    public UriInfoReader(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public String getRequiredStringQueryParameter(String parameter) {
        requireQueryParameter(parameter);
        return uriInfo.getQueryParameters().getFirst(parameter);
    }

    public LocalDateTime getRequiredDateTimeQueryParameter(String parameter) {
        requireQueryParameter(parameter);
        return convertQueryParameter(parameter, Dates::parseDateTime);
    }

    private void requireQueryParameter(String parameter) {
        List<String> strings = uriInfo.getQueryParameters().get(parameter);
        if (strings == null) {
            throw new BadRequestException("query parameter " + parameter + " is required");
        }
    }

    private <T> T convertQueryParameter(String parameter, Function<String, T> conversionMapper) {
        try {
            String value = uriInfo.getQueryParameters().getFirst(parameter);
            return value != null ? conversionMapper.apply(value) : null;
        } catch (Exception e) {
            throw new BadRequestException("query parameter " + parameter + " has an invalid value");
        }
    }
}
