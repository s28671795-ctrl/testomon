/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast;

import com.incoresoft.plugins.exceptions.PluginIsBeingInstalledException;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.system.processes.ProcessIsAlreadyRunningException;

public class PluginInstallationsRegistry {
    private final InterferingProcessesRegistry processesRegistry;
    private final PluginType pluginType;

    public void registerOrThrowIfRegistered(String pluginId) {
        try {
            String id = this.getSystemComponentId(pluginId);
            this.processesRegistry.registerOrThrowIfRegistered(id);
        }
        catch (ProcessIsAlreadyRunningException e) {
            throw new PluginIsBeingInstalledException(pluginId);
        }
    }

    public void unregister(String pluginId) {
        String id = this.getSystemComponentId(pluginId);
        this.processesRegistry.unregister(id);
    }

    private String getSystemComponentId(String pluginId) {
        return String.format("%s__installation__%s", new Object[]{this.pluginType, pluginId});
    }

    public PluginInstallationsRegistry(InterferingProcessesRegistry processesRegistry, PluginType pluginType) {
        this.processesRegistry = processesRegistry;
        this.pluginType = pluginType;
    }
}

