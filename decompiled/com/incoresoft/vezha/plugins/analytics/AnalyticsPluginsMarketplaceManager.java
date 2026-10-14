/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.analytics;

import com.google.inject.Inject;
import com.incoresoft.marketplace.BasePluginMarketplaceManager;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.marketplace.models.MarketplaceProductType;

public class AnalyticsPluginsMarketplaceManager
extends BasePluginMarketplaceManager {
    @Inject
    public AnalyticsPluginsMarketplaceManager(MarketplaceClient marketplaceClient) {
        super(marketplaceClient);
    }

    @Override
    public MarketplaceProductType getProductType() {
        return MarketplaceProductType.VEZHA_ANALYTICS_PLUGIN;
    }
}

