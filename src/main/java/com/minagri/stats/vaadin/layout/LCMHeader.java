package com.minagri.stats.vaadin.layout;

import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.button.LCMButtonGroup;
import com.minagri.stats.vaadin.component.html.LCMDiv;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.component.icon.LcmIcon;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.translation.control.LCMI18NProvider;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import lombok.Getter;

import java.util.Locale;

@Getter
public class LCMHeader extends Header {
    private LCMHorizontalLayout headerLayout;
    private Image appLogo;
    private H1 appTitle;
    private LCMBreadCrumbs breadCrumbs;
    private LCMSpan userName;
    private LCMVerticalLayout settingsLayout;

    public LCMHeader() {
        getStyle().set("align-items", "center");
        
        appTitle = new H1();
        appLogo = new Image();
        breadCrumbs = new LCMBreadCrumbs();
        userName = new LCMSpan();

        // margins needed to center because of Segoe UI font offset
        appTitle.getStyle().setMarginBottom("4px");
        breadCrumbs.getStyle().setMarginBottom("1px");
        userName.getStyle().setMarginBottom("1px");

        LCMDiv spacer = new LCMDiv();
        headerLayout = new LCMHorizontalLayout()
                .withSizeFull()
                .withPadding()
                .withAlignItemsCenter()
                .withJustifyContentModeStart()
                .withAdd(appLogo)
                .withAdd(appTitle)
                .withAdd(new LCMDiv().withWidthInPixels(25))
                .withAdd(breadCrumbs)
                .withAdd(spacer)
                .withFlexGrow(1, spacer)
                .withAdd(userName);

        setId("app-header");
        addComponentAsFirst(new DrawerToggle());
        add(headerLayout);

        LCMButton settingsButton = new LCMButton()
                .withIconTheme()
                .withTertiaryTheme()
                .setMarginZero()
                .withIcon(new LcmIcon(VaadinIcon.COG)
                        .setPaddingInPixels(0)
                        .withColorWhite()
                        .setBackgroundTransparent());

        LCMButtonGroup<Locale> languageButtonGroup = new LCMButtonGroup<Locale>()
                .setLabel(CommonMessage.LANGUAGE)
                .setItems(LCMI18NProvider.LOCALE_NL, LCMI18NProvider.LOCALE_FR)
                .setButtonText(locale -> locale == LCMI18NProvider.LOCALE_NL ? "Nederlands" : "Français")
                .setValue(LCMI18NProvider.getLocale())
                .setValueChangeListener(LCMI18NProvider::setLocale);

        settingsLayout = new LCMVerticalLayout()
                .withAdd(languageButtonGroup);

        Popover settingsPopover = new Popover();
        settingsPopover.setTarget(settingsButton);
        settingsPopover.setPosition(PopoverPosition.BOTTOM_END);
        settingsPopover.add(settingsLayout);

        headerLayout.add(settingsButton, settingsPopover);
    }

    public void addToSettings(Component component) {
        settingsLayout.add(component);
    }
}
