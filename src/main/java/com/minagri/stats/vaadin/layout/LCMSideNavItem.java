package com.minagri.stats.vaadin.layout;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.fluent.ComponentFluentAuthorized;
import com.minagri.stats.vaadin.fluent.HasLabelFluent;
import com.minagri.stats.vaadin.fluent.HasTranslation;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouteParameters;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LCMSideNavItem extends SideNavItem implements
        ComponentFluentAuthorized<LCMSideNavItem>,
        HasTranslation,
        HasLabel,
        HasLabelFluent<LCMSideNavItem> {

    private Action action;
    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    
    @Getter
    private Class<? extends Component> routeClass;

    public LCMSideNavItem() {
        super("");
    }

    @Override
    public LCMSideNavItem getFluent() {
        return this;
    }

    @Override
    public LCMSideNavItem setAction(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

    @Override
    public void setPath(Class<? extends Component> view, RouteParameters routeParameters) {
        this.routeClass = view;
        super.setPath(view, routeParameters);
    }

    public LCMSideNavItem withPath(Class<? extends Component> view) {
        setPath(view);
        return this;
    }

    public LCMSideNavItem withPath(String path) {
        setPath(path);
        return this;
    }

    public LCMSideNavItem withPath(Translation pathTranslation) {
        this.addTranslationLinkedToMethod("setPath", pathTranslation);
        setPath(Translator.translate(pathTranslation));
        return this.getFluent();
    }

    public LCMSideNavItem addItem() {
        LCMSideNavItem item = new LCMSideNavItem();
        addItem(item);
        return item;
    }
    
    public LCMSideNavItem withOpenInNewBrowserTab() {
        setOpenInNewBrowserTab(true);
        return this;
    }

    public boolean containsRouteView(Class<?> routeClass) {
        if (this.routeClass != null && this.routeClass.isAssignableFrom(routeClass)) {
            return true;
        }
        
        return Collections.notEmptyAndAnyMatch(getItems(), item -> {
            if (item instanceof LCMSideNavItem) {
                return ((LCMSideNavItem) item).containsRouteView(routeClass);
            }
            return false;
        });
    }

}
