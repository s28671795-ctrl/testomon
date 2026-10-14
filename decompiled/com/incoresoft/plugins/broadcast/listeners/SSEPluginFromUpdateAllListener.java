/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.listeners;

import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.PluginUpdatesTracker;
import com.incoresoft.plugins.broadcast.PluginInstallationsSSEBroadcaster;
import com.incoresoft.plugins.models.PluginType;

public class SSEPluginFromUpdateAllListener
implements InstallationListener {
    private final PluginInstallationsSSEBroadcaster broadcaster;
    private final PluginUpdatesTracker tracker;
    private final String pluginId;
    private final PluginType pluginType;

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
        this.tracker.trackUpdateFinished(this.pluginId);
        if (this.tracker.areAllUpdatesFinished()) {
            this.broadcaster.broadcastAllPluginsUpdated(this.pluginType);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        this.tracker.trackUpdateFinished(this.pluginId);
        if (this.tracker.areAllUpdatesFinished()) {
            this.broadcaster.broadcastAllPluginsUpdated(this.pluginType);
        }
    }

    public SSEPluginFromUpdateAllListener(PluginInstallationsSSEBroadcaster broadcaster, PluginUpdatesTracker tracker, String pluginId, PluginType pluginType) {
        this.broadcaster = broadcaster;
        this.tracker = tracker;
        this.pluginId = pluginId;
        this.pluginType = pluginType;
    }
}

