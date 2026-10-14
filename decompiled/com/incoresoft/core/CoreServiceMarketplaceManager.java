/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.marketplace.MarketplaceClient;
import com.incoresoft.marketplace.exceptions.MarketplaceServerUnavailableException;
import com.incoresoft.marketplace.models.ProductVersion;
import com.incoresoft.services.exceptions.ServiceVersionNotFoundException;
import com.incoresoft.version.models.MinorVersion;
import java.util.List;

public abstract class CoreServiceMarketplaceManager {
    private final MarketplaceClient marketplaceClient;

    public abstract String getProductId();

    public List<ProductVersion> getCoreVersions(MinorVersion version) {
        try {
            return this.marketplaceClient.getProductVersions(this.getProductId(), version);
        }
        catch (MarketplaceServerUnavailableException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceVersionNotFoundException(this.getProductId(), version.toString());
        }
    }

    public ProductVersion getCoreVersion(Version version) {
        try {
            return this.marketplaceClient.getProductVersion(this.getProductId(), version);
        }
        catch (MarketplaceServerUnavailableException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceVersionNotFoundException(this.getProductId(), version.toString());
        }
    }

    public CoreServiceMarketplaceManager(MarketplaceClient marketplaceClient) {
        this.marketplaceClient = marketplaceClient;
    }
}

