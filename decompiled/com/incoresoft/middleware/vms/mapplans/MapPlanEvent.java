/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import java.time.Instant;
import java.util.UUID;

public interface MapPlanEvent {
    public UUID getSourceId();

    public Instant getTimestamp();
}

