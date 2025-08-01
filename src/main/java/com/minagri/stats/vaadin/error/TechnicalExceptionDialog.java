/*
 * Copyright 2003-2009 LCM-ANMC, Inc. All rights reserved.
 * This source code is the property of LCM-ANMC, Direction
 * Informatique and cannot be copied or distributed without
 * the formal permission of LCM-ANMC.
 */
package com.minagri.stats.vaadin.error;

import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.dialog.LCMDialog;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.component.textfield.LCMTextArea;
import com.minagri.stats.vaadin.component.textfield.LCMTextField;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TechnicalExceptionDialog extends LCMDialog {

    private LCMSpan title;
    private LCMSpan exceptionMessage;
    private LCMTextField uuidField;
    private LCMButton showTextAreaButton;
    private LCMTextArea stackTraceMessageTextArea;
    private LCMButton closeButton;

    public TechnicalExceptionDialog(Throwable throwable, String errorId) {
        this.buildScreen();
        this.exceptionMessage.setText(throwable.getMessage());
        this.uuidField.setValue(errorId);
        this.stackTraceMessageTextArea.setValue(formatException(throwable));
    }

    protected void buildScreen() {
        this.title = new LCMSpan().withText(CommonMessage.EXCEPTION_POPUP_TITLE);
        this.title.getElement().getStyle().set("font-size", "var(--lumo-font-size-xxxl)");
        this.exceptionMessage = new LCMSpan();
        this.exceptionMessage.getElement().getStyle().set("font-size", "var(--lumo-font-size-xl)");
        this.uuidField = new LCMTextField().withLabel(CommonMessage.UNIQUE_IDENTIFIER).withReadOnly().withWidth("400px");
        this.showTextAreaButton = new LCMButton().withText(CommonMessage.SHOW_MORE).withIcon(VaadinIcon.PLUS_CIRCLE_O.create()).withClickListener(event -> {
            this.stackTraceMessageTextArea.setVisible(!this.stackTraceMessageTextArea.isVisible());
            if (this.stackTraceMessageTextArea.isVisible()) {
                this.showTextAreaButton.withText(CommonMessage.SHOW_LESS).withIcon(VaadinIcon.MINUS_CIRCLE_O.create());
            } else {
                this.showTextAreaButton.withText(CommonMessage.SHOW_MORE).withIcon(VaadinIcon.PLUS_CIRCLE_O.create());
            }
        });

        this.stackTraceMessageTextArea = new LCMTextArea().withReadOnly().withHeight("500px").withWidth("100%").withInvisible();
        this.closeButton = new LCMButton().withText(CommonMessage.CLOSE).withClickListener(event -> this.close());
        LCMVerticalLayout container = new LCMVerticalLayout().withWidth("100%")
                .withAdd(this.title, this.exceptionMessage, this.uuidField, this.showTextAreaButton, this.stackTraceMessageTextArea, this.closeButton)
                .withWidth("60vw");
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.END, this.closeButton);
        this.withAdd(container);
    }

    private String formatException(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter stackTraceWriter = new PrintWriter(stringWriter);
        e.printStackTrace(stackTraceWriter);
        return stringWriter.toString();
    }

}
