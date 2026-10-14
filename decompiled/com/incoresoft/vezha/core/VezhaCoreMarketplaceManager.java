/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.core;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceMarketplaceManager;
import com.incoresoft.marketplace.MarketplaceClient;

public class VezhaCoreMarketplaceManager
extends CoreServiceMarketplaceManager {
    @Inject
    public VezhaCoreMarketplaceManager(MarketplaceClient marketplaceClient) {
        super(marketplaceClient);
    }

    @Override
    public String getProductId() {
        return "middleware-core";
    }
}

