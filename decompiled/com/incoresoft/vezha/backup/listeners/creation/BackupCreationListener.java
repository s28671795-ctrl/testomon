/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.listeners.creation;

import com.incoresoft.backup.database.listeners.BackupDatabaseCreationListener;
import com.incoresoft.vezha.backup.BackupCreationStage;

public interface BackupCreationListener
extends BackupDatabaseCreationListener {
    public void onBackupCreationProgress(BackupCreationStage var1);

    @Override
    default public void onBackupDatabaseSchemaCreationStage() {
        this.onBackupCreationProgress(BackupCreationStage.DATABASE_SCHEMA_CREATION);
    }

    @Override
    default public void onBackupDatabasePopulationStage() {
        this.onBackupCreationProgress(BackupCreationStage.DATABASE_POPULATION);
    }

    public void onBackupCreationFailed(Exception var1);

    public void onBackupFileCreationCompleted();

    public void onBackupCreationCompleted(int var1);
}

