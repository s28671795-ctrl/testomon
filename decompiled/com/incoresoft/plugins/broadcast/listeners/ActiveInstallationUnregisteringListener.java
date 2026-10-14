/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.listeners;

import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.broadcast.PluginInstallationsRegistry;

public class ActiveInstallationUnregisteringListener
implements InstallationListener {
    private final PluginInstallationsRegistry installationsRegistry;
    private final String pluginId;

    @Override
    public void onStart() {
    }

    @Override
    public void onPartDownloaded(double v) {
    }

    @Override
    public void onDownloaded() {
    }

    @Override
    public void onPreInstall() {
    }

    @Override
    public void onInstalled() {
        this.installationsRegistry.unregister(this.pluginId);
    }

    @Override
    public void onError(Throwable throwable) {
        this.installationsRegistry.unregister(this.pluginId);
    }

    public ActiveInstallationUnregisteringListener(PluginInstallationsRegistry installationsRegistry, String pluginId) {
        this.installationsRegistry = installationsRegistry;
        this.pluginId = pluginId;
    }
}

