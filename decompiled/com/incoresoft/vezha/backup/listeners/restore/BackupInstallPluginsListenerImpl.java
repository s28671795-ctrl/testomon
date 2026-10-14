/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.listeners.restore.BackupInstallPluginsListener;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationProgressMessage;
import com.incoresoft.vezha.models.users.UserImpl;
import io.javalin.websocket.WsMessageContext;

public class BackupInstallPluginsListenerImpl
implements BackupInstallPluginsListener {
    private final WsMessageContext context;
    private final UserImpl user;
    private final Auditor auditor;

    @Override
    public void onStart(String pluginId, PluginType pluginType) {
    }

    @Override
    public void onPartDownloaded(String pluginId, PluginType pluginType, double downloadedPart) {
        this.context.send(new PluginInstallationProgressMessage(pluginId, pluginType, downloadedPart));
    }

    @Override
    public void onDownloaded(String pluginId, PluginType pluginType) {
    }

    @Override
    public void onInstalled(String pluginId, PluginType pluginType) {
        this.context.send(new PluginInstallationCompletedMessage(pluginId, pluginType));
        this.auditor.audit(null, this.user, CoreAuditEventAction.PLUGIN_INSTALLED, "middleware-core");
    }

    @Override
    public void onError(String pluginId, PluginType pluginType, Throwable var1) {
        this.context.send(new PluginInstallationFailedMessage(pluginId, pluginType));
    }

    public BackupInstallPluginsListenerImpl(WsMessageContext context, UserImpl user, Auditor auditor) {
        this.context = context;
        this.user = user;
        this.auditor = auditor;
    }
}

