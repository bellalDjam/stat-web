package com.minagri.stats.vaadin.component.button;

import com.minagri.stats.core.java.Objects;
import com.minagri.stats.vaadin.component.combobox.LCMRadioButtonGroup;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.ButtonVariant;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Tag("vaadin-vertical-layout")
public class LCMButtonGroup<T> extends Component implements ComponentFluent<LCMButtonGroup<T>> {
    private LCMRadioButtonGroup<Object> dummyComponentForLabel;
    private LCMHorizontalLayout buttonLayout;

    private Function<T, Translation> buttonTextTranslation;
    private Consumer<T> valueChangeListener;

    @Getter
    private T value;
    private List<T> items = new ArrayList<>();
    private Map<T, LCMButton> itemButtons = new HashMap<>();

    public LCMButtonGroup() {
        dummyComponentForLabel = new LCMRadioButtonGroup<>().withHeight("20px");
        buttonLayout = new LCMHorizontalLayout().withNoMargin().withNoSpacing().withNoPadding();

        getElement().appendChild(dummyComponentForLabel.getElement());
        getElement().appendChild(buttonLayout.getElement());
    }

    public LCMButtonGroup<T> setItems(Collection<T> collection) {
        items.clear();
        items.addAll(collection);
        refreshItemButtons();
        return this;
    }

    @SafeVarargs
    public final LCMButtonGroup<T> setItems(T... items) {
        return setItems(Arrays.asList(items));
    }

    public LCMButtonGroup<T> setButtonTextTranslation(Function<T, Translation> buttonTextTranslation) {
        this.buttonTextTranslation = buttonTextTranslation;
        refreshItemButtons();
        return this;
    }

    public LCMButtonGroup<T> setButtonTextKey(Function<T, TranslationKey> buttonTextKey) {
        return setButtonTextTranslation(item -> Translator.createTranslation(buttonTextKey.apply(item)));
    }

    public LCMButtonGroup<T> setButtonTextEnum(Function<T, Enum<?>> buttonTextEnum) {
        return setButtonTextTranslation(item -> Translator.createTranslation(buttonTextEnum.apply(item)));
    }

    public LCMButtonGroup<T> setButtonText(Function<T, String> buttonText) {
        return setButtonTextTranslation(item -> Translator.createTranslation(buttonText.apply(item)));
    }

    public LCMButtonGroup<T> setLabelKey(TranslationKey labelTextKey) {
        dummyComponentForLabel.withLabelKey(labelTextKey);
        return this;
    }

    public LCMButtonGroup<T> setLabel(String label) {
        dummyComponentForLabel.withLabel(label);
        return this;
    }

    public LCMButtonGroup<T> setLabel(Enum<?> label) {
        dummyComponentForLabel.withLabel(label);
        return this;
    }

    public LCMButtonGroup<T> setValue(T value) {
        T oldValue = this.value;
        this.value = value;

        items.forEach(item -> {
            LCMButton button = itemButtons.get(item);
            if (item.equals(value)) {
                button.withStyle("--lumo-primary-color", null);
                button.withStyle("--_lumo-button-primary-text-color", null);
            } else {
                button.withStyle("--lumo-primary-color", "rgba(25, 59, 103, 0.05)");
                button.withStyle("--_lumo-button-primary-text-color", "rgb(78, 166, 83)");
            }
        });

        if (valueChangeListener != null && Objects.notEqualTo(oldValue, value)) {
            valueChangeListener.accept(value);
        }

        return this;
    }

    public LCMButtonGroup<T> setValueChangeListener(Consumer<T> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
        return this;
    }

    void refreshItemButtons() {
        buttonLayout.removeAll();
        itemButtons.clear();

        items.forEach(item -> {
            LCMButton itemButton = new LCMButton()
                    .withThemeVariants(ButtonVariant.LUMO_PRIMARY)
                    .withStyle("--lumo-border-radius", "var(--lumo-border-radius-l)")
                    .withClickListener(() -> setValue(item));

            if (buttonTextTranslation != null) {
                itemButton.withText(buttonTextTranslation.apply(item));
            } else {
                itemButton.withText(item.toString());
            }
            buttonLayout.add(itemButton);
            itemButtons.put(item, itemButton);

            boolean isFirst = item.equals(items.getFirst());
            boolean isLast = item.equals(items.getLast());
            if (isFirst) {
                itemButton.withStyle("border-bottom-right-radius", "0");
                itemButton.withStyle("border-top-right-radius", "0");
            } else if (isLast) {
                itemButton.withStyle("border-bottom-left-radius", "0");
                itemButton.withStyle("border-top-left-radius", "0");
            } else {
                itemButton.withStyle("border-bottom-right-radius", "0");
                itemButton.withStyle("border-top-right-radius", "0");
                itemButton.withStyle("border-bottom-left-radius", "0");
                itemButton.withStyle("border-top-left-radius", "0");
            }
        });
    }

    @Override
    public LCMButtonGroup<T> getFluent() {
        return this;
    }
}