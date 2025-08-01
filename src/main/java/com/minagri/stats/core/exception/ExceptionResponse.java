package com.minagri.stats.core.exception;

import jakarta.ws.rs.core.Response;

import java.util.List;

public record ExceptionResponse(int status, String message, List<Detail> details) {

    public ExceptionResponse(Response.StatusType status) {
        this(status.getStatusCode(), status.getReasonPhrase(), null);
    }

    public ExceptionResponse(Response.StatusType status, String message) {
        this(status.getStatusCode(), message, null);
    }

    public ExceptionResponse(Response.StatusType status, String message, List<Detail> details) {
        this(status.getStatusCode(), message, details);
    }

    public ExceptionResponse(Response.StatusType status, List<Detail> details) {
        this(status.getStatusCode(), status.getReasonPhrase(), details);
    }

    public record Detail(String property, String message) {
    }
}
