/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.model.user.User;
import java.util.Optional;
import java.util.UUID;

public interface RuleSource {
    public UUID getId();

    public String getName();

    default public boolean isAllowed(User user) {
        return true;
    }

    default public Optional<Integer> getCameraId() {
        return Optional.empty();
    }
}

