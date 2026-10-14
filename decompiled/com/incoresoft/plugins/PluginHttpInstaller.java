/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.incoresoft.http.HttpInstaller;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import java.nio.file.Path;

public class PluginHttpInstaller
extends HttpInstaller {
    private final BasePluginJarsManager jarsManager;
    private final boolean startAfterInstallation;

    public PluginHttpInstaller(BasePluginJarsManager jarsManager, MarketplacePluginInfo plugin, Path installPath, boolean startAfterInstallation) {
        super(plugin.getDownloadUrl(), Path.of("temp", installPath.getFileName().toString()), installPath, plugin.getHash(), 10);
        this.jarsManager = jarsManager;
        this.startAfterInstallation = startAfterInstallation;
    }

    @Override
    protected void install() {
        if (this.startAfterInstallation) {
            this.jarsManager.loadAndStart(this.installPath);
        } else {
            this.jarsManager.loadPlugin(this.installPath);
        }
    }
}

