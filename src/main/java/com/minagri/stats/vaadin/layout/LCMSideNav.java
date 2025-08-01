package com.minagri.stats.vaadin.layout;

import com.minagri.stats.core.java.Collections;
import com.minagri.stats.vaadin.fluent.HasLabelFluent;
import com.minagri.stats.vaadin.fluent.HasTranslation;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.sidenav.SideNav;

import java.util.HashMap;
import java.util.Map;

public class LCMSideNav extends SideNav implements
        HasTranslation,
        HasLabel,
        HasLabelFluent<LCMSideNav> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

    @Override
    public LCMSideNav getFluent() {
        return this;
    }

    public LCMSideNavItem addItem() {
        LCMSideNavItem item = new LCMSideNavItem();
        addItem(item);
        return item;
    }
    
    public boolean containsRouteView(Class<?> routeClass) {
        return Collections.notEmptyAndAnyMatch(getItems(), item -> {
            if (item instanceof LCMSideNavItem sideNavItem) {
                return sideNavItem.containsRouteView(routeClass);
            }
            return false;
        });
    }
}
