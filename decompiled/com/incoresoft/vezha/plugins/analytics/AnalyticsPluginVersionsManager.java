/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.analytics;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.plugins.BasePluginVersionsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginJarsManager;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsMarketplaceManager;

public class AnalyticsPluginVersionsManager
extends BasePluginVersionsManager {
    @Inject
    public AnalyticsPluginVersionsManager(AnalyticsPluginJarsManager systemVezhaPluginsManager, AnalyticsPluginsMarketplaceManager marketplaceManager, CoreServiceVersionSupplier coreServiceVersionSupplier) {
        super(systemVezhaPluginsManager, marketplaceManager, coreServiceVersionSupplier);
    }
}

