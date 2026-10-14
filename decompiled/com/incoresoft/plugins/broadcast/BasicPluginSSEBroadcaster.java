/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.sse.SSEBroadcaster;
import com.incoresoft.plugins.broadcast.PluginInstallationsSSEBroadcaster;
import com.incoresoft.plugins.broadcast.PluginStateSSEBroadcaster;
import com.incoresoft.plugins.broadcast.events.AllPluginsUpdatedEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationCompletedEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationFailedEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationProgressEvent;
import com.incoresoft.plugins.broadcast.events.PluginInstallationStartedEvent;
import com.incoresoft.plugins.broadcast.events.PluginStartedEvent;
import com.incoresoft.plugins.broadcast.events.PluginStoppedEvent;
import com.incoresoft.plugins.broadcast.events.PluginUninstalledEvent;
import com.incoresoft.plugins.models.PluginType;
import io.javalin.http.sse.SseClient;

public class BasicPluginSSEBroadcaster
extends SSEBroadcaster
implements PluginInstallationsSSEBroadcaster,
PluginStateSSEBroadcaster {
    @Inject
    public BasicPluginSSEBroadcaster(UsersManager usersManager) {
        super(usersManager, true);
    }

    @Override
    public void broadcastInstallationStarted(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginInstallationStartedEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    public void broadcastInstallationProgress(String pluginId, PluginType pluginType, double downloadedPart) {
        this.broadcast((Object)new PluginInstallationProgressEvent(pluginId, pluginType, downloadedPart), (User user) -> true);
    }

    @Override
    public void broadcastInstallationCompleted(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginInstallationCompletedEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    public void broadcastInstallationFailed(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginInstallationFailedEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    public void broadcastAllPluginsUpdated(PluginType pluginType) {
        this.broadcast((Object)new AllPluginsUpdatedEvent(pluginType), (User user) -> true);
    }

    @Override
    public void broadcastPluginStarted(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginStartedEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    public void broadcastPluginStopped(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginStoppedEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    public void broadcastPluginUninstalled(String pluginId, PluginType pluginType) {
        this.broadcast((Object)new PluginUninstalledEvent(pluginId, pluginType), (User user) -> true);
    }

    @Override
    protected void onConnectionOpened(SseClient sseClient) {
        sseClient.keepAlive();
    }

    @Override
    protected void onConnectionClosed(SseClient sseClient) {
    }

    @Override
    public String getPath() {
        return "api/v1/sse/plugins";
    }
}

