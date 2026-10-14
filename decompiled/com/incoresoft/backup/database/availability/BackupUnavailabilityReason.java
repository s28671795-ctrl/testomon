/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.availability;

import com.incoresoft.backup.database.exceptions.SystemIsUnavailableDuringBackupCreationException;
import com.incoresoft.backup.database.exceptions.SystemIsUnavailableDuringBackupRestoreException;
import com.incoresoft.dependencyinjection.exceptions.SystemIsUnavailableException;
import com.incoresoft.dependencyinjection.exceptions.UnavailabilityReason;
import java.util.function.Supplier;

public enum BackupUnavailabilityReason implements UnavailabilityReason
{
    BACKUP_CREATION(SystemIsUnavailableDuringBackupCreationException::new),
    BACKUP_RESTORE(SystemIsUnavailableDuringBackupRestoreException::new);

    private final Supplier<SystemIsUnavailableException> exceptionSupplier;

    @Override
    public Supplier<SystemIsUnavailableException> getExceptionSupplier() {
        return this.exceptionSupplier;
    }

    private BackupUnavailabilityReason(Supplier<SystemIsUnavailableException> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }
}

