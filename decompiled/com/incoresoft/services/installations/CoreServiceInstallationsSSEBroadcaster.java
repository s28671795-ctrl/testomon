/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.sse.SSEBroadcaster;
import com.incoresoft.services.installations.events.CoreServiceInstallationCompletedEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationFailedEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationProgressEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationStartedEvent;
import io.javalin.http.sse.SseClient;

public class CoreServiceInstallationsSSEBroadcaster
extends SSEBroadcaster {
    @Inject
    public CoreServiceInstallationsSSEBroadcaster(UsersManager usersManager) {
        super(usersManager, true);
    }

    public void broadcastInstallationStarted(String serviceId) {
        this.broadcast((Object)new CoreServiceInstallationStartedEvent(serviceId), (User user) -> true);
    }

    public void broadcastInstallationProgress(String serviceId, double downloadedPart) {
        this.broadcast((Object)new CoreServiceInstallationProgressEvent(serviceId, downloadedPart), (User user) -> true);
    }

    public void broadcastInstallationCompleted(String serviceId) {
        this.broadcast((Object)new CoreServiceInstallationCompletedEvent(serviceId), (User user) -> true);
    }

    public void broadcastInstallationFailed(String serviceId) {
        this.broadcast((Object)new CoreServiceInstallationFailedEvent(serviceId), (User user) -> true);
    }

    @Override
    protected void onConnectionOpened(SseClient sseClient) {
        sseClient.keepAlive();
    }

    @Override
    protected void onConnectionClosed(SseClient sseClient) {
    }

    @Override
    protected String getPath() {
        return "api/v1/sse/installations/services";
    }
}

