package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasTranslation;
import com.minagri.stats.vaadin.fluent.IsColumnFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.i18n.LocaleChangeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LCMColumn<VALUECLASS> extends Grid.Column<VALUECLASS> implements
        ComponentFluent<LCMColumn<VALUECLASS>>,
        IsColumnFluent<LCMColumn<VALUECLASS>, VALUECLASS>,
        HasTranslation {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private SerializableFunction<VALUECLASS, String> tooltipGenerator; 

    public LCMColumn(Grid<VALUECLASS> grid, String columnId, Renderer<VALUECLASS> renderer) {
        super(grid, columnId, renderer);
        setAutoWidth(true);
    }

    @Override
    public LCMColumn<VALUECLASS> getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    @SuppressWarnings("unchecked")
    public LCMGrid<VALUECLASS> getLcmGrid() {
        return (LCMGrid<VALUECLASS>) getGrid();
    }

    @Override
    public Grid.Column<VALUECLASS> setWidth(String width) {
        setAutoWidth(false);
        setFlexGrow(0);
        return super.setWidth(width);
    }

    @Override
    public Grid.Column<VALUECLASS> setTooltipGenerator(SerializableFunction<VALUECLASS, String> tooltipGenerator) {
        this.tooltipGenerator = tooltipGenerator;
        return super.setTooltipGenerator(tooltipGenerator);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        IsColumnFluent.super.localeChange(event);
        Optional.ofNullable(tooltipGenerator).ifPresent(this::setTooltipGenerator);
    }
}
