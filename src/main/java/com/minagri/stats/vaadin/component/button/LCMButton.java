package com.minagri.stats.vaadin.component.button;

import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.component.icon.LcmIcon;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;

import java.util.HashMap;
import java.util.Map;

public class LCMButton extends Button implements
        ComponentFluentAuthorized<LCMButton>,
        HasSizeFluent<LCMButton>,
        HasEnabledFluent<LCMButton>,
        HasTextFluent<LCMButton>,
        FocusableFluent<LCMButton>,
        HasStyleFluent<LCMButton>,
        HasThemeFluent<LCMButton>,
        ClickNotifierFluent<LCMButton> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private Action action;

    public LCMButton withIcon(Component icon) {
        setIcon(icon);
        return this;
    }

    public LCMButton withIconAfterText() {
        return withIconAfterText(true);
    }

    public LCMButton withIconBeforeText() {
        return withIconAfterText(false);
    }

    public LCMButton withIconAfterText(boolean value) {
        setIconAfterText(value);
        return this;
    }

    public LCMButton withDisableOnClick(boolean value) {
        setDisableOnClick(value);
        return this;
    }

    public LCMButton withDisableOnClick() {
        return withDisableOnClick(true);
    }

    public LCMButton withAutoFocus(boolean value) {
        setAutofocus(value);
        return this;
    }

    public LCMButton withAutoFocus() {
        return withAutoFocus(true);
    }

    public LCMButton withThemeVariants(ButtonVariant... variants) {
        addThemeVariants(variants);
        return this;
    }

    public LCMButton withRemoveThemeVariants(ButtonVariant... variants) {
        removeThemeVariants(variants);
        return this;
    }

    public LCMButton withPrimaryTheme() {
        return withThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    public LCMButton withTertiaryTheme() {
        return withThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }

    public LCMButton withTertiaryInlineTheme() {
        return withThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
    }

    public LCMButton withSuccessTheme() {
        return withThemeVariants(ButtonVariant.LUMO_SUCCESS);
    }

    public LCMButton withLargeTheme() {
        return withThemeVariants(ButtonVariant.LUMO_LARGE);
    }

    public LCMButton withSmallTheme() {
        return withThemeVariants(ButtonVariant.LUMO_SMALL);
    }

    public LCMButton withContrastTheme() {
        return withThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }

    public LCMButton withErrorTheme() {
        return withThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    public LCMButton withIconTheme() {
        return withThemeVariants(ButtonVariant.LUMO_ICON);
    }

    public LCMButton withCompactIcon(VaadinIcon icon) {
        return withCompactIcon(new LcmIcon(icon));
    }

    public LCMButton withCompactIcon(LcmIcon icon) {
        withIcon(icon);
        setPadding("0");
        setMarginZero();
        withIconTheme();
        setBackgroundTransparent();
        return this;
    }

    public LCMButton withClick() {
        click();
        return this;
    }

    @Override
    public Registration withClickListenerRemovable(ComponentEventListener<ClickEvent<LCMButton>> listener) {
        withDisableOnClick();
        return getFluent().addClickListener(event -> {
            setEnabled(true);
            if (event == null) {
                listener.onComponentEvent(null);
            } else {
                listener.onComponentEvent(new ClickEvent<>(event.getSource(),
                        event.isFromClient(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getClientX(),
                        event.getClientY(),
                        event.getClickCount(),
                        event.getButton(),
                        event.isCtrlKey(),
                        event.isShiftKey(),
                        event.isAltKey(),
                        event.isMetaKey()));
            }
        });
    }

    @Override
    public LCMButton withClickShortcut(Key key, KeyModifier... keyModifiers) {
        UI.getCurrent().addShortcutListener(this::click, key, keyModifiers);
        return this;
    }

    @Override
    public LCMButton getFluent() {
        return this;
    }

    @Override
    public LCMButton setAction(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }
}
