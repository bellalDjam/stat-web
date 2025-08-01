package com.minagri.stats.vaadin.translation.control;

import com.minagri.stats.core.enumeration.control.Enums;
import com.minagri.stats.core.java.Booleans;
import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.data.binder.ErrorMessageProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;

@Slf4j
@UtilityClass
public class Translator {

    public static Translation createTranslation(String key, Object... messageParameters) {
        if (Strings.isBlank(key)) {
            return null;
        }

        Translation translation = new Translation();
        translation.setKey(key);
        translation.setMessageParameters(messageParameters);
        return translation;
    }

    public static Translation createTranslation(Enum<?> key, Object... messageParameters) {
        if (key == null) {
            return null;
        }

        Translation translation = new Translation();
        translation.setKey(String.format("%s.%s", key.getClass().getSimpleName(), key.name()));
        translation.setMessageParameters(messageParameters);
        return translation;
    }

    public static Translation createTranslation(TranslationKey key, Object... messageParameters) {
        if (key == null) {
            return null;
        }

        Translation translation = new Translation();
        translation.setKey(key.getKey());
        translation.setMessageParameters(messageParameters);
        return translation;
    }

    public static Translation createTranslation(String explicitValueNl, String explicitValueFr) {
        Translation translation = new Translation();
        translation.setExplicitValues(Map.of(LCMI18NProvider.LOCALE_NL, Strings.nullToEmpty(explicitValueNl), LCMI18NProvider.LOCALE_FR, Strings.nullToEmpty(explicitValueFr)));
        return translation;
    }

    public static String translate(Translation translation) {
        if (translation == null) {
            return Strings.EMPTY;
        }

        if (translation.getExplicitValues() != null) {
            VaadinSession vaadinSession = VaadinSession.getCurrent();
            Locale locale = (vaadinSession == null ? LCMI18NProvider.LOCALE_NL : vaadinSession.getLocale());
            return translation.getExplicitValues().get(locale);
        }

        return translate(translation.getKey(), translation.getMessageParameters());
    }

    public static String translate(Boolean value) {
        if (value == null) {
            return Strings.EMPTY;
        }

        return translate(value ? CommonMessage.YES : CommonMessage.NO);
    }

    public static String translate(String key, Object... messageParameters) {
        if (Strings.isBlank(key)) {
            return Strings.EMPTY;
        }

        VaadinSession vaadinSession = VaadinSession.getCurrent();
        Locale locale = (vaadinSession == null ? LCMI18NProvider.LOCALE_NL : vaadinSession.getLocale());

        I18NProvider i18NProvider = VaadinService.getCurrent().getInstantiator().getI18NProvider();
        return i18NProvider.getTranslation(key, locale, messageParameters);
    }

    public static <T extends Enum<?>> String translateFromBundle(T bundle, Object key, Object... messageParameters) {
        return translateFromBundle(Enums.getName(bundle), key, messageParameters);
    }

    public static String translateFromBundle(String bundle, Object key, Object... messageParameters) {
        if (key == null || Strings.isBlank(key.toString())) {
            return Strings.EMPTY;
        }

        VaadinSession vaadinSession = VaadinSession.getCurrent();
        Locale locale = (vaadinSession == null ? LCMI18NProvider.LOCALE_NL : vaadinSession.getLocale());

        LCMI18NProvider i18NProvider = (LCMI18NProvider) VaadinService.getCurrent().getInstantiator().getI18NProvider();
        return i18NProvider.getTranslation(bundle, key.toString(), locale, messageParameters);
    }

    public static <R, T extends TranslationKey> String translateTranslationKeyProperty(R object, Function<R, T> propertyGetter) {
        T property = propertyGetter.apply(object);
        if (property != null) {
            return translate(property.getKey());
        }

        return Strings.EMPTY;
    }

    public static <T extends Enum<?>> List<String> listBundleKeys(T bundle) {
        return listBundleKeys(Enums.getName(bundle));
    }

    public static List<String> listBundleKeys(String bundle) {
        if (Strings.isBlank(bundle)) {
            return emptyList();
        }

        VaadinSession vaadinSession = VaadinSession.getCurrent();
        Locale locale = (vaadinSession == null ? LCMI18NProvider.LOCALE_NL : vaadinSession.getLocale());

        LCMI18NProvider i18NProvider = (LCMI18NProvider) VaadinService.getCurrent().getInstantiator().getI18NProvider();
        return i18NProvider.listBundleKeys(bundle, locale);
    }

