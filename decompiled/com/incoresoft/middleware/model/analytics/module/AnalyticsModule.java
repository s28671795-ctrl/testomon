/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module;

import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareSettings;
import java.util.List;

public interface AnalyticsModule {
    public String getAnalyticPluginName();

    public List<AnalyticsTypes> getAnalyticsTypes();

    public HardwareSettings getHardwareSettings();
}

