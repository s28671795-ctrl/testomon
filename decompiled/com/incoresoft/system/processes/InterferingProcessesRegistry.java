/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.processes;

import com.incoresoft.system.processes.ProcessIsAlreadyRunningException;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;

public class InterferingProcessesRegistry {
    @GuardedBy(value="this")
    private final Set<String> runningProcesses = new HashSet<String>();

    public synchronized void registerOrThrowIfRegistered(String id) {
        if (this.runningProcesses.contains(id)) {
            throw new ProcessIsAlreadyRunningException();
        }
        this.runningProcesses.add(id);
    }

    public synchronized void unregister(String id) {
        this.runningProcesses.remove(id);
    }

    public synchronized boolean isRunning(String id) {
        return this.runningProcesses.contains(id);
    }

    public synchronized boolean isRunningAny() {
        return !this.runningProcesses.isEmpty();
    }
}

