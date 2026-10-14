/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.marketplace.exceptions.MarketplaceServerUnavailableException;
import com.incoresoft.marketplace.models.MarketplaceProductType;
import com.incoresoft.marketplace.models.ProductVersion;
import com.incoresoft.plugins.exceptions.PluginNotFoundInMarketplaceException;
import com.incoresoft.version.models.MinorVersion;
import java.util.List;

public abstract class BasePluginMarketplaceManager {
    private final MarketplaceClient marketplaceClient;

    public abstract MarketplaceProductType getProductType();

    public List<ProductVersion> getPluginVersions(MinorVersion version) {
        return this.marketplaceClient.getProductVersions(this.getProductType(), version);
    }

    public List<ProductVersion> getPluginVersions(String id, MinorVersion version) {
        return this.marketplaceClient.getProductVersions(id, version);
    }

    public ProductVersion getPluginVersion(String id, Version version) {
        try {
            return this.marketplaceClient.getProductVersion(id, version);
        }
        catch (MarketplaceServerUnavailableException e) {
            throw e;
        }
        catch (Exception e) {
            throw new PluginNotFoundInMarketplaceException(id);
        }
    }

    public BasePluginMarketplaceManager(MarketplaceClient marketplaceClient) {
        this.marketplaceClient = marketplaceClient;
    }
}

