/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.role;

import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.permission.Permission;
import java.util.Collection;

public interface Role
extends ClientId {
    public int getId();

    public String getName();

    default public String getDescription() {
        return "";
    }

    public Collection<String> getPermissions();

    public boolean hasPermission(Permission var1);

    public boolean hasAllPermissions(Collection<? extends Permission> var1);

    public boolean hasAnyPermission(Collection<? extends Permission> var1);
}

