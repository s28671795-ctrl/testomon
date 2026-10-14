/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message;

import com.incoresoft.vezha.backup.ws.DatabaseTablePopulatedStageMessage;
import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;
import com.incoresoft.vezha.backup.ws.message.create.client.CancelBackupCreationMessage;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationCancellationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.create.server.BackupCreationProgressMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.ConfirmRestoreMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.ExcludePluginMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.InstallAllPluginsMessage;
import com.incoresoft.vezha.backup.ws.message.restore.client.InstallPluginMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.AllPluginsInstallationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreCannotBeStartedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.BackupRestoreProgressMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginExclusionCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginExclusionFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationCompletedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationFailedMessage;
import com.incoresoft.vezha.backup.ws.message.restore.server.PluginInstallationProgressMessage;

public enum BackupWsMessageType {
    DATABASE_TABLE_POPULATED(DatabaseTablePopulatedStageMessage.class),
    CREATE_BACKUP(CreateBackupMessage.class),
    CANCEL_BACKUP_CREATION(CancelBackupCreationMessage.class),
    BACKUP_CREATION_PROGRESS(BackupCreationProgressMessage.class),
    BACKUP_CREATION_FAILED(BackupCreationFailedMessage.class),
    BACKUP_CREATION_COMPLETED(BackupCreationCompletedMessage.class),
    BACKUP_CREATION_CANCELLATION_FAILED(BackupCreationCancellationFailedMessage.class),
    INSTALL_PLUGIN(InstallPluginMessage.class),
    EXCLUDE_PLUGIN(ExcludePluginMessage.class),
    INSTALL_ALL(InstallAllPluginsMessage.class),
    CONFIRM_RESTORE(ConfirmRestoreMessage.class),
    PLUGIN_INSTALLATION_FAILED(PluginInstallationFailedMessage.class),
    ALL_PLUGINS_INSTALLATION_FAILED(AllPluginsInstallationFailedMessage.class),
    PLUGIN_INSTALLATION_PROGRESS(PluginInstallationProgressMessage.class),
    PLUGIN_INSTALLATION_COMPLETED(PluginInstallationCompletedMessage.class),
    PLUGIN_EXCLUSION_COMPLETED(PluginExclusionCompletedMessage.class),
    PLUGIN_EXCLUSION_FAILED(PluginExclusionFailedMessage.class),
    BACKUP_RESTORE_CANNOT_BE_STARTED(BackupRestoreCannotBeStartedMessage.class),
    BACKUP_RESTORE_FAILED(BackupRestoreFailedMessage.class),
    BACKUP_RESTORE_PROGRESS(BackupRestoreProgressMessage.class),
    BACKUP_RESTORE_COMPLETED(BackupRestoreCompletedMessage.class),
    UNKNOWN(null);

    private final Class<? extends BackupWSMessage> messageClass;

    public String toString() {
        return this.name().toLowerCase();
    }

    public static BackupWsMessageType parse(String value) {
        return BackupWsMessageType.valueOf(value.toUpperCase());
    }

    public static BackupWsMessageType getByMessageClass(Class<? extends BackupWSMessage> messageClass) {
        for (BackupWsMessageType messageType : BackupWsMessageType.values()) {
            if (!messageType.getMessageClass().equals(messageClass)) continue;
            return messageType;
        }
        return UNKNOWN;
    }

    public Class<? extends BackupWSMessage> getMessageClass() {
        return this.messageClass;
    }

    private BackupWsMessageType(Class<? extends BackupWSMessage> messageClass) {
        this.messageClass = messageClass;
    }
}

