/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.backup.database.availability.BackupUnavailabilityReason;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.http.server.JavalinAvailabilityInterceptor;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.system.processes.ProcessIsAlreadyRunningException;
import com.incoresoft.utils.ConcurrencyUtils;
import com.incoresoft.vezha.backup.BackupsRestoreManager;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.listeners.restore.AuditBackupRestoreListener;
import com.incoresoft.vezha.backup.listeners.restore.BackupInstallPluginsListenerImpl;
import com.incoresoft.vezha.backup.listeners.restore.BackupRestoreListener;
import com.incoresoft.vezha.backup.listeners.restore.LoggingBackupRestoreListener;
import com.incoresoft.vezha.backup.listeners.restore.WSBackupRestoreListener;
import com.incoresoft.vezha.backup.ws.BackupRestoreProcessesRegistry;
import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.ClientBackupRestoreWSMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.ConfirmRestoreMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.ExcludePluginMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.InstallAllPluginsMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.InstallPluginMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.AllPluginsInstallationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreCannotBeStartedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginExclusionCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginExclusionFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationFailedMessage;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.protocol.ws.WSHandler;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import java.lang.runtime.SwitchBootstraps;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupsRestoreWSHandler
extends WSHandler {
    private static final Logger log = LoggerFactory.getLogger(BackupsRestoreWSHandler.class);
    private final UsersManagerImpl usersManager;
    private final Auditor auditor;
    private final BackupsRestoreManager backupsRestoreManager;
    private final JavalinAvailabilityInterceptor javalinAvailabilityInterceptor;
    private final BackupRestoreProcessesRegistry restoreProcessesRegistry;
    private final ApplicationReloadManager reloadManager;
    private final VezhaConfigManager configManager;
    private volatile UserImpl connectedUser;

    @Inject
    public BackupsRestoreWSHandler(UsersManagerImpl usersManager, Auditor auditor, BackupsRestoreManager backupsRestoreManager, JavalinAvailabilityInterceptor javalinAvailabilityInterceptor, BackupRestoreProcessesRegistry restoreProcessesRegistry, ApplicationReloadManager reloadManager, VezhaConfigManager configManager) {
        super(true);
        this.usersManager = usersManager;
        this.auditor = auditor;
        this.backupsRestoreManager = backupsRestoreManager;
        this.javalinAvailabilityInterceptor = javalinAvailabilityInterceptor;
        this.restoreProcessesRegistry = restoreProcessesRegistry;
        this.reloadManager = reloadManager;
        this.configManager = configManager;
    }

    @Override
    protected synchronized void onSessionConnect(WsConnectContext ctx) {
        this.validateConnection(ctx);
        this.connectedUser = Objects.requireNonNull(this.usersManager.getUser(ctx));
        this.javalinAvailabilityInterceptor.setUnavailable(BackupUnavailabilityReason.BACKUP_RESTORE);
    }

    @Override
    protected void onMessage(WsMessageContext ctx) {
        try {
            BackupWSMessage message = ctx.messageAsClass(BackupWSMessage.class);
            if (message instanceof ClientBackupRestoreWSMessage) {
                ClientBackupRestoreWSMessage clientMessage = (ClientBackupRestoreWSMessage)message;
                this.validateClientMessage(clientMessage);
                ClientBackupRestoreWSMessage clientBackupRestoreWSMessage = clientMessage;
                Objects.requireNonNull(clientBackupRestoreWSMessage);
                ClientBackupRestoreWSMessage clientBackupRestoreWSMessage2 = clientBackupRestoreWSMessage;
                int n = 0;
                switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{InstallPluginMessage.class, ExcludePluginMessage.class, InstallAllPluginsMessage.class, ConfirmRestoreMessage.class}, (Object)clientBackupRestoreWSMessage2, n)) {
                    case 0: {
                        InstallPluginMessage m4 = (InstallPluginMessage)clientBackupRestoreWSMessage2;
                        this.handleInstallPluginMessage(ctx, m4);
                        break;
                    }
                    case 1: {
                        ExcludePluginMessage m5 = (ExcludePluginMessage)clientBackupRestoreWSMessage2;
                        this.handleExcludePluginMessage(ctx, m5);
                        break;
                    }
                    case 2: {
                        InstallAllPluginsMessage m6 = (InstallAllPluginsMessage)clientBackupRestoreWSMessage2;
                        this.handleInstallAllPluginsMessage(ctx, m6);
                        break;
                    }
                    case 3: {
                        ConfirmRestoreMessage m7 = (ConfirmRestoreMessage)clientBackupRestoreWSMessage2;
                        this.handleConfirmRestoreMessage(ctx);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected value: " + String.valueOf(clientMessage));
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Error while processing message", e);
            ctx.closeSession();
        }
    }

    private void handleInstallPluginMessage(WsMessageContext ctx, InstallPluginMessage message) {
        String pluginId = message.getPluginId();
        PluginType pluginType = message.getPluginType();
        try {
            this.backupsRestoreManager.installPluginLatestVersion(pluginId, pluginType, new BackupInstallPluginsListenerImpl(ctx, this.connectedUser, this.auditor).getSingleListener(pluginId, pluginType));
        }
        catch (Exception e) {
            log.error("Plugin installation failed", e);
            PluginInstallationFailedMessage failedMessage = new PluginInstallationFailedMessage(message.getPluginId(), message.getPluginType());
            ctx.send(failedMessage);
        }
    }

    private void handleExcludePluginMessage(WsMessageContext ctx, ExcludePluginMessage message) {
        String pluginId = message.getPluginId();
        PluginType pluginType = message.getPluginType();
        try {
            this.backupsRestoreManager.excludePlugin(pluginId, pluginType);
            ctx.send(new PluginExclusionCompletedMessage(pluginId, pluginType));
            this.auditor.audit(null, this.connectedUser, CoreAuditEventAction.PLUGIN_DELETED, "middleware-core", "Plugin was installed with file " + pluginId);
        }
        catch (Exception e) {
            log.error("Plugin exclusion failed", e);
            PluginExclusionFailedMessage failedMessage = new PluginExclusionFailedMessage(message.getPluginId(), message.getPluginType());
            ctx.send(failedMessage);
        }
    }

    private void handleInstallAllPluginsMessage(WsMessageContext ctx, InstallAllPluginsMessage message) {
        try {
            this.backupsRestoreManager.installAllPluginsLatestVersions(message.getPluginType(), new BackupInstallPluginsListenerImpl(ctx, this.connectedUser, this.auditor));
        }
        catch (Exception e) {
            log.error("All plugins installation failed", e);
            AllPluginsInstallationFailedMessage failedMessage = new AllPluginsInstallationFailedMessage(message.getPluginType());
            ctx.send(failedMessage);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleConfirmRestoreMessage(WsMessageContext ctx) {
        try {
            this.restoreProcessesRegistry.registerOrThrowIfRegistered();
        }
        catch (ProcessIsAlreadyRunningException e) {
            ctx.send(new BackupRestoreCannotBeStartedMessage());
            return;
        }
        try {
            BackupFileInfo info = this.backupsRestoreManager.getCurrentBackupFileInfo();
            List<BackupRestoreListener> listeners = List.of(new WSBackupRestoreListener(ctx), new AuditBackupRestoreListener(this.auditor, this.connectedUser, info), new LoggingBackupRestoreListener());
            this.backupsRestoreManager.confirmRestore(listeners);
            ctx.closeSession();
        }
        catch (Exception e) {
            log.error("Backup restore failed", e);
            ctx.send(new BackupRestoreFailedMessage());
            if (this.configManager.isVmsIntegrationEnabled()) {
                this.configManager.disableVmsIntegration();
            }
            ConcurrencyUtils.ensureInterruptedStatusRestored(e);
        }
        finally {
            this.backupsRestoreManager.clearCurrentBackupAssociation();
            this.reloadManager.reload();
        }
    }

    private void validateClientMessage(ClientBackupRestoreWSMessage message) {
        String hash = this.backupsRestoreManager.getCurrentBackupFileInfo().getHash();
        if (!message.getBackupHash().equals(hash)) {
            throw new IllegalStateException("Backup hash mismatch");
        }
    }

    private void validateConnection(WsConnectContext ctx) {
        if (this.backupsRestoreManager.getCurrentBackupFileInfo() == null || this.connectedUser != null) {
            ctx.closeSession();
        }
    }

    @Override
    protected synchronized void onClose(WsCloseContext wsCloseContext) {
        this.connectedUser = null;
        this.javalinAvailabilityInterceptor.setAvailable();
    }

    @Override
    protected void onError(WsErrorContext wsErrorContext) {
        if (wsErrorContext.error() instanceof ClosedChannelException) {
            return;
        }
        log.error("Error while processing message", wsErrorContext.error());
        wsErrorContext.closeSession();
    }
}

