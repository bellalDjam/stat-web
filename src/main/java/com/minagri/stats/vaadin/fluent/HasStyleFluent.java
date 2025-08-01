package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.vaadin.css.LCMCss;
import com.vaadin.flow.component.HasStyle;

public interface HasStyleFluent<FLUENT extends HasStyle> {


    default FLUENT withClassName(String className) {
        getFluent().addClassName(className);
        return getFluent();
    }

    default FLUENT withPaperClass() {
        return withClassName(LCMCss.PAPER);
    }

    default FLUENT withClassNames(String... classNames) {
        getFluent().addClassNames(classNames);
        return getFluent();
    }

    default FLUENT withSingleClassName(String className) {
        getFluent().setClassName(className);
        return getFluent();
    }

    default FLUENT withRemoveClassName(String className) {
        getFluent().removeClassName(className);
        return getFluent();
    }

    default FLUENT withRemoveClassNames(String... classNames) {
        getFluent().removeClassNames(classNames);
        return getFluent();
    }

    default FLUENT withStyle(String name, String value) {
        getFluent().getStyle().set(name, value);
        return getFluent();
    }

    default FLUENT setMarginZero() {
        return setMargin("0");
    }

    default FLUENT setMargin(String margin) {
        getFluent().getStyle().set("margin", margin);
        return getFluent();
    }

    default FLUENT setMarginLeft(String marginLeft) {
        getFluent().getStyle().set("margin-left", marginLeft);
        return getFluent();
    }

    default FLUENT setMarginRight(String marginRight) {
        getFluent().getStyle().set("margin-right", marginRight);
        return getFluent();
    }

    default FLUENT setMarginRight() {
        return setMarginRight("1em");
    }

    default FLUENT setMarginLeftRight(String marginLeftRight) {
        setMarginLeft(marginLeftRight);
        setMarginRight(marginLeftRight);
        return getFluent();
    }

    default FLUENT setMarginLeftRight() {
        return setMarginLeftRight("1em");
    }
    
    default FLUENT setMarginTop() {
        return setMarginTop("1em");
    }

    default FLUENT setMarginTop(String margin) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("margin-top", margin);
        return fluent;
    }

    default FLUENT setMarginBottom(String margin) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("margin-bottom", margin);
        return fluent;
    }

    default FLUENT setMarginBottom() {
        return setMarginBottom("1em");
    }

    default FLUENT setPaddingTopZero() {
        return setPaddingTop("0");
    }
    
    default FLUENT setPaddingTop(String paddingTop) {
        getFluent().getStyle().set("padding-top", paddingTop);
        return getFluent();
    }

    default FLUENT setPaddingLeft(String paddingLeft) {
        getFluent().getStyle().set("padding-left", paddingLeft);
        return getFluent();
    }

    default FLUENT setPadding(String padding) {
        getFluent().getStyle().set("padding", padding);
        return getFluent();
    }

    default FLUENT setPaddingInPixels(Integer pixels) {
        return setPadding(pixels + "px");
    }

    default FLUENT setOverflowHidden() {
        return setOverflow("hidden");
    }

    default FLUENT setOverflow(String overflow) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("overflow", overflow);
        return fluent;
    }

    default FLUENT setBackgroundTransparent() {
        return setBackground("transparent");
    }

    default FLUENT setBackground(String background) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("background", background);
        return fluent;
    }

    default FLUENT setAlignItems(String alignItems) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("align-items", alignItems);
        return fluent;
    }

    default FLUENT setAlignItemsCenter() {
        return setAlignItems("center");
    }
    
    default FLUENT setJustifyContent(String justifyContent) {
        FLUENT fluent = getFluent();
        fluent.getStyle().set("justify-content", justifyContent);
        return fluent;
    }
    
    default FLUENT setJustifyContentCenter() {
        return setJustifyContent("center");
    }

    default FLUENT withJustifySelf(String justifySelf) {
        return withStyle("justify-self", justifySelf);
    }
    
    default FLUENT withJustifySelfCenter() {
        return withJustifySelf("center");
    }
    
    default FLUENT withJustifySelfEnd() {
        return withJustifySelf("end");
    }
    
    default FLUENT withJustifySelfStart() {
        return withJustifySelf("start");
    }
    
    default FLUENT withAlignSelf(String alignSelf) {
        return withStyle("align-self", alignSelf);
    }
    
    default FLUENT withAlignSelfCenter() {
        return withAlignSelf("center");
    }
    
    default FLUENT withAlignSelfEnd() {
        return withAlignSelf("end");
    }
    
    default FLUENT withAlignSelfStart() {
        return withAlignSelf("start");
    }
    

    default FLUENT withColor(String color) {
        return withStyle("color", color);
    }

    default FLUENT withColorRed() {
        return withColor("red");
    }
    
    default FLUENT withColorGreen() {
        return withColor("green");
    }

    default FLUENT withColorWhite() {
        return withColor("white");
    }

    default FLUENT withColorOrange() {
        return withColor("orange");
    }

    default FLUENT withColorLumoTertiary() {
        return withColor("var(--lumo-tertiary-text-color)");
    }
    
    default FLUENT withFontWeightBold() {
        return withStyle("font-weight", "bold");
    }

    default FLUENT withFontSize(String fontSize) {
        return withStyle("font-size", fontSize);
    }
    
    default FLUENT withFontSizeInEm(double em) {
        return withFontSize(em + "em");
    }

    default FLUENT withOpacity(String opacity) {
        return withStyle("opacity", opacity);
    }
    
    default FLUENT withOpacity(Number opacity) {
        return withOpacity(String.valueOf(opacity));
    }

    FLUENT getFluent();
}
