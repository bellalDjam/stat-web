package com.minagri.stats.vaadin.component.dialog;

import com.minagri.stats.vaadin.component.html.LCMH2;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.component.icon.LcmIcon;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasComponentsFluent;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.VaadinIcon;

public class LCMConfirmDialog extends ConfirmDialog implements
        ComponentFluent<LCMConfirmDialog>,
        HasComponentsFluent<LCMConfirmDialog>,
        HasSizeFluent<LCMConfirmDialog>,
        HasStyleFluent<LCMConfirmDialog> {

    private LCMH2 header;
    private LCMHorizontalLayout headerLayout;
    private LCMSpan textSpan;
    
    public LCMConfirmDialog() {
        header = new LCMH2();
        headerLayout = new LCMHorizontalLayout()
                .withAlignItemsCenter()
                .withPadding()
                .withAdd(header);
        setHeader(headerLayout);

        textSpan = new LCMSpan();
        setText(textSpan);
    }

    public LCMConfirmDialog withHeader(Enum<?> headerKey) {
        header.setText(Translator.translate(headerKey));
        return this;
    }

    public LCMConfirmDialog withHeaderKey(TranslationKey headerKey) {
        header.setText(Translator.translateKey(headerKey));
        return this;
    }

    public LCMConfirmDialog withHeaderRed() {
        header.withColorRed();
        return this;
    }

    public LCMConfirmDialog withHeaderIcon(VaadinIcon icon) {
        headerLayout.addComponentAsFirst(new LcmIcon(icon).withSizeLarge());
        return this;
    }

    public LCMConfirmDialog withHeaderIcon(LcmIcon icon) {
        headerLayout.addComponentAsFirst(icon);
        return this;
    }

    public LCMConfirmDialog withText(String text) {
        textSpan.setText(text);
        return this;
    }

    public LCMConfirmDialog withTextRed() {
        textSpan.withColorRed();
        return this;
    }

    public LCMConfirmDialog withConfirmText(String text) {
        setConfirmText(text);
        return this;
    }

    public LCMConfirmDialog withConfirmText(Enum<?> text) {
        setConfirmText(Translator.translate(text));
        return this;
    }

    public LCMConfirmDialog withConfirmTextKey(TranslationKey textKey) {
        setConfirmText(Translator.translateKey(textKey));
        return this;
    }

    public LCMConfirmDialog withConfirmListener(ComponentEventListener<ConfirmEvent> listener) {
        addConfirmListener(listener);
        return this;
    }

    public LCMConfirmDialog withConfirmListener(Runnable listener) {
        addConfirmListener((event) -> listener.run());
        return this;
    }

    public LCMConfirmDialog withConfirmRed() {
        setConfirmButtonTheme(ButtonVariant.LUMO_ERROR.getVariantName() + " " + ButtonVariant.LUMO_PRIMARY.getVariantName());
        return this;
    }

    public LCMConfirmDialog withCancel() {
        setCancelable(true);
        setCancelText(Translator.translate(CommonMessage.CANCEL));
        return this;
    }
    
    public LCMConfirmDialog withCancelText(String text) {
        setCancelable(true);
        setCancelText(text);
        return this;
    }

    @Override
    public LCMConfirmDialog getFluent() {
        return this;
    }
}
