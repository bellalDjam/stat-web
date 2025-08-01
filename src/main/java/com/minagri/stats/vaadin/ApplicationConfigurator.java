package com.minagri.stats.vaadin;

import com.minagri.stats.vaadin.error.LCMErrorHandler;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.LoadingIndicatorConfiguration;
import com.vaadin.flow.server.*;
import com.vaadin.flow.theme.Theme;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@Theme("common-theme")
@Unremovable
@ApplicationScoped
public class ApplicationConfigurator implements AppShellConfigurator {


    @ConfigProperty(name = "application.style")
    ApplicationStyle applicationStyle;

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", applicationStyle.getFaviconLocation(), "16x16");
    }

    public void serviceInit(@Observes ServiceInitEvent serviceInitEvent) {
        log.info("serviceInit - vaadin running in mode Production: {}", serviceInitEvent.getSource().getDeploymentConfiguration().isProductionMode());

        serviceInitEvent.getSource().addSessionInitListener((SessionInitEvent event) -> {
            log.debug("SESSION INIT: VaadinSession# {} - HttpSession#: {}", event.getSession(), event.getSession().getSession().getId());
            event.getSession().setErrorHandler(new LCMErrorHandler());
        });

        serviceInitEvent.getSource().addSessionDestroyListener((SessionDestroyEvent event) -> {
            log.debug("SESSION DESTROY: VaadinSession# {} - HttpSession#: {}", event.getSession(), event.getSession().getSession().getId());
        });

        if (log.isDebugEnabled()) {
            serviceInitEvent.getSource().addUIInitListener((UIInitEvent event) -> {
                log.debug("UI INIT. UI id # {} - page: {}", event.getUI().getUIId(), event.getUI().getPage());
            });
        }

        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            LoadingIndicatorConfiguration loadingIndicatorConfiguration = uiInitEvent.getUI().getLoadingIndicatorConfiguration();
            loadingIndicatorConfiguration.setApplyDefaultTheme(false);
        });
    }
}