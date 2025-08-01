package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.java.Objects;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasTranslation;
import com.minagri.stats.vaadin.fluent.IsColumnFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.Renderer;

import java.util.HashMap;
import java.util.Map;

public class LCMEditColumn<VALUECLASS, C extends Component & HasValue<?, CV>, CV> extends Grid.Column<VALUECLASS> implements
        ComponentFluent<LCMEditColumn<VALUECLASS, C, CV>>,
        IsColumnFluent<LCMEditColumn<VALUECLASS, C, CV>, VALUECLASS>,
        HasTranslation {
    private Map<VALUECLASS, C> editComponents = new HashMap<>();
    private Map<VALUECLASS, CV> initialValues = new HashMap<>();
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    public LCMEditColumn(Grid<VALUECLASS> grid, String columnId, Renderer<VALUECLASS> renderer) {
        super(grid, columnId, renderer);
    }

    @Override
    public LCMEditColumn<VALUECLASS, C, CV> getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }


    public C getEditComponent(VALUECLASS item) {
        return editComponents.get(item);
    }

    public void setEditComponent(VALUECLASS item, C editComponent) {
        editComponents.put(item, editComponent);
        initialValues.put(item, editComponent.getValue());
    }

    public boolean hasChanges(VALUECLASS item) {
        C editComponent = getEditComponent(item);
        return Objects.notEqualTo(editComponent.getValue(), initialValues.get(item));
    }

    public void clear() {
        editComponents.clear();
        initialValues.clear();
    }
}