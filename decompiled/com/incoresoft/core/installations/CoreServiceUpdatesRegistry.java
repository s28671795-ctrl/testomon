/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core.installations;

import com.google.inject.Inject;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.services.exceptions.ServiceIsBeingUpdatedException;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.system.processes.ProcessIsAlreadyRunningException;

public class CoreServiceUpdatesRegistry {
    private final InterferingProcessesRegistry processesRegistry;
    private final CoreService coreService;

    public void registerOrThrowIfRegistered() {
        try {
            String id = this.getSystemComponentId();
            this.processesRegistry.registerOrThrowIfRegistered(id);
        }
        catch (ProcessIsAlreadyRunningException e) {
            throw new ServiceIsBeingUpdatedException(this.coreService.getId());
        }
    }

    public void unregister() {
        String id = this.getSystemComponentId();
        this.processesRegistry.unregister(id);
    }

    public boolean isBeingUpdated() {
        return this.processesRegistry.isRunning(this.getSystemComponentId());
    }

    private String getSystemComponentId() {
        return String.format("core__update__%s", this.coreService.getId());
    }

    @Inject
    public CoreServiceUpdatesRegistry(InterferingProcessesRegistry processesRegistry, CoreService coreService) {
        this.processesRegistry = processesRegistry;
        this.coreService = coreService;
    }
}

