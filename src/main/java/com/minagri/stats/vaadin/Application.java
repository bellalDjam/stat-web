/*
 * Copyright 2003-2007 LCM-ANMC, Inc. All rights reserved.
 * This source code is the property of LCM-ANMC, Direction
 * Informatique and cannot be copied or distributed without
 * the formal permission of LCM-ANMC.
 */
package com.minagri.stats.vaadin;

import com.minagri.stats.core.java.Dates;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.ConfigProvider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * Provides information about the application
 */
@ApplicationScoped
@Startup
@Getter
public class Application {

	private String startupTime;
	private String version;
	private String buildTime;
	private String node;

	@PostConstruct
	public void init() {
		this.buildTime = ConfigProvider.getConfig().getOptionalValue("application.build-time", String.class).orElse("");
		this.version = ConfigProvider.getConfig().getOptionalValue("quarkus.application.version", String.class).orElse("");
		this.startupTime = Dates.YYYYMMDD_DASHED_HHMMSS.format(LocalDateTime.now());

		try {
			this.node = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			this.node = "";
		}
	}
}
