package com.minagri.stats.vaadin.error;

import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class LCMErrorHandler implements ErrorHandler {

    @Override
    public void error(ErrorEvent event) {

        // Log error.
        String errorId = UUID.randomUUID().toString();
        String message = "Error " + errorId + ": " + event.getThrowable().getMessage();
        log.error(message, event.getThrowable());

        new TechnicalExceptionDialog(event.getThrowable(), errorId).open();
    }

}