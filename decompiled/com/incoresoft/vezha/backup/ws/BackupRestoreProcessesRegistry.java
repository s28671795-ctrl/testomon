/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws;

import com.google.inject.Inject;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.system.processes.ProcessIsAlreadyRunningException;

public class BackupRestoreProcessesRegistry {
    private static final String PROCESS_ID = "backup_restore";
    private final InterferingProcessesRegistry processesRegistry;

    public synchronized void registerOrThrowIfRegistered() {
        if (this.processesRegistry.isRunningAny()) {
            throw new ProcessIsAlreadyRunningException();
        }
        this.processesRegistry.registerOrThrowIfRegistered(PROCESS_ID);
    }

    public synchronized void unregister() {
        this.processesRegistry.unregister(PROCESS_ID);
    }

    @Inject
    public BackupRestoreProcessesRegistry(InterferingProcessesRegistry processesRegistry) {
        this.processesRegistry = processesRegistry;
    }
}

