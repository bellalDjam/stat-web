package com.minagri.stats.vaadin.translation.control;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.quarkus.annotation.VaadinServiceEnabled;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;
import io.quarkus.arc.Unremovable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

import static java.util.ResourceBundle.getBundle;

/**
 * Two locales are provided, NL_BE and FR_BE, the resource bundles are preloaded.
 *
 * @author 7515234 Kevin Van Raepenbusch
 */
@VaadinServiceScoped
@VaadinServiceEnabled
@Unremovable
public class LCMI18NProvider implements I18NProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(LCMI18NProvider.class);

    public static final String DEFAULT_RESOURCE_BUNDLE = "common-messages";
    public static final Locale LOCALE_NL = Locale.of("nl", "BE");
    public static final Locale LOCALE_FR = Locale.of("fr", "BE");

    private static final ResourceBundle DEFAULT_RESOURCE_BUNDLE_NL = getBundle(DEFAULT_RESOURCE_BUNDLE, LOCALE_NL);
    private static final ResourceBundle DEFAULT_RESOURCE_BUNDLE_FR = getBundle(DEFAULT_RESOURCE_BUNDLE, LOCALE_FR);

    private static final String BUNDLE_PREFIX = "messages";

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(LOCALE_NL, LOCALE_FR);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        LOGGER.trace("getTranslation() for {} in {}.", key, locale);

        if (key == null) {
            LOGGER.trace("Got translation request for key with null value.");
            return "";
        }

        Locale knownLocale = tryLookupLocale(locale);
        ResourceBundle resourceBundle = getBundle(BUNDLE_PREFIX, knownLocale, Thread.currentThread().getContextClassLoader());

        String value = tryLookupInResourceBundle(resourceBundle, key)
                .or(() -> tryLookupInResourceBundle(getDefaultResourceBundle(knownLocale), key))
                .orElse(key);

        return tryFormat(value, params);
    }

    public static Locale getLocale() {
        UI ui = UI.getCurrent();
        if (ui != null && ui.getLocale() == LCMI18NProvider.LOCALE_FR) {
            return LOCALE_FR;
        }
        return LOCALE_NL;
    }

    public static void setLocale(Locale locale) {
        if (!locale.equals(VaadinSession.getCurrent().getLocale())) {
            VaadinSession.getCurrent().setLocale(locale);
        }
    }

    public static String getLanguageParam() {
        return getLocale().getLanguage();
    }

    public String getTranslation(String bundle, String key, Locale locale, Object... params) {
        LOGGER.debug("getTranslation() in {} for {} in {}.", bundle, key, locale);

        if (bundle == null) {
            LOGGER.debug("Got translation request for class with null value.");
            return "";
        }

        if (key == null) {
            LOGGER.debug("Got translation request for key with null value.");
            return "";
        }

        Locale searchLocale = tryLookupLocale(locale);

        ResourceBundle resourceBundle;
        try {
            resourceBundle = getBundle(bundle, searchLocale, Thread.currentThread().getContextClassLoader());
        } catch (MissingResourceException e) {
            LOGGER.debug("Could not load resource for bundle {} with locale {}.", bundle, searchLocale);
            return key;
        }

        String value = tryLookupInResourceBundle(resourceBundle, key)
                .orElse(key);

        return tryFormat(value, params);
    }

    public List<String> listBundleKeys(String bundle, Locale locale) {
        if (bundle == null) {
            return Collections.emptyList();
        }

        Locale searchLocale = tryLookupLocale(locale);

        ResourceBundle resourceBundle;
        try {
            resourceBundle = getBundle(bundle, searchLocale, Thread.currentThread().getContextClassLoader());
        } catch (MissingResourceException e) {
            LOGGER.debug("Could not load resource for bundle {} with locale {}.", bundle, searchLocale);
            return Collections.emptyList();
        }

        return resourceBundle.keySet().stream().toList();
    }

    /**
     * Make sure we only work with the included locales, fallback to NL_BE.
     */
    private static Locale tryLookupLocale(Locale locale) {
        if (LOCALE_FR.equals(locale)) {
            return locale;
        }

        return LOCALE_NL;
    }

    /**
     * Try and lookup a key in a resourcebundle. In case a key is not found null is returned.
     */
    private static Optional<String> tryLookupInResourceBundle(ResourceBundle resourceBundle, String key) {
        try {
            return Optional.of(resourceBundle.getString(key));
        } catch (MissingResourceException e) {
            LOGGER.debug("Could not find translation for key {} in {}.", key, resourceBundle.getBaseBundleName());
            return Optional.empty();
        }
    }

    /**
     * Get the default resourcebundle for the locale.
     */
    private static ResourceBundle getDefaultResourceBundle(Locale locale) {
        if (LOCALE_FR.equals(locale)) {
            return DEFAULT_RESOURCE_BUNDLE_FR;
        }

        return DEFAULT_RESOURCE_BUNDLE_NL;
    }

    /**
     * Format the translation if needed.
     */
    private static String tryFormat(String value, Object... params) {
        if (value == null) {
            return "";
        }

        if (params != null && params.length > 0) {
            return MessageFormat.format(value, params);
        }

        return value;
    }
}
