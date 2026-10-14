/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.backups.BackupCreatedAuditMessage;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import com.incoresoft.plugin.commons.utils.JavalinFile;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.backup.BackupNamesManager;
import com.incoresoft.vezha.backup.BackupsCreationManager;
import com.incoresoft.vezha.backup.BackupsRestoreManager;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.file.BackupFileRestoreInfo;
import com.incoresoft.vezha.backup.file.BackupFilesManager;
import com.incoresoft.vezha.backup.listeners.creation.BackupCreationListener;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import com.incoresoft.vezha.database.BackupInfosRepository;
import java.io.File;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class BackupsManager {
    private final BackupInfosRepository repository;
    private final BackupsCreationManager backupsCreationManager;
    private final BackupsRestoreManager backupsRestoreManager;
    private final BackupFilesManager backupFilesManager;
    private final BackupNamesManager backupNamesManager;
    private final Auditor auditor;

    public Pair<Integer, List<BackupInfo>> getAllInfos(StartEndDateParams dateParams, PaginationParams paginationParams) {
        return this.repository.getAll(dateParams, paginationParams);
    }

    public BackupInfo getBackupInfo(int id) {
        return this.repository.getBackupInfoOrThrow(id);
    }

    public boolean exists(String name) {
        return this.repository.exists(BackupFilesManager.withZipExtension(name));
    }

    public boolean exists(int id) {
        return this.repository.exists(id);
    }

    public BackupInfo create(User user, CreateBackupMessage createBackupMessage, List<BackupCreationListener> listeners) {
        createBackupMessage.setName(this.backupNamesManager.getUniqueName(createBackupMessage.getName()));
        BackupFileInfo fileInfo = this.backupsCreationManager.createBackupFile(createBackupMessage, listeners);
        BackupInfo info = new BackupInfo(createBackupMessage, fileInfo);
        BackupInfo added = this.repository.add(info);
        ListenersUtils.triggerListeners(l -> l.onBackupCreationCompleted(added.getId()), listeners);
        this.auditor.audit(null, user, CoreAuditEventAction.BACKUP_CREATED, "middleware-core", new BackupCreatedAuditMessage(info.getId(), fileInfo.getFileName()));
        return added;
    }

    public BackupInfo delete(int id) {
        BackupInfo backupInfo = this.repository.getBackupInfoOrThrow(id);
        this.repository.delete(backupInfo.getId());
        this.backupFilesManager.deleteBackupFile(backupInfo.getFileName());
        return backupInfo;
    }

    public File getBackupFile(int id) {
        BackupInfo backupInfo = this.repository.getBackupInfoOrThrow(id);
        return this.backupFilesManager.getBackupZipFile(backupInfo.getFileName());
    }

    public BackupFileRestoreInfo prepareForRestore(int id) {
        String backupFileName = this.repository.getBackupInfoOrThrow(id).getFileName();
        File backupZipFile = this.backupFilesManager.getBackupZipFile(backupFileName);
        return this.backupsRestoreManager.prepareForRestore(backupZipFile);
    }

    public BackupFileRestoreInfo prepareForRestore(JavalinFile javalinFile) {
        File backupZipFile = this.backupFilesManager.saveBackupZipFile(javalinFile);
        return this.backupsRestoreManager.prepareForRestore(backupZipFile);
    }

    @Inject
    public BackupsManager(BackupInfosRepository repository, BackupsCreationManager backupsCreationManager, BackupsRestoreManager backupsRestoreManager, BackupFilesManager backupFilesManager, BackupNamesManager backupNamesManager, Auditor auditor) {
        this.repository = repository;
        this.backupsCreationManager = backupsCreationManager;
        this.backupsRestoreManager = backupsRestoreManager;
        this.backupFilesManager = backupFilesManager;
        this.backupNamesManager = backupNamesManager;
        this.auditor = auditor;
    }
}

