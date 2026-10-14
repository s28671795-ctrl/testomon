/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.marketplace.BasePluginMarketplaceManager;
import com.incoresoft.marketplace.dto.ClientVisibility;
import com.incoresoft.marketplace.models.ProductVersion;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.exceptions.PluginNotFoundInMarketplaceException;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import com.incoresoft.plugins.models.PluginFileInfo;
import com.incoresoft.version.models.MinorVersion;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BasePluginVersionsManager {
    private final BasePluginJarsManager pluginJarsManager;
    private final BasePluginMarketplaceManager marketplaceManager;
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;

    public List<MarketplacePluginInfo> getLatestMarketplacePluginsInfos() {
        Set installedPluginIds = this.pluginJarsManager.getPlugins().stream().map(PluginFileInfo::getId).collect(Collectors.toUnmodifiableSet());
        return this.marketplaceManager.getPluginVersions(this.coreServiceVersionSupplier.getMinorCoreVersion()).stream().filter(it -> it.getClientVisibility() == ClientVisibility.FOR_ALL || installedPluginIds.contains(it.getId())).map(it -> new MarketplacePluginInfo((ProductVersion)it, installedPluginIds.contains(it.getId()))).toList();
    }

    public List<MarketplacePluginInfo> getMarketplacePluginInfos(String pluginId) {
        MinorVersion version = this.coreServiceVersionSupplier.getMinorCoreVersion();
        Set installedPluginIds = this.pluginJarsManager.getPlugins().stream().map(PluginFileInfo::getId).collect(Collectors.toUnmodifiableSet());
        List<ProductVersion> versions = this.marketplaceManager.getPluginVersions(pluginId, version);
        return versions.stream().map(productVersion -> new MarketplacePluginInfo((ProductVersion)productVersion, installedPluginIds.contains(productVersion.getId()))).toList();
    }

    public MarketplacePluginInfo getMarketplacePluginInfo(String id, Version version) {
        boolean isInstalled = this.pluginJarsManager.getPlugin(id).isPresent();
        return new MarketplacePluginInfo(this.marketplaceManager.getPluginVersion(id, version), isInstalled);
    }

    public MarketplacePluginInfo getLatestMarketplacePluginInfo(String id) {
        boolean isInstalled = this.pluginJarsManager.getPlugin(id).isPresent();
        return this.marketplaceManager.getPluginVersions(id, this.coreServiceVersionSupplier.getMinorCoreVersion()).stream().map(it -> new MarketplacePluginInfo((ProductVersion)it, isInstalled)).findFirst().orElseThrow(() -> new PluginNotFoundInMarketplaceException(id));
    }

    public List<InstalledPluginInfo> getInstalledPluginsInfos() {
        return this.pluginJarsManager.getPlugins().stream().map(it -> new InstalledPluginInfo((PluginFileInfo)it, this.marketplaceManager.getProductType().getValue())).toList();
    }

    public BasePluginVersionsManager(BasePluginJarsManager pluginJarsManager, BasePluginMarketplaceManager marketplaceManager, CoreServiceVersionSupplier coreServiceVersionSupplier) {
        this.pluginJarsManager = pluginJarsManager;
        this.marketplaceManager = marketplaceManager;
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
    }
}

