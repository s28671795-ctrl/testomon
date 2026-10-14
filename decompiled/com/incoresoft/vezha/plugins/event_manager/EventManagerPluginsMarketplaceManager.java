/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.event_manager;

import com.google.inject.Inject;
import com.incoresoft.marketplace.BasePluginMarketplaceManager;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.marketplace.models.MarketplaceProductType;

public class EventManagerPluginsMarketplaceManager
extends BasePluginMarketplaceManager {
    @Inject
    public EventManagerPluginsMarketplaceManager(MarketplaceClient marketplaceClient) {
        super(marketplaceClient);
    }

    @Override
    public MarketplaceProductType getProductType() {
        return MarketplaceProductType.VEZHA_EVENT_MANAGER_PLUGIN;
    }
}

