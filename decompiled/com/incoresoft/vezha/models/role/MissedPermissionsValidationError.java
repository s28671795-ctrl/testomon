/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role;

import com.incoresoft.middleware.model.permission.Permission;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class MissedPermissionsValidationError {
    private final Permission target;
    private final Set<Permission> missed;

    public MissedPermissionsValidationError(@NotNull Permission target, @NotNull Set<Permission> missed) {
        this.target = Objects.requireNonNull(target);
        this.missed = Objects.requireNonNull(missed);
    }

    public Permission getTarget() {
        return this.target;
    }

    public Set<Permission> getMissed() {
        return this.missed;
    }
}

