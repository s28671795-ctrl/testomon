/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.permissions;

import com.incoresoft.middleware.model.permission.Permission;
import java.util.Set;

public class UnobtainablePermission
implements Permission {
    @Override
    public String getId() {
        return "unobtainable";
    }

    @Override
    public String getLocaleKey() {
        return "unobtainable";
    }

    @Override
    public Set<String> getRequired() {
        return Set.of();
    }
}

