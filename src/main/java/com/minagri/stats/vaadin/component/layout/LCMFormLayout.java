package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.component.html.LCMDiv;
import com.minagri.stats.vaadin.component.html.LCMLabel;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LCMFormLayout extends FormLayout implements
        ComponentFluent<LCMFormLayout>,
        HasSizeFluent<LCMFormLayout>,
        HasEnabledFluent<LCMFormLayout>,
        HasStyleFluent<LCMFormLayout>,
        HasComponentsFluent<LCMFormLayout> {

    @Deprecated // bad naming for list, use withResponsiveSteps
    public LCMFormLayout withResponsiveStep(List<ResponsiveStep> steps) {
        return withResponsiveSteps(steps);
    }

    public LCMFormLayout withResponsiveSteps(List<ResponsiveStep> steps) {
        this.setResponsiveSteps(steps);
        return getFluent();
    }

    public LCMFormLayout withResponsiveSteps(ResponsiveStep... steps) {
        this.setResponsiveSteps(steps);
        return getFluent();
    }

    public LCMFormLayout withHorizontalColumnExpansion() {
        return withHorizontalColumnExpansion(225, 300);
    }

    public LCMFormLayout withHorizontalColumnExpansion(int columnSpaceInPixels) {
        return withHorizontalColumnExpansion(225, columnSpaceInPixels);
    }

    public LCMFormLayout withHorizontalColumnExpansion(int additionSpaceInPixels, int columnSpaceInPixels) {
        return withResponsiveSteps(
                new ResponsiveStep("0px", 1),
                new ResponsiveStep(additionSpaceInPixels + (columnSpaceInPixels * 2) + "px", 2),
                new ResponsiveStep(additionSpaceInPixels + (columnSpaceInPixels * 3) + "px", 3),
                new ResponsiveStep(additionSpaceInPixels + (columnSpaceInPixels * 4) + "px", 4),
                new ResponsiveStep(additionSpaceInPixels + (columnSpaceInPixels * 5) + "px", 5),
                new ResponsiveStep(additionSpaceInPixels + (columnSpaceInPixels * 6) + "px", 6)
        );
    }

    public LCMFormLayout withColumns(int columns) {
        return withResponsiveSteps(new ResponsiveStep("0px", columns));
    }

    public LCMFormItem addFormItem(Component field, Enum<?> labelKey, Object... messageParameters) {
        return addFormItem(field, new LCMLabel().withText(labelKey, messageParameters));
    }

    public LCMFormLayout withAdd(Component component, int colspan) {
        withAdd(component).setColspan(component, colspan);
        return this;
    }

    public LCMFormLayout withFormItem(Component field) {
        addFormItem(field);
        return this;
    }

    public LCMFormLayout withFormItem(Component field, TranslationKey translationKey) {
        addFormItem(field, translationKey);
        return this;
    }

    @SafeVarargs
    public final LCMFormLayout withFormItem(Component field, TranslationKey translationKey, Consumer<LCMFormItem>... consumers) {
        LCMFormItem formItem = addFormItem(field, translationKey);
        Arrays.stream(consumers).forEach(consumer -> consumer.accept(formItem));
        return this;
    }

    public LCMFormLayout withEmptyFormItem() {
        addFormItem(new LCMDiv());
        return this;
    }

    public LCMFormLayout setLabelWidthPixels(Integer labelWidthPixels) {
        setLabelWidth(labelWidthPixels + "px");
        return this;
    }

    public LCMFormItem addFormItem(Component field, int colspan, TranslationKey translationKey, Object... messageParameters) {
        LCMFormItem formItem = addFormItem(field, translationKey, messageParameters);
        formItem.getElement().setAttribute("colspan", colspan + "");
        return formItem;
    }

    public LCMFormItem addFormItem(Component field, TranslationKey translationKey, Object... messageParameters) {
        return addFormItem(field, new LCMLabel().withTextKey(translationKey, messageParameters));
    }

    public LCMFormItem addFormItem(Component field) {
        return addFormItem(field, new LCMLabel());
    }

    public LCMFormItem addFormItemWithKey(Component field, TranslationKey labelKey, Object... messageParameters) {
        return addFormItem(field, new LCMLabel().withTextKey(labelKey, messageParameters));
    }

    public LCMFormItem addFormItem(Component field, int colspan, Translation translation) {
        LCMFormItem formItem = addFormItem(field, new LCMLabel().withTextKey(translation.getKey(), translation.getMessageParameters()));
        formItem.getElement().setAttribute("colspan", colspan + "");
        return formItem;
    }

    public LCMFormItem addFormItemWithKey(Component field, String labelKey, Object... messageParameters) {
        return addFormItem(field, new LCMLabel().withTextKey(labelKey, messageParameters));
    }

    public LCMFormItem addFormItem(Component field, Translation translation) {
        return addFormItem(field, new LCMLabel().withTextKey(translation.getKey(), translation.getMessageParameters()));
    }

    @Override
    public LCMFormItem addFormItem(Component field, String label) {
        return (LCMFormItem) super.addFormItem(field, label);
    }

    @Override
    public LCMFormItem addFormItem(Component field, Component label) {
        LCMFormItem formItem = new LCMFormItem(this).withAdd(field);
        formItem.addToLabel(label);
        add(formItem);
        return formItem;
    }

    public LCMFormLayout withFormItemVisible(Component field, boolean visible) {
        getChildren()
                .filter(LCMFormItem.class::isInstance)
                .map(LCMFormItem.class::cast)
                .filter(formItem -> formItem.getChildren().findFirst().orElse(null) == field)
                .findFirst()
                .ifPresent(formItem -> formItem.setVisible(visible));
        return this;
    }

    public LCMFormLayout withFormItemEnabled(Component field, boolean enabled) {
        getChildren()
                .filter(LCMFormItem.class::isInstance)
                .map(LCMFormItem.class::cast)
                .filter(formItem -> formItem.getChildren().findFirst().orElse(null) == field)
                .findFirst()
                .ifPresent(formItem -> formItem.setEnabled(enabled));
        return this;
    }

    public LCMFormLayout withFormItemEnabled(Component field) {
        return withFormItemEnabled(field, true);
    }

    public LCMFormLayout withFormItemDisabled(Component field) {
        return withFormItemEnabled(field, false);
    }

    @Override
    public LCMFormLayout getFluent() {
        return this;
    }
}
