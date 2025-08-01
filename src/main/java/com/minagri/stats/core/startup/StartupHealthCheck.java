package com.minagri.stats.core.startup;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class StartupHealthCheck implements HealthCheck {
    private static final String NAME = "Startup";

    @Inject
    StartupExecutor startupExecutor;

    @Override
    public HealthCheckResponse call() {
        if (startupExecutor.isStarted()) {
            return HealthCheckResponse.up(NAME);
        }
        return HealthCheckResponse.down(NAME);
    }
}
