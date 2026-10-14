/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.vms.rules.RuleMessage;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface Alarm {
    public UUID getSourceId();

    public UUID getServerId();

    public RuleMessage getMessage();

    public Set<Integer> getAllowedUserIds();

    default public Instant getTimestamp() {
        return null;
    }
}

