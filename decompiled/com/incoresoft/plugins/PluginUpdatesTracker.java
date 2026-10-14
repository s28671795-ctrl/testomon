/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;

public class PluginUpdatesTracker {
    @GuardedBy(value="this")
    private final Set<String> markedAsUpdated;
    private final int totalSize;

    public PluginUpdatesTracker(int totalSize) {
        this.totalSize = totalSize;
        this.markedAsUpdated = new HashSet<String>();
    }

    public synchronized void trackUpdateFinished(String pluginId) {
        this.markedAsUpdated.add(pluginId);
    }

    public synchronized boolean areAllUpdatesFinished() {
        return this.markedAsUpdated.size() == this.totalSize;
    }
}

