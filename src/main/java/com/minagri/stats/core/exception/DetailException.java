package com.minagri.stats.core.exception;

import jakarta.ws.rs.core.Response.StatusType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Getter
public class DetailException extends RuntimeException {
    private final StatusType status;
    private final String message;
    private final Map<String, String> details = new HashMap<>();

    public DetailException(StatusType status, String message, Map<String, String> details, Throwable throwable) {
        super(message, throwable);
        this.status = status;
        this.message = message;
        this.details.putAll(details);
    }

    public DetailException(StatusType status, String message, Map<String, String> details) {
        super(message);
        this.status = status;
        this.message = message;
        this.details.putAll(details);
    }

    public DetailException(String message, Map<String, String> details, Throwable throwable) {
        super(message, throwable);
        this.status = INTERNAL_SERVER_ERROR;
        this.message = message;
        this.details.putAll(details);
    }

    public DetailException(String message, Map<String, String> details) {
        super(message);
        this.status = INTERNAL_SERVER_ERROR;
        this.message = message;
        this.details.putAll(details);
    }
}
