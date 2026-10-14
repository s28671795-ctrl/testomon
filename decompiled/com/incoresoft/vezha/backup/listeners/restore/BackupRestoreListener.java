/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.restore;

import com.incoresoft.backup.database.listeners.BackupDatabaseRestoreListener;
import com.incoresoft.vezha.backup.BackupRestoreStage;

public interface BackupRestoreListener
extends BackupDatabaseRestoreListener {
    public void onBackupRestoreProgress(BackupRestoreStage var1);

    @Override
    default public void onDatabaseDataDeletingStage() {
        this.onBackupRestoreProgress(BackupRestoreStage.DATABASE_DATA_DELETING);
    }

    @Override
    default public void onDatabasePopulationStage() {
        this.onBackupRestoreProgress(BackupRestoreStage.DATABASE_POPULATION);
    }

    public void onBackupRestoreCompleted();
}

