/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.listeners;

import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.broadcast.PluginInstallationsSSEBroadcaster;
import com.incoresoft.plugins.models.PluginType;

public class SSEPluginInstallationListener
implements InstallationListener {
    private final PluginInstallationsSSEBroadcaster broadcaster;
    private final String pluginId;
    private final PluginType pluginType;

    @Override
    public void onStart() {
        this.broadcaster.broadcastInstallationStarted(this.pluginId, this.pluginType);
    }

    @Override
    public void onPartDownloaded(double downloadedPart) {
        this.broadcaster.broadcastInstallationProgress(this.pluginId, this.pluginType, downloadedPart);
    }

    @Override
    public void onDownloaded() {
    }

    @Override
    public void onPreInstall() {
    }

    @Override
    public void onInstalled() {
        this.broadcaster.broadcastInstallationCompleted(this.pluginId, this.pluginType);
    }

    @Override
    public void onError(Throwable throwable) {
        this.broadcaster.broadcastInstallationFailed(this.pluginId, this.pluginType);
    }

    public SSEPluginInstallationListener(PluginInstallationsSSEBroadcaster broadcaster, String pluginId, PluginType pluginType) {
        this.broadcaster = broadcaster;
        this.pluginId = pluginId;
        this.pluginType = pluginType;
    }
}

