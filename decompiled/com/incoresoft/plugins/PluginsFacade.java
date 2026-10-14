/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PluginsFacade {
    public List<MarketplacePluginInfo> getMarketplacePluginsInfos();

    public List<MarketplacePluginInfo> getMarketplacePluginInfos(String var1);

    public List<InstalledPluginInfo> getInstalledPluginsInfos();

    public void installPlugin(String var1, Version var2, InstallationListener ... var3);

    public void installPlugin(Path var1);

    public void installLatestVersion(String var1, InstallationListener ... var2);

    public void updatePlugin(String var1, Version var2, InstallationListener ... var3);

    public void updatePlugin(String var1, Path var2);

    public List<CompletableFuture<Void>> updateAll(InstallationListener ... var1);

    public void uninstallPlugin(String var1);

    public void loadAndStartEnabledPlugins();
}

