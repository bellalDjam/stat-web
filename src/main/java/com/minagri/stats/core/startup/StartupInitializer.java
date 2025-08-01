package com.minagri.stats.core.startup;

public interface StartupInitializer {
    int HIGH_PRIORITY = 10;
    int DEFAULT_PRIORITY = 100;

    void initialize();

    default void destroy() {

    }
}
