/*
 * Copyright 2003-2009 LCM-ANMC, Inc. All rights reserved.
 * This source code is the property of LCM-ANMC, Direction
 * Informatique and cannot be copied or distributed without
 * the formal permission of LCM-ANMC.
 */
package com.minagri.stats.vaadin.component.html;

import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.minagri.stats.vaadin.translation.control.LCMI18NProvider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import java.util.Locale;

/**
 * @author 7515234 Kevin Van Raepenbusch
 */
public class LCMLogo extends Image implements LocaleChangeObserver, HasSizeFluent<LCMLogo> {

    public LCMLogo() {
        super();
        setAlt("Logo");
        setSource(UI.getCurrent().getLocale());
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent) {
        setSource(localeChangeEvent.getLocale());
    }

    @Override
    public LCMLogo getFluent() {
        return this;
    }

    private void setSource(Locale locale) {
        if (LCMI18NProvider.LOCALE_NL.equals(locale)) {
            this.setSrc("frontend/images/logo-nl.png");
        } else {
            this.setSrc("frontend/images/logo-fr.png");
        }
    }
}