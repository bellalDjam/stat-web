package com.minagri.stats.layout.boundary;

import com.minagri.stats.vaadin.layout.LCMSideNav;
import com.minagri.stats.vaadin.layout.LCMSideNavItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.ZoneView;
import java.util.Locale;


@Slf4j
@CssImport(value = "./styles/custom-styles.css")
@Getter
public class ViewerMainLayout extends com.minagri.stats.vaadin.layout.LCMAppLayout implements LocaleChangeObserver {

    private LCMSideNavItem manualSideNavItem;

    @Override
    @PostConstruct
    public void postConstruct() {
        super.postConstruct();


        LCMSideNav statViewerSideNav = addSideNav().withLabel("Zone Viewer");
        statViewerSideNav.addItem().withLabel("Zone").withPath(ZoneView.class);

    }

    private String getManuelLink() {
        return LCMI18NProvider.getLocale().equals(LCMI18NProvider.LOCALE_FR) ? MANUAL_FR : MANUAL_NL;
    }

    @Override
    public void afterFirstNavigation() {
        super.afterFirstNavigation();

    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        super.afterNavigation(afterNavigationEvent);
        setApplicationTitle(Translator.translateKey(TITLE));
        getHeader().getSettingsLayout().getChildren().forEach(child -> {
            if (child instanceof LCMButtonGroup<?>) {
                ((LCMButtonGroup<Locale>) child).setValue(LCMI18NProvider.getLocale());
            }
        });
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent) {
        if (manualSideNavItem != null) {
            manualSideNavItem.getElement().setAttribute("path", getManuelLink());
        }
    }
}