    public static String translate(Enum<?> key, Object... messageParameters) {
        Translation translation = createTranslation(key, messageParameters);
        return translate(translation);
    }

    public static String translateKey(TranslationKey key, Object... messageParameters) {
        Translation translation = createTranslation(key, messageParameters);
        return translate(translation);
    }

    public static <R, T extends Enum<?>> ValueProvider<R, String> translateEnumValueProvider(Function<R, T> propertyGetter) {
        return source -> {
            T property = propertyGetter.apply(source);
            if (property != null) {
                return translate(property);
            }
            return Strings.EMPTY;
        };
    }

    public static <R, T extends Enum<?>> ValueProvider<R, String> translateFromBundleValueProvider(T bundle, Function<R, String> propertyGetter) {
        return translateFromBundleValueProvider(Enums.getName(bundle), propertyGetter);
    }

    public static <R> ValueProvider<R, String> translateFromBundleValueProvider(String bundle, Function<R, String> propertyGetter) {
        return source -> {
            String property = propertyGetter.apply(source);
            if (property != null) {
                return translateFromBundle(bundle, property);
            }
            return Strings.EMPTY;
        };
    }

    public static <R, T extends TranslationKey> ValueProvider<R, String> translateEnumTranslationKey(Function<R, T> propertyGetter) {
        return myDto -> {
            T property = propertyGetter.apply(myDto);
            if (property != null) {
                return translateKey(property);
            }
            return Strings.EMPTY;
        };
    }

    public static <SOURCE, TARGET> ValueProvider<SOURCE, TARGET> localizedValueProvider(ValueProvider<SOURCE, TARGET> dutchProvider, ValueProvider<SOURCE, TARGET> frenchProvider) {
        return source -> {
            if (LCMI18NProvider.getLocale() == LCMI18NProvider.LOCALE_FR) {
                return frenchProvider.apply(source);
            }
            return dutchProvider.apply(source);
        };
    }

    public static <R> ValueProvider<R, String> translationValueProvider(Function<R, Translation> translationGetter) {
        return source -> {
            Translation translation = translationGetter.apply(source);
            if (translation != null) {
                return translate(translation);
            }
            return Strings.EMPTY;
        };
    }

    public static <T> ItemLabelGenerator<T> localizedItemLabelGenerator(ItemLabelGenerator<T> dutchProvider, ItemLabelGenerator<T> frenchProvider) {
        return source -> {
            if (LCMI18NProvider.getLocale() == LCMI18NProvider.LOCALE_FR) {
                return frenchProvider.apply(source);
            }
            return dutchProvider.apply(source);
        };
    }

    public static <TARGET> TARGET localizedValue(Supplier<TARGET> dutchProvider, Supplier<TARGET> frenchProvider) {
        if (LCMI18NProvider.getLocale() == LCMI18NProvider.LOCALE_FR) {
            return frenchProvider.get();
        }
        return dutchProvider.get();
    }

    public static <TARGET> TARGET localizedValue(TARGET dutchValue, TARGET frenchValue) {
        if (LCMI18NProvider.getLocale() == LCMI18NProvider.LOCALE_FR) {
            return frenchValue;
        }
        return dutchValue;
    }

    public static <SOURCE> ValueProvider<SOURCE, String> yesNoMessageProvider(ValueProvider<SOURCE, Boolean> valueProvider) {
        return source -> {
            CommonMessage yesNoMessage = Booleans.isTrue(valueProvider.apply(source)) ? CommonMessage.YES : CommonMessage.NO;
            return translate(yesNoMessage);
        };
    }

    public static ErrorMessageProvider createErrorMessageProvider(String key, Object... messageParameters) {
        return context -> Translator.translate(key, messageParameters);
    }

    public static ErrorMessageProvider createErrorMessageProvider(Enum<?> key, Object... messageParameters) {
        return context -> Translator.translate(key, messageParameters);
    }

    public static ErrorMessageProvider createErrorMessageProvider(TranslationKey key, Object... messageParameters) {
        return context -> Translator.translateKey(key, messageParameters);
    }
}
