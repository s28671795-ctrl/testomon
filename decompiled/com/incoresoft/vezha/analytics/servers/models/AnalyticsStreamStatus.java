/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AnalyticsStreamStatus {
    offline,
    online,
    unknown;

    private static final Logger log;

    public static AnalyticsStreamStatus fromWire(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (AnalyticsStreamStatus it : AnalyticsStreamStatus.values()) {
            if (!it.name().equalsIgnoreCase(value)) continue;
            return it;
        }
        log.warn("Unknown stream status '{}' in heartbeat, treating as {}", (Object)value, (Object)unknown);
        return unknown;
    }

    static {
        log = LoggerFactory.getLogger(AnalyticsStreamStatus.class);
    }
}

