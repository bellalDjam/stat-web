package com.minagri.stats.core.exception;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@Interceptor
@EnrichException
@Priority(Interceptor.Priority.LIBRARY_AFTER + 1)
public class EnrichExceptionInterceptor {

	@AroundInvoke
	public Object enrichException(InvocationContext invocationContext) {
		try {
			return invocationContext.proceed();
		} catch (Exception e) {
			String method = invocationContext.getMethod().getName();
			Object[] parameters = invocationContext.getParameters();

			if (parameters == null || parameters.length == 0) {
				throw Exceptions.simpleException(e, "Exception calling", method);
			}

			String formattedParameters = Arrays.stream(parameters)
					.map(p -> p == null ? "(null)" : p.toString())
					.collect(Collectors.joining(", "));
			
			throw Exceptions.simpleException(e, "Exception calling", method, "with parameters", formattedParameters);
		}
	}

}
