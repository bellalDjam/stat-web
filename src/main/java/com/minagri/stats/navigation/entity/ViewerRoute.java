package com.minagri.stats.navigation.entity;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ViewerRoute {
    private static final String ROOT = "app";

    public static final String HOME = ROOT;
    public static final String ZONE = ROOT + "/Zone";
}
