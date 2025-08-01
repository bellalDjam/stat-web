package com.minagri.stats.core.jaxrs.control;


import com.minagri.stats.core.jaxrs.entity.NotFoundToEmpty;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.WebApplicationException;

import java.util.Optional;

@Interceptor
@NotFoundToEmpty
@Priority(0)
public class NotFoundToEmptyInterceptor {
    @AroundInvoke
    public Object mapNotFound(InvocationContext ctx) throws Exception {
        try {
            return ctx.proceed();
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                boolean isOptionalReturn = ctx.getMethod().getReturnType().isAssignableFrom(Optional.class);
                return isOptionalReturn ? Optional.empty() : null;
            }
            throw e;
        }
    }
}
