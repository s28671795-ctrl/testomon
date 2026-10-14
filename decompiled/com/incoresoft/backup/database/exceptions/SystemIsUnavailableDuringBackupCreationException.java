/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.exceptions;

import com.incoresoft.dependencyinjection.exceptions.SystemIsUnavailableException;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.middleware.http.errors.Error;

public class SystemIsUnavailableDuringBackupCreationException
extends SystemIsUnavailableException {
    @Override
    public Error getError() {
        return CoreErrors.SYSTEM_NOT_AVAILABLE_DURING_BACKUP_CREATION;
    }
}

