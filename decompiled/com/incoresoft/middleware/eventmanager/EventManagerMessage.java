/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import java.time.Instant;

public interface EventManagerMessage {
    default public int getVaId() {
        return -1;
    }

    default public Instant getTimestamp() {
        return Instant.now();
    }
}

