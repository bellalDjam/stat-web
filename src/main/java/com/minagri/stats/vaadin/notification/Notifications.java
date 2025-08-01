package com.minagri.stats.vaadin.notification;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Notifications {
    public static void createErrorNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    public static void createErrorNotification(TranslationKey key, Object... messageParameters) {
        createErrorNotification(Translator.translateKey(key, messageParameters));
    }

    public static void createInfoNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.open();
    }

    public static void createInfoNotification(TranslationKey key, Object... messageParameters) {
        createInfoNotification(Translator.translateKey(key, messageParameters));
    }
}
