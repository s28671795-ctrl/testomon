/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.BasePluginUpdatesManager;
import com.incoresoft.plugins.BasePluginVersionsManager;
import com.incoresoft.plugins.PluginsFacade;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BasePluginsFacade
implements PluginsFacade {
    protected final BasePluginJarsManager pluginJarsManager;
    protected final BasePluginVersionsManager pluginVersionsManager;
    protected final BasePluginUpdatesManager basePluginUpdatesManager;

    @Override
    public List<MarketplacePluginInfo> getMarketplacePluginsInfos() {
        return this.pluginVersionsManager.getLatestMarketplacePluginsInfos();
    }

    @Override
    public List<MarketplacePluginInfo> getMarketplacePluginInfos(String pluginId) {
        return this.pluginVersionsManager.getMarketplacePluginInfos(pluginId);
    }

    @Override
    public List<InstalledPluginInfo> getInstalledPluginsInfos() {
        return this.pluginVersionsManager.getInstalledPluginsInfos();
    }

    @Override
    public void installPlugin(String pluginId, Version version, InstallationListener ... listeners) {
        this.basePluginUpdatesManager.installPlugin(pluginId, version, listeners);
    }

    @Override
    public void installPlugin(Path pluginPath) {
        this.basePluginUpdatesManager.installPlugin(pluginPath);
    }

    @Override
    public void installLatestVersion(String pluginId, InstallationListener ... listeners) {
        this.basePluginUpdatesManager.installLatestVersion(pluginId, listeners);
    }

    @Override
    public void updatePlugin(String pluginId, Version version, InstallationListener ... listeners) {
        this.basePluginUpdatesManager.updatePlugin(pluginId, version, listeners);
    }

    @Override
    public void updatePlugin(String pluginId, Path pluginPath) {
        this.basePluginUpdatesManager.updatePlugin(pluginId, pluginPath);
    }

    @Override
    public List<CompletableFuture<Void>> updateAll(InstallationListener ... listeners) {
        return this.basePluginUpdatesManager.updateAll(listeners);
    }

    @Override
    public void uninstallPlugin(String pluginId) {
        this.pluginJarsManager.uninstallPlugin(pluginId);
    }

    @Override
    public void loadAndStartEnabledPlugins() {
        this.pluginJarsManager.loadAndStartEnabledPlugins();
    }

    public BasePluginsFacade(BasePluginJarsManager pluginJarsManager, BasePluginVersionsManager pluginVersionsManager, BasePluginUpdatesManager basePluginUpdatesManager) {
        this.pluginJarsManager = pluginJarsManager;
        this.pluginVersionsManager = pluginVersionsManager;
        this.basePluginUpdatesManager = basePluginUpdatesManager;
    }
}

