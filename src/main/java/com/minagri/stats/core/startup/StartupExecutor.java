package com.minagri.stats.core.startup;

import com.minagri.stats.core.java.Classes;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class StartupExecutor {
    @Inject
    @Any
    Instance<StartupInitializer> initializers;

    boolean started;

    void onStart(@Observes StartupEvent event) {
        getSortedInitializers().forEach(StartupInitializer::initialize);
        started = true;
    }

    void onShutdown(@Observes ShutdownEvent event) {
        started = false;

        List<StartupInitializer> sortedInitializers = getSortedInitializers();
        Collections.reverse(sortedInitializers);
        sortedInitializers.forEach(StartupInitializer::destroy);
    }

    public boolean isStarted() {
        return started;
    }

    private List<StartupInitializer> getSortedInitializers() {
        ArrayList<StartupInitializer> sortedInitializers = new ArrayList<>();
        initializers.forEach(sortedInitializers::add);
        sortedInitializers.sort((i1, i2) -> {
            Priority priority1Annotation = Classes.getAnnotation(i1.getClass(), Priority.class);
            Priority priority2Annotation = Classes.getAnnotation(i2.getClass(), Priority.class);

            Integer priority1 = Optional.ofNullable(priority1Annotation).map(Priority::value).orElse(StartupInitializer.DEFAULT_PRIORITY);
            Integer priority2 = Optional.ofNullable(priority2Annotation).map(Priority::value).orElse(StartupInitializer.DEFAULT_PRIORITY);

            return priority1.compareTo(priority2);
        });
        return sortedInitializers;
    }
}
