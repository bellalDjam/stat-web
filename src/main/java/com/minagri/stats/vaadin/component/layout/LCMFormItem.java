package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.component.html.LCMLabel;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasComponentsFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.dom.Element;

import java.util.Optional;

public class LCMFormItem extends FormLayout.FormItem implements
        HasComponentsFluent<LCMFormItem>,
        ComponentFluent<LCMFormItem>,
        HasStyleFluent<LCMFormItem> {

    private LCMFormLayout formLayout;

    public LCMFormItem() {

    }

    public LCMFormItem(LCMFormLayout formLayout) {
        this.formLayout = formLayout;
    }

    public LCMFormLayout getFormLayout() {
        return formLayout;
    }

    @Override
    public void addToLabel(Component... components) {
        super.addToLabel(components);
    }

    public LCMFormItem withLabel(TranslationKey translationKey, Object... messageParameters) {
        this.addToLabel(new LCMLabel().withTextKey(translationKey, messageParameters));
        return getFluent();
    }

    public LCMFormItem withLabel(Translation translation) {
        this.addToLabel(new LCMLabel().withTextKey(translation.getKey(), translation.getMessageParameters()));
        return getFluent();
    }

    public String getLabel() {
        Optional<Element> optionalElement = this.getElement()
                .getChildren()
                .filter(child -> "label".equals(child.getAttribute("slot")))
                .flatMap(Element::getChildren)
                .filter(Element::isTextNode)
                .findFirst();
        if (optionalElement.isPresent()) {
            return optionalElement.get().getText();
        } else {
            return "";
        }
    }

    public LCMFormItem withColspan(int colspan) {
        formLayout.setColspan(this, colspan);
        return this;
    }

    @Override
    public LCMFormItem getFluent() {
        return this;
    }
}
