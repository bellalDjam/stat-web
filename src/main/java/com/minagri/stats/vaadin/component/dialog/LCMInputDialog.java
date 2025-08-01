package com.minagri.stats.vaadin.component.dialog;

import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasComponentsFluent;
import com.minagri.stats.vaadin.fluent.HasEnabledFluent;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.function.Supplier;

@Getter
public class LCMInputDialog extends Dialog implements
        ComponentFluent<LCMInputDialog>,
        HasSizeFluent<LCMInputDialog>,
        HasEnabledFluent<LCMInputDialog>,
        HasComponentsFluent<LCMInputDialog>,
        IsDialogFluent<LCMInputDialog> {

    private LCMVerticalLayout inputForm;
    private LCMButton confirm;
    private LCMButton cancel;

    public LCMInputDialog() {
        inputForm = new LCMVerticalLayout()
                .withNoPadding()
                .withSpacing();

        confirm = new LCMButton()
                .withText(CommonMessage.CONFIRM)
                .withPrimaryTheme();

        cancel = new LCMButton()
                .withText(CommonMessage.CANCEL)
                .withClickListener(this::close);

        LCMHorizontalLayout footer = new LCMHorizontalLayout()
                .withAdd(cancel, confirm)
                .withWidthFull()
                .withJustifyContentModeBetween();

        LCMVerticalLayout content = new LCMVerticalLayout()
                .withAdd(inputForm, footer)
                .withNoPadding();

        add(content);
        setMinWidth(20, Unit.VW);
    }

    @Override
    public LCMInputDialog getFluent() {
        return this;
    }

    public LCMInputDialog withConfirmText(Enum<?> confirmText) {
        confirm.withText(confirmText);
        return this;
    }

    public LCMInputDialog withConfirmTextKey(TranslationKey confirmTextKey) {
        confirm.withTextKey(confirmTextKey);
        return this;
    }

    public LCMInputDialog withConfirmRed() {
        confirm.withErrorTheme();
        return this;
    }

    public LCMInputDialog withConfirmListener(Runnable confirmListener) {
        confirm.withClickListener(() -> {
            confirmListener.run();
            close();
        });
        return this;
    }

    public LCMInputDialog withConfirmListener(Binder<?> inputBinder, Runnable confirmListener) {
        confirm.withClickListener(() -> {
            if (inputBinder.validate().isOk()) {
                confirmListener.run();
                close();
            }
        });
        return this;
    }

    public <T> LCMInputDialog withConfirmListener(Binder<T> inputBinder, T inputBean, Runnable confirmListener) {
        confirm.withClickListener(() -> {
            if (inputBinder.writeBeanIfValid(inputBean)) {
                confirmListener.run();
                close();
            }
        });
        return this;
    }

    public LCMInputDialog withCancelText(Enum<?> cancelText) {
        cancel.withText(cancelText);
        return this;
    }

    public LCMInputDialog withCancelListener(Runnable cancelListener) {
        cancel.withClickListener(cancelListener);
        return this;
    }

    public LCMInputDialog withInputComponent(Component component) {
        inputForm.add(component);
        return this;
    }

    @SneakyThrows
    public <T extends Component & HasValue<?, V>, V> T addInputComponent(Class<T> componentClass, V value) {
        T component = componentClass.getConstructor().newInstance();
        component.setValue(value);
        inputForm.add(component);
        return component;
    }

    @SneakyThrows
    public <T extends Component> T addInputComponent(Class<T> componentClass) {
        T component = componentClass.getConstructor().newInstance();
        inputForm.add(component);
        return component;
    }

    public <T extends Component> T addInputComponent(Supplier<T> componentSupplier) {
        T component = componentSupplier.get();
        inputForm.add(component);
        return component;
    }
}
