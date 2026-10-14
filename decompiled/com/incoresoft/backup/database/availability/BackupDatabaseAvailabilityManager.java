/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.availability;

import com.google.inject.Inject;
import com.incoresoft.backup.database.availability.BackupUnavailabilityReason;
import com.incoresoft.dependencyinjection.jooq.ExceptionThrowingDataSourceConnectionProvider;
import com.incoresoft.dependencyinjection.jooq.LockingDataSourceConnectionProvider;

public class BackupDatabaseAvailabilityManager {
    private final ExceptionThrowingDataSourceConnectionProvider exceptionThrowingDataSourceConnectionProvider;
    private final LockingDataSourceConnectionProvider lockingDataSourceConnectionProvider;

    public void setUnavailableDuringBackupCreation() {
        this.lockingDataSourceConnectionProvider.setAvailable(false);
    }

    public void setUnavailableDuringBackupRestore() {
        this.exceptionThrowingDataSourceConnectionProvider.setUnavailable(BackupUnavailabilityReason.BACKUP_CREATION);
        this.lockingDataSourceConnectionProvider.setAvailable(false);
    }

    public void setAvailable() {
        this.lockingDataSourceConnectionProvider.setAvailable(true);
        this.exceptionThrowingDataSourceConnectionProvider.setAvailable();
    }

    @Inject
    public BackupDatabaseAvailabilityManager(ExceptionThrowingDataSourceConnectionProvider exceptionThrowingDataSourceConnectionProvider, LockingDataSourceConnectionProvider lockingDataSourceConnectionProvider) {
        this.exceptionThrowingDataSourceConnectionProvider = exceptionThrowingDataSourceConnectionProvider;
        this.lockingDataSourceConnectionProvider = lockingDataSourceConnectionProvider;
    }
}

