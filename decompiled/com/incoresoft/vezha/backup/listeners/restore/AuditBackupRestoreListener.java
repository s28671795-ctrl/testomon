/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.backups.BackupRestoredAuditMessage;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.vezha.backup.BackupRestoreStage;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.listeners.restore.BackupRestoreListener;
import com.incoresoft.vezha.models.users.UserImpl;

public class AuditBackupRestoreListener
implements BackupRestoreListener {
    private final Auditor auditor;
    private final UserImpl user;
    private final BackupFileInfo info;

    @Override
    public void onBackupRestoreProgress(BackupRestoreStage stage) {
    }

    @Override
    public void onBackupRestoreCompleted() {
        this.auditor.audit(null, this.user, CoreAuditEventAction.BACKUP_RESTORED, "middleware-core", new BackupRestoredAuditMessage(this.info.getFileName()));
    }

    @Override
    public void onTablePopulated(String s2) {
    }

    public AuditBackupRestoreListener(Auditor auditor, UserImpl user, BackupFileInfo info) {
        this.auditor = auditor;
        this.user = user;
        this.info = info;
    }
}

