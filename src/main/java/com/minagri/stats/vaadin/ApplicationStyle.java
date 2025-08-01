package com.minagri.stats.vaadin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationStyle {

    CM("frontend/icons/favicon.ico"),
    OPENMUT("frontend/icons/favicon_openmut.png");

    final String faviconLocation;
}