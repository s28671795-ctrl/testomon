/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import com.incoresoft.middleware.model.permission.Permission;
import java.util.Collection;
import java.util.Collections;

public interface AccessibleView {
    default public Collection<? extends Permission> requiredPermissions() {
        return Collections.emptyList();
    }
}

