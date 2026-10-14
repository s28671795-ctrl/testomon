/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics;

import com.incoresoft.middleware.model.analytics.AnalyticsServer;
import com.incoresoft.middleware.model.analytics.Gpu;

public interface AnalyticsHardwareInfo {
    public AnalyticsServer getAnalyticsServer();

    public Gpu getGpu();
}

