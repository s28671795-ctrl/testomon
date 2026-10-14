/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.metadata;

import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import java.util.List;

public interface ListenerFactory {
    public String getPluginName();

    public List<AnalyticsMetadataListener<? extends AnalyticsMetadata>> createMetadataListeners(String var1);

    public List<ServiceMetadataListener<? extends ServiceMetadata>> createServiceListeners(String var1);
}

