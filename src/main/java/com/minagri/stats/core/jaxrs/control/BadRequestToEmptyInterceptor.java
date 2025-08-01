package com.minagri.stats.core.jaxrs.control;


import com.minagri.stats.core.jaxrs.entity.BadRequestToEmpty;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.WebApplicationException;

import java.util.Optional;

@Interceptor
@BadRequestToEmpty
@Priority(0)
public class BadRequestToEmptyInterceptor {
    @AroundInvoke
    public Object mapBadRequest(InvocationContext ctx) throws Exception {
        try {
            return ctx.proceed();
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 400) {
                boolean isOptionalReturn = ctx.getMethod().getReturnType().isAssignableFrom(Optional.class);
                return isOptionalReturn ? Optional.empty() : null;
            }
            throw e;
        }
    }
}
