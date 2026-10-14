/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.strategies;

import java.util.Set;
import java.util.UUID;

public interface StreamDeletionStrategy {
    public void deleteStreams(Set<Integer> var1);

    public void deleteStreamsByUuids(Set<UUID> var1);
}

