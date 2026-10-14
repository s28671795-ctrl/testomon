/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics;

import java.time.Instant;

public interface AnalyticsServer {
    public String getId();

    public Instant lastHeartBeat();
}

