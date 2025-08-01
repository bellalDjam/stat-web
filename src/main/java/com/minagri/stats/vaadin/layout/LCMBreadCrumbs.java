package com.minagri.stats.vaadin.layout;

import com.minagri.stats.vaadin.component.html.LCMLabel;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.component.router.LCMRouterLink;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteParameters;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class LCMBreadCrumbs extends LCMHorizontalLayout {

    private List<LCMRouterLink> breadCrumbLinks = new ArrayList<>();

    public LCMBreadCrumbs() {
        withNoPadding();
        withNoSpacing();
    }

    public void push(String screenName, Class<? extends Component> routeClass, Map<String, String> routeParameters, Map<String, List<String>> queryParameters) {
        List<LCMRouterLink> toRemove = new ArrayList<>();
        for (LCMRouterLink breadCrumbLink : breadCrumbLinks) {
            if (!toRemove.isEmpty() || breadCrumbLink.getNavigationTarget().equals(routeClass)) {
                toRemove.add(breadCrumbLink);
            }
        }
        breadCrumbLinks.removeAll(toRemove);

        breadCrumbLinks.forEach(LCMRouterLink::withEnabled);

        LCMRouterLink newBreadCrumbLink = new LCMRouterLink()
                .withText(screenName)
                .withColorWhite()
                .withRoute(routeClass, new RouteParameters(routeParameters))
                .withQueryParameters(new QueryParameters(queryParameters))
                .withDisabled();

        breadCrumbLinks.add(newBreadCrumbLink);

        if (breadCrumbLinks.size() > 5) {
            breadCrumbLinks.removeFirst();
        }

        createComponents();
    }

    public void rebase() {
        if (breadCrumbLinks.isEmpty()) {
            return;
        }

        breadCrumbLinks.retainAll(List.of(breadCrumbLinks.getLast()));
        createComponents();
    }

    public void updateCurrentScreenName(String screenName) {
        if (breadCrumbLinks.isEmpty()) {
            return;
        }

        LCMRouterLink breadCrumbLink = breadCrumbLinks.getLast();
        breadCrumbLink.setText(screenName);
    }

    public void updateCurrentQueryParameters(Map<String, List<String>> queryParameters) {
        if (breadCrumbLinks.isEmpty()) {
            return;
        }

        LCMRouterLink breadCrumbLink = breadCrumbLinks.getLast();
        breadCrumbLink.setQueryParameters(new QueryParameters(queryParameters));
    }


    void createComponents() {
        removeAll();
        for (int i = 0; i < breadCrumbLinks.size(); i++) {
            add(breadCrumbLinks.get(i));
            if (i < breadCrumbLinks.size() - 1) {
                add(new LCMLabel().withText(">").withColorWhite().setMarginLeftRight());
            }
        }
    }
}
