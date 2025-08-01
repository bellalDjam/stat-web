package com.minagri.stats.vaadin.route;

import com.minagri.stats.vaadin.layout.LCMAppLayout;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Setter
@SuppressWarnings("rawtypes")
public abstract class RouteController<VIEW extends RouteView> {
    protected VIEW view;

    public abstract void afterNavigation();

    public void setScreenName(String screenName) {
        LCMAppLayout.getCurrent().setScreenName(screenName);
    }

    public Map<String, String> getRouteParameters() {
        return LCMAppLayout.getCurrent().getRouteParameters();
    }

    public String getRouteParameter(String name) {
        return LCMAppLayout.getCurrent().getRouteParameter(name);
    }

    public Long getLongRouteParameter(String name) {
        return LCMAppLayout.getCurrent().getLongRouteParameter(name);
    }

    public Integer getIntegerRouteParameter(String name) {
        return LCMAppLayout.getCurrent().getIntegerRouteParameter(name);
    }

    public Map<String, List<String>> getQueryParameters() {
        return LCMAppLayout.getCurrent().getQueryParameters();
    }

    public Optional<String> getOptionalQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getOptionalQueryParameter(name);
    }

    public String getQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getQueryParameter(name);
    }

    public Optional<Long> getOptionalLongQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getOptionalLongQueryParameter(name);
    }

    public Long getLongQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getLongQueryParameter(name);
    }

    public Optional<Integer> getOptionalIntegerQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getOptionalIntegerQueryParameter(name);
    }

    public Integer getIntegerQueryParameter(String name) {
        return LCMAppLayout.getCurrent().getIntegerQueryParameter(name);
    }

    public <T extends Enum<?>> Optional<T> getOptionalEnumQueryParameter(String name, Class<T> enumClass) {
        return LCMAppLayout.getCurrent().getOptionalEnumQueryParameter(name, enumClass);
    }

    public <T extends Enum<?>> T getEnumQueryParameter(String name, Class<T> enumClass) {
        return LCMAppLayout.getCurrent().getEnumQueryParameter(name, enumClass);
    }

    public void replaceQueryParameter(String name, List<String> values) {
        LCMAppLayout.getCurrent().replaceQueryParameter(name, values);
    }

    public void replaceQueryParameter(String name, String value) {
        LCMAppLayout.getCurrent().replaceQueryParameter(name, value);
    }

    public void replaceLongQueryParameter(String name, Long value) {
        LCMAppLayout.getCurrent().replaceLongQueryParameter(name, value);
    }

    public void replaceIntegerQueryParameter(String name, Integer value) {
        LCMAppLayout.getCurrent().replaceIntegerQueryParameter(name, value);
    }

    public <T extends Enum<?>> void replaceEnumQueryParameter(String name, T value) {
        LCMAppLayout.getCurrent().replaceEnumQueryParameter(name, value);
    }

    public boolean isRouteParameterChanged(String... parameterNames) {
        return LCMAppLayout.getCurrent().isRouteParameterChanged(parameterNames);
    }

    public boolean isQueryParameterChanged(String... parameterNames) {
        return LCMAppLayout.getCurrent().isQueryParameterChanged(parameterNames);
    }
    
    public void rebaseBreadCrumbs() {
        LCMAppLayout.getCurrent().rebaseBreadCrumbs();
    }
}
