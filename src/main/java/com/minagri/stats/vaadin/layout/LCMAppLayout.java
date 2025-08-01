package com.minagri.stats.vaadin.layout;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.*;
import com.minagri.stats.core.java.Collections;
import com.minagri.stats.vaadin.ApplicationStyle;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.route.RouteView;
import com.minagri.stats.vaadin.user.entity.User;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.*;
import io.micrometer.core.instrument.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@CssImport(value = "./styles/vaadin-flow-shared-styles.css")
@CssImport(value = "./styles/lcm-app-layout.css")
@CssImport(value = "./styles/lcm-app-layout.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/lcm-custom-field.css", themeFor = "vaadin-custom-field")
@CssImport(value = "./styles/lcm-icon.css", themeFor = "vaadin-icon")
@CssImport(value = "./styles/lcm-form-item.css", themeFor = "vaadin-form-item")
@CssImport(value = "./styles/lcm-grid.css", themeFor = "vaadin-grid")
@CssImport(value = "./styles/lcm-dialog-overlay.css", themeFor = "vaadin-dialog-overlay")
@CssImport(value = "./styles/lcm-side-nav-item.css", themeFor = "vaadin-side-nav-item")
@CssImport(value = "./styles/lcm-tooltip-overlay.css", themeFor = "vaadin-tooltip-overlay")
@Getter
public class LCMAppLayout extends AppLayout implements AfterNavigationListener {

    @Inject
    protected User user;

    @ConfigProperty(name = "environment")
    protected String environment;

    @ConfigProperty(name = "quarkus.application.version")
    protected String appVersion;

    @ConfigProperty(name = "application.style")
    ApplicationStyle applicationStyle;

    private LCMHeader header;
    private LCMFooter footer;
    private Main content;

    private List<LCMSideNav> sideNavs = new ArrayList<>();

    private RouteView<?> routeView;
    private Class<? extends Component> routeClass;
    private Map<String, String> routeParameters;
    private Map<String, List<String>> queryParameters;
    private Map<Class<?>, Map<String, String>> routeToPreviousRouteParameters = new HashMap<>();
    private Map<Class<?>, Map<String, List<String>>> routeToPreviousQueryParameters = new HashMap<>();
    private boolean firstNavigation = true;

    private static final AtomicInteger tabCount = new AtomicInteger(0);

    public LCMAppLayout() {
        UI.getCurrent().addAfterNavigationListener(this);

        header = new LCMHeader();
        addToNavbar(header);
        
        content = new Main();
        content.setId("app-content");
        content.setWidthFull();
        content.setHeightFull();
        content.getStyle().set("overflow", "auto");

        footer = new LCMFooter();

        LCMVerticalLayout contentAndFooterWrapper = new LCMVerticalLayout().withNoPadding()
                .withStyle("margin-bottom", "0")
                .withStyle("height", "100%")
                .withAlignItems(FlexComponent.Alignment.STRETCH)
                .withAdd(content)
                .withFlexGrow(1.0, content)
                .withAdd(footer);
        setContent(contentAndFooterWrapper);
    }

    @PostConstruct
    public void postConstruct() {
        setEnvironment(environment);
        setVersion(appVersion);
        setUserName(user.getName());

        Iterable<Tag> baseTags = Arrays.asList(Tag.of("method", "tabCounter"), Tag.of("class", this.getClass().getName()));
    }

    public static LCMAppLayout getCurrent() {
        return (LCMAppLayout) UI.getCurrent()
                .getChildren()
                .filter(component -> LCMAppLayout.class.isAssignableFrom(component.getClass()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            content.getElement().getComponent().ifPresent(this.content::add);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        HasElement navigationComponent = afterNavigationEvent.getActiveChain().getFirst();
        if (!(navigationComponent instanceof RouteView)) {
            return;
        }

        if (routeView != null) {
            routeToPreviousRouteParameters.put(routeClass, routeParameters);
            routeToPreviousQueryParameters.put(routeClass, queryParameters);
        }

        routeView = (RouteView<?>) navigationComponent;
        routeClass = (Class<? extends Component>) Classes.getAnnotatedClass(routeView.getClass(), Route.class);

        routeParameters = new HashMap<>();
        afterNavigationEvent.getRouteParameters().getParameterNames().forEach(parameterName ->
                routeParameters.put(parameterName, afterNavigationEvent.getRouteParameters().get(parameterName).orElseThrow()));

        queryParameters = new HashMap<>(afterNavigationEvent.getLocationChangeEvent().getQueryParameters());

        String screenName = routeView.getScreenName();
        getFooter().getScreenName().withText(screenName);
        header.getBreadCrumbs().push(screenName, routeClass, routeParameters, queryParameters);

        if (Collections.notEmptyAndAnyMatch(sideNavs, sideNav -> sideNav.containsRouteView(routeClass))) {
            header.getBreadCrumbs().rebase();
        }

        if (firstNavigation) {
            afterFirstNavigation();
            firstNavigation = false;
        }
    }

    public void afterFirstNavigation() {
        if (applicationStyle == ApplicationStyle.OPENMUT) {
            header.getAppLogo().setSrc("frontend/images/logo-openmut.svg");
            header.getAppLogo().setWidth(90, Unit.PIXELS);
            header.getAppLogo().getStyle().setMarginTop("4px");
            header.getAppLogo().getStyle().setMarginRight("25px");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        tabCount.incrementAndGet();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        tabCount.decrementAndGet();
    }

    public void setUserName(String userName) {
        getHeader().getUserName().setText(userName);
    }

    public void setEnvironment(String environment) {
        String environmentClassName = "env-" + environment.toLowerCase().replaceAll("\\s+", "-");

        getFooter().getEnvironment().withText(environment).withClassName(environmentClassName);

        getHeader().addClassName(environmentClassName);
    }

    public void setVersion(String version) {
        getFooter().getVersion().withText(version);
    }

    public void setScreenName(String screenName) {
        footer.getScreenName().withText(screenName);
        header.getBreadCrumbs().updateCurrentScreenName(screenName);
    }

    public void setApplicationTitle(String value) {
        header.getAppTitle().setText(value);
    }

    public void setApplicationPicture(String pictureName, String alternativePicture) {
        header.getAppLogo().setSrc(pictureName);
        header.getAppLogo().setAlt(alternativePicture);
    }

    public LCMSideNav addSideNav() {
        LCMSideNav sideNav = new LCMSideNav();
        sideNavs.add(sideNav);
        addToDrawer(sideNav);
        return sideNav;
    }

    public String getRouteParameter(String name) {
        return routeParameters.get(name);
    }

    public Long getLongRouteParameter(String name) {
        return Strings.toLong(getRouteParameter(name));
    }

    public Integer getIntegerRouteParameter(String name) {
        return Strings.toInteger(getRouteParameter(name));
    }

    public Optional<String> getOptionalQueryParameter(String name) {
        List<String> queryParameterValues = queryParameters.get(name);
        if (queryParameterValues == null || queryParameterValues.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(queryParameterValues.getFirst());
    }

    public String getQueryParameter(String name) {
        return getOptionalQueryParameter(name).orElseThrow();
    }

    public Optional<Long> getOptionalLongQueryParameter(String name) {
        return getOptionalQueryParameter(name).map(Strings::toLong);
    }

    public Long getLongQueryParameter(String name) {
        return getOptionalLongQueryParameter(name).orElseThrow();
    }

    public Optional<Integer> getOptionalIntegerQueryParameter(String name) {
        return getOptionalQueryParameter(name).map(Strings::toInteger);
    }

    public Integer getIntegerQueryParameter(String name) {
        return getOptionalIntegerQueryParameter(name).orElseThrow();
    }

    public <T extends Enum<?>> Optional<T> getOptionalEnumQueryParameter(String name, Class<T> enumClass) {
        return getOptionalQueryParameter(name).map(parameter -> Enums.getFromName(parameter, enumClass));
    }

    public <T extends Enum<?>> T getEnumQueryParameter(String name, Class<T> enumClass) {
        return getOptionalEnumQueryParameter(name, enumClass).orElseThrow();
    }

    public void replaceQueryParameter(String name, List<String> values) {
        if (values == null || values.isEmpty()) {
            queryParameters.remove(name);
        } else {
            queryParameters.put(name, values);
        }

        String updatedRouteUrl = RouteConfiguration.forSessionScope().getUrl(routeClass, new RouteParameters(routeParameters));
        Location updatedLocation = new Location(updatedRouteUrl, new QueryParameters(queryParameters));
        UI.getCurrent().getPage().getHistory().replaceState(null, updatedLocation);

        header.getBreadCrumbs().updateCurrentQueryParameters(queryParameters);
    }

    public void replaceQueryParameter(String name, String value) {
        if (value == null) {
            replaceQueryParameter(name, emptyList());
        } else {
            replaceQueryParameter(name, singletonList(value));
        }
    }

    public void replaceLongQueryParameter(String name, Long value) {
        replaceQueryParameter(name, Longs.toString(value));
    }

    public void replaceIntegerQueryParameter(String name, Integer value) {
        replaceQueryParameter(name, Integers.toString(value));
    }

    public <T extends Enum<?>> void replaceEnumQueryParameter(String name, T value) {
        replaceQueryParameter(name, Enums.getName(value));
    }

    public boolean isRouteParameterChanged(String... parameterNames) {
        Map<String, String> previousRouteParams = routeToPreviousRouteParameters.get(routeClass);
        if (previousRouteParams == null) {
            return true;
        }
        return Arrays.stream(parameterNames).anyMatch(parameterName -> !Objects.equals(previousRouteParams.get(parameterName), routeParameters.get(parameterName)));
    }

    public boolean isQueryParameterChanged(String... parameterNames) {
        Map<String, List<String>> previousQueryParameters = routeToPreviousQueryParameters.get(routeClass);
        if (previousQueryParameters == null) {
            return true;
        }
        return Arrays.stream(parameterNames).anyMatch(parameterName -> !Objects.equals(previousQueryParameters.get(parameterName), queryParameters.get(parameterName)));
    }

    public void rebaseBreadCrumbs() {
        header.getBreadCrumbs().rebase();
    }
}
