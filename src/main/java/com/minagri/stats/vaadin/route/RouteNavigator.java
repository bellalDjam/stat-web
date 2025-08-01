package com.minagri.stats.vaadin.route;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.Collections;
import com.minagri.stats.core.java.Integers;
import com.minagri.stats.core.java.Longs;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteNavigator {
    Class<? extends RouteView<?>> route;
    Map<String, String> routeParameters = new HashMap<>();
    Map<String, List<String>> queryParameters = new HashMap<>();
    
    public static RouteNavigator to(Class<? extends RouteView<?>> route) {
        return new RouteNavigator(route);
    }

    public RouteNavigator(Class<? extends RouteView<?>> route) {
        this.route = route;
    }

    public void navigate() {
        if (routeParameters.isEmpty()) {
            UI.getCurrent().navigate((Class<? extends Component>) route, new QueryParameters(queryParameters));
        } else {
            UI.getCurrent().navigate((Class<? extends Component>) route, new RouteParameters(routeParameters), new QueryParameters(queryParameters));   
        }
    }

    public RouteNavigator setRouteParameter(String key, String value) {
        routeParameters.put(key, value);
        return this;
    }

    public RouteNavigator setIntegerRouteParameter(String key, Integer value) {
        routeParameters.put(key, value.toString());
        return this;
    }

    public RouteNavigator setLongRouteParameter(String key, Long value) {
        routeParameters.put(key, value.toString());
        return this;
    }

    public RouteNavigator setEnumRouteParameter(String key, Enum<?> value) {
        routeParameters.put(key, value.name());
        return this;
    }

    public RouteNavigator setQueryParameter(String key, List<String> values) {
        queryParameters.put(key, values);
        return this;
    }

    public RouteNavigator setQueryParameter(String key, String value) {
        queryParameters.put(key, List.of(value));
        return this;
    }

    public RouteNavigator setIntegerQueryParameter(String key, List<Integer> values) {
        queryParameters.put(key, Collections.map(values, Integers::toString));
        return this;
    }

    public RouteNavigator setIntegerQueryParameter(String key, Integer value) {
        queryParameters.put(key, List.of(value.toString()));
        return this;
    }

    public RouteNavigator setLongQueryParameter(String key, List<Long> values) {
        queryParameters.put(key, Collections.map(values, Longs::toString));
        return this;
    }

    public RouteNavigator setLongQueryParameter(String key, Long value) {
        queryParameters.put(key, List.of(value.toString()));
        return this;
    }

    public RouteNavigator setEnumQueryParameter(String key, List<? extends Enum<?>> values) {
        queryParameters.put(key, Collections.map(values, Enums::getName));
        return this;
    }

    public RouteNavigator setEnumQueryParameter(String key, Enum<?> value) {
        queryParameters.put(key, List.of(value.name()));
        return this;
    }
}