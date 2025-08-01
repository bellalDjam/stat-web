package com.minagri.stats.core.exception;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import org.jboss.logmanager.MDC;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

import java.util.UUID;

public class ExceptionContextFilter {
    private static String REQUEST_ID_HEADER = "X-Request-ID";
    private static String REQUEST_ID_MDC_KEY = "requestId";

    @ServerRequestFilter
    public void requestFilter(ContainerRequestContext context) {
        var requestId = context.getHeaderString(REQUEST_ID_HEADER);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
            context.getHeaders().add(REQUEST_ID_HEADER, requestId);
        }

        MDC.put(REQUEST_ID_MDC_KEY, requestId);
    }

    @ServerResponseFilter
    public void responseFilter(ContainerResponseContext context) {
        MDC.remove(REQUEST_ID_MDC_KEY);
    }
}
