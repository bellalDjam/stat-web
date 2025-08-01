package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.html.LCMLegend;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.css.LCMCss;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag("fieldset")
public class LCMCollapsibleFieldset extends HtmlContainer implements
        ClickNotifier<LCMCollapsibleFieldset>,
        ComponentFluent<LCMCollapsibleFieldset>,
        HasSizeFluent<LCMCollapsibleFieldset>,
        HasEnabledFluent<LCMCollapsibleFieldset>,
        HasTextFluent<LCMCollapsibleFieldset>,
        HasStyleFluent<LCMCollapsibleFieldset>,
        HtmlComponentFluent<LCMCollapsibleFieldset>,
        HasComponentsFluent<LCMCollapsibleFieldset>,
        ClickNotifierFluent<LCMCollapsibleFieldset> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private Icon expandIcon = VaadinIcon.EXPAND_SQUARE.create();
    private Icon collapseIcon = VaadinIcon.COMPRESS_SQUARE.create();

    private boolean collapsed;

    private LCMButton toggleButton;
    private LCMSpan legendTitle;
    private LCMHorizontalLayout titleIconLayout;
    private LCMVerticalLayout contentLayout;
    private LCMSpan collapsedTextSpan;

    public LCMCollapsibleFieldset() {
        setClassName(LCMCss.PAPER);
        setWidthFull();
        setMarginZero();

        toggleButton = new LCMButton().withThemeVariants(ButtonVariant.LUMO_ICON)
                .withSmallTheme()
                .withStyle("background", "transparent")
                .withIcon(collapseIcon)
                .withClickListener(event -> toggleCollapse());

        legendTitle = new LCMSpan();

        contentLayout = new LCMVerticalLayout()
                .withNoMargin()
                .withNoPadding()
                .withSizeFull();

        collapsedTextSpan = new LCMSpan()
                .withVisible(false);

        titleIconLayout = new LCMHorizontalLayout()
                .withNoPadding()
                .withVisible(false);

        LCMLegend legend = new LCMLegend().withAdd(new LCMHorizontalLayout()
                .withAdd(toggleButton, legendTitle, titleIconLayout)
                .withJustifyContentModeStart()
                .withAlignItemsCenter());

        // Explicitly call super add(Collection<Component> components) to bypass the overridden HasComponents methods
        super.add(List.of(legend, contentLayout, collapsedTextSpan));
    }

    @Override
    public LCMCollapsibleFieldset getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    public LCMCollapsibleFieldset setLegendText(Enum<?> key, Object... messageParameters) {
        legendTitle.withText(key, messageParameters);
        return this;
    }

    public LCMCollapsibleFieldset setLegendText(TranslationKey translationKey, Object... messageParameters) {
        legendTitle.withTextKey(translationKey, messageParameters);
        return this;
    }

    public LCMCollapsibleFieldset setLegendText(String text) {
        legendTitle.withText(text);
        return this;
    }

    public LCMCollapsibleFieldset toggleCollapse() {
        if (collapsed) {
            expand();
        } else {
            collapse();
        }
        return this;
    }

    public LCMCollapsibleFieldset collapse() {
        collapsed = true;
        contentLayout.setVisible(false);
        collapsedTextSpan.setVisible(true);
        toggleButton.setIcon(expandIcon);
        withStyle("padding-block", "0");
        return this;
    }

    public LCMCollapsibleFieldset expand() {
        collapsed = false;
        contentLayout.setVisible(true);
        collapsedTextSpan.setVisible(false);
        toggleButton.setIcon(collapseIcon);
        withStyle("padding-block", null);
        return this;
    }

    public LCMCollapsibleFieldset withTitleIcon(VaadinIcon icon, Runnable clickListener) {
        titleIconLayout.setVisible(true);
        titleIconLayout.add(new LCMButton()
                .withCompactIcon(icon)
                .withClickListener(clickListener));
        
        return this;
    }
    
    public LCMCollapsibleFieldset withCollapsedText(String collapsedText) {
        collapsedTextSpan.setText(collapsedText);
        return this;
    }

    // Override HasComponent methods to redirect to contentLayout

    @Override
    public void add(Collection<Component> components) {
        contentLayout.add(components);
    }

    @Override
    public void add(Component... components) {
        contentLayout.add(components);
    }

    @Override
    public void add(String text) {
        contentLayout.add(text);
    }

    @Override
    public void remove(Component... components) {
        contentLayout.remove(components);
    }

    @Override
    public void remove(Collection<Component> components) {
        contentLayout.remove(components);
    }

    @Override
    public void removeAll() {
        contentLayout.removeAll();
    }

    @Override
    public void addComponentAtIndex(int index, Component component) {
        contentLayout.addComponentAtIndex(index, component);
    }

    @Override
    public void addComponentAsFirst(Component component) {
        contentLayout.addComponentAsFirst(component);
    }
}
