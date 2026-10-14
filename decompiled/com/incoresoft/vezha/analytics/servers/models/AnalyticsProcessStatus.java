/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AnalyticsProcessStatus {
    starting,
    warming,
    fps_calc,
    working,
    unknown;

    private static final Logger log;

    public static AnalyticsProcessStatus fromWire(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (AnalyticsProcessStatus it : AnalyticsProcessStatus.values()) {
            if (!it.name().equalsIgnoreCase(value)) continue;
            return it;
        }
        log.warn("Unknown analytics status '{}' in heartbeat, treating as {}", (Object)value, (Object)unknown);
        return unknown;
    }

    static {
        log = LoggerFactory.getLogger(AnalyticsProcessStatus.class);
    }
}

