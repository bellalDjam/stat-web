package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.html.Div;

import java.util.HashMap;
import java.util.Map;

public class LCMGridLayout extends Div implements
        ComponentFluent<LCMGridLayout>,
        HasSizeFluent<LCMGridLayout>,
        HasEnabledFluent<LCMGridLayout>,
        HasStyleFluent<LCMGridLayout>,
        HtmlComponentFluent<LCMGridLayout>,
        HasComponentsFluent<LCMGridLayout>,
        ClickNotifierFluent<LCMGridLayout> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public LCMGridLayout getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    public LCMGridLayout() {
        this.withStyle("display", "grid");
    }

    public LCMGridLayout setRows(int number) {
        this.withStyle("grid-template-rows", String.format("repeat(%d , auto", number));
        return this;
    }

    public LCMGridLayout setColumns(int number) {
        this.withStyle("grid-template-columns", String.format("repeat(%d, auto)", number));
        return this;
    }

    public LCMGridLayout setRowGap() {
        return setRowGap("1em");
    }

    public LCMGridLayout setRowGap(String rowGap) {
        if (Strings.isNotBlank(rowGap)) {
            this.withStyle("grid-row-gap", rowGap);
        } else {
            this.withStyle("grid-row-gap", "0");
        }
        return this;
    }

    public LCMGridLayout setColumnGap() {
        return setColumnGap("1em");
    }

    public LCMGridLayout setColumnGap(String columnGap) {
        if (Strings.isNotBlank(columnGap)) {
            this.withStyle("grid-column-gap", columnGap);
        } else {
            this.withStyle("grid-column-gap", "0");
        }
        return this;
    }
}