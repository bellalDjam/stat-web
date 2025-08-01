package com.minagri.stats.core.exception;

import com.minagri.stats.core.java.Strings;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface Exceptions {

    static RuntimeException invalidValueException(Object value) {
        if (value == null) {
            return new RuntimeException("Invalid null value");
        }
        return new RuntimeException("Invalid value: " + value);
    }

    static RuntimeException serviceException(String service) {
        return new RuntimeException("Error calling service " + service);
    }

    static RuntimeException serviceException(String service, Throwable cause) {
        return new RuntimeException("Error calling service " + service, cause);
    }

    static RuntimeException invalidStateException(Throwable cause, Object... messages) {
        return new RuntimeException(message("Invalid application state:", messages), cause);
    }

    static RuntimeException webServiceErrorException(Throwable cause, Object... messages) {
        return new RuntimeException(message("Web service error", messages), cause);
    }

    static RuntimeException simpleException(Throwable cause, Object... messages) {
        if (cause == null) {
            return new RuntimeException(message(messages));
        }
        return new RuntimeException(message(messages), cause);
    }

    @SneakyThrows
    static <T> T sneakyThrows(ExceptionSupplier<T> supplier) {
        return supplier.get();
    }

    @SneakyThrows
    static void sneakyThrows(ExceptionRunnable runnable) {
        runnable.run();
    }

    static Throwable findRootCause(Throwable throwable) {
        Objects.requireNonNull(throwable);

        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    private static String message(Object... messages) {
        List<String> stringMessages = new ArrayList<>();
        for (Object m : messages) {
            stringMessages.add(Objects.toString(m, "[null]"));
        }

        return Strings.joinWithSpace(stringMessages);
    }
}
