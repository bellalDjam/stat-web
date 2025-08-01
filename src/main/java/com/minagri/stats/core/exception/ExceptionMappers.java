package com.minagri.stats.core.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.minagri.stats.core.exception.ExceptionResponse.Detail;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.Status;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.minagri.stats.core.exception.Exceptions.findRootCause;
import static java.util.Optional.ofNullable;
import static org.jboss.resteasy.reactive.RestResponse.Status.*;
import static org.jboss.resteasy.reactive.RestResponse.status;

@Slf4j
public class ExceptionMappers {
    private static String REQUEST_ID_MDC_KEY = "requestId";
    private static String REQUEST_ID_HEADER = "X-Request-ID";
    private static String GENERIC_ERROR = "See application logs for more details";

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> notFoundException(NotFoundException exception) {
        log.debug(exception.getMessage(), exception);

        // Occurs with an invalid enum value in a path param
        if (exception.getCause() instanceof IllegalArgumentException ex) {
            return status(BAD_REQUEST, new ExceptionResponse(BAD_REQUEST, ex.getMessage()));
        }

        String message = ofNullable(findRootCause(exception).getMessage()).orElse(NOT_FOUND.getReasonPhrase());
        return status(NOT_FOUND, new ExceptionResponse(NOT_FOUND, message));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> badRequestException(BadRequestException exception, HttpHeaders httpHeaders) {
        log.error(exception.getMessage(), exception);

        String message = ofNullable(findRootCause(exception).getMessage()).orElse(GENERIC_ERROR);
        Detail detail = new Detail(REQUEST_ID_MDC_KEY, httpHeaders.getHeaderString(REQUEST_ID_HEADER));
        return status(BAD_REQUEST, new ExceptionResponse(BAD_REQUEST, message, List.of(detail)));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> webException(WebApplicationException exception, HttpHeaders httpHeaders) {

        Throwable rootCause = findRootCause(exception);
        Detail detail = new Detail(REQUEST_ID_MDC_KEY, httpHeaders.getHeaderString(REQUEST_ID_HEADER));

        Status status = fromStatusCode(exception.getResponse().getStatus());
        // Occurs with any kind of JSON serialization error
        if (rootCause instanceof IllegalArgumentException) {
            status = INTERNAL_SERVER_ERROR;
        }

        if (status == NOT_FOUND) {
            log.debug(exception.getMessage(), exception);
        } else {
            log.error(exception.getMessage(), exception);
        }
        return status(status, new ExceptionResponse(status, GENERIC_ERROR, List.of(detail)));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> exception(Exception exception, HttpHeaders httpHeaders) {
        log.error(exception.getMessage(), exception);

        Detail detail = new Detail(REQUEST_ID_MDC_KEY, httpHeaders.getHeaderString(REQUEST_ID_HEADER));
        return status(INTERNAL_SERVER_ERROR, new ExceptionResponse(INTERNAL_SERVER_ERROR, GENERIC_ERROR, List.of(detail)));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> constraintViolationException(ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);

        List<Detail> errorMessages = exception.getConstraintViolations().stream()
                .map(constraintViolation -> new Detail(
                        constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getMessage()
                ))
                .toList();

        return status(BAD_REQUEST, new ExceptionResponse(BAD_REQUEST, errorMessages));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> mismatchedInputException(MismatchedInputException exception) {
        log.error(exception.getMessage(), exception);

        List<Detail> details = new ArrayList<>();
        if (exception instanceof InvalidFormatException ex
                && ex.getValue() instanceof String value) {
            details.add(new Detail("value", value));
        }
        return status(BAD_REQUEST, new ExceptionResponse(BAD_REQUEST, "Unable to parse input", details));
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionResponse> detailException(DetailException exception, HttpHeaders headers) {
        if (exception.getStatus() != null && exception.getStatus().getStatusCode() == 404) {
            log.debug(exception.getMessage(), exception);
        } else {
            log.error(exception.getMessage(), exception);
        }

        List<Detail> details = exception.getDetails().entrySet().stream()
                .map(entry -> new Detail(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        Detail detail = new Detail(REQUEST_ID_MDC_KEY, headers.getHeaderString(REQUEST_ID_HEADER));
        details.add(detail);

        ExceptionResponse entity = new ExceptionResponse(exception.getStatus(), exception.getMessage(), details);
        return RestResponse.status(exception.getStatus(), entity);
    }
}
