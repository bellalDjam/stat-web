package com.minagri.stats.vaadin.component.router;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.Integers;
import com.minagri.stats.core.java.Longs;
import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasEnabledFluent;
import com.minagri.stats.vaadin.fluent.HasStyleFluent;
import com.minagri.stats.vaadin.fluent.HasTextFluent;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LCMRouterLink extends RouterLink implements
        LocaleChangeObserver,
        HasTextFluent<LCMRouterLink>,
        ComponentFluent<LCMRouterLink>,
        HasStyleFluent<LCMRouterLink>,
        HasEnabledFluent<LCMRouterLink> {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private Map<String, String> queryParameters = new HashMap<>();

    @Getter
    private Class<? extends Component> navigationTarget;

    @Override
    public LCMRouterLink getFluent() {
        return this;
    }

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return this.translationsLinkedToMethodsMap;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            withStyle("pointer-events", null);
            withStyle("cursor", null);
            withStyle("text-decoration", null);
            withStyle("user-select", null);
        } else {
            withStyle("pointer-events", "none");
            withStyle("cursor", "default");
            withStyle("text-decoration", "none");
            withStyle("user-select", "none");
        }
    }


    @Override
    public void setRoute(Router router, Class<? extends Component> navigationTarget, RouteParameters parameters) {
        super.setRoute(router, navigationTarget, parameters);
        this.navigationTarget = navigationTarget;
    }

    public LCMRouterLink withRoute(Class<? extends Component> route) {
        getFluent().setRoute(route);
        return getFluent();
    }

    public <T, C extends Component & HasUrlParameter<T>> LCMRouterLink withRoute(Class<? extends C> route, T parameter) {
        getFluent().setRoute(route, parameter);
        return getFluent();
    }

    public LCMRouterLink withRoute(Class<? extends Component> route, RouteParameters parameters) {
        getFluent().setRoute(route, parameters);
        return getFluent();
    }

    public LCMRouterLink withQueryParameters(QueryParameters queryParameters) {
        getFluent().setQueryParameters(queryParameters);
        return getFluent();
    }

    public LCMRouterLink withQueryParameter(String key, String value) {
        if (value != null) {
            queryParameters.put(key, value);
            getFluent().setQueryParameters(QueryParameters.simple(queryParameters));
        }
        return getFluent();
    }

    public LCMRouterLink withQueryParameter(String key, Long value) {
        return withQueryParameter(key, Longs.toString(value));
    }

    public LCMRouterLink withQueryParameter(String key, Integer value) {
        return withQueryParameter(key, Integers.toString(value));
    }

    public LCMRouterLink withQueryParameter(String key, Enum<?> value) {
        return withQueryParameter(key, Enums.getName(value));
    }
}
