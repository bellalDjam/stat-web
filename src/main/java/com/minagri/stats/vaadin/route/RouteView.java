package com.minagri.stats.vaadin.route;

import com.minagri.stats.core.java.Classes;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import io.quarkus.arc.All;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import lombok.Getter;

import java.util.List;

@Getter
@SuppressWarnings("rawtypes")
public abstract class RouteView<CONTROLLER extends RouteController> extends LCMVerticalLayout implements HasDynamicTitle, AfterNavigationObserver {
    @Inject
    @All
    List<RouteController<?>> controllers;

    protected CONTROLLER controller;

    public abstract void buildView();

    @SuppressWarnings("unchecked")
    public String getScreenName() {
        Class<? extends Component> routeClass = (Class<? extends Component>) Classes.getAnnotatedClass(this.getClass(), Route.class);
        return routeClass.getSimpleName().replace("View", "");
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void postConstruct() {
        Class<?> controllerClass = Classes.getGenericTypeParameter(this.getClass(), RouteView.class);
        controller = (CONTROLLER) CDI.current().select(controllerClass).get();
        controller.setView(this);

        setSizeFull();
        setPaddingTopZero();

        buildView();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        controller.afterNavigation();
    }

    @Override
    public String getPageTitle() {
        return getScreenName();
    }
}
