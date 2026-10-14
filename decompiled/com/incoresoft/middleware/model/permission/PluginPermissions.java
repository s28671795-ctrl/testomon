/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.permission;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PermissionGroup;
import java.util.Collection;
import java.util.Collections;

public class PluginPermissions {
    private final Collection<PermissionGroup> groups;
    private final Collection<Permission> permissions;

    public PluginPermissions() {
        this.groups = Collections.emptyList();
        this.permissions = Collections.emptyList();
    }

    public PluginPermissions(Collection<PermissionGroup> groups2, Collection<Permission> permissions) {
        this.groups = Collections.unmodifiableCollection(groups2);
        this.permissions = Collections.unmodifiableCollection(permissions);
    }

    public Collection<PermissionGroup> getGroups() {
        return this.groups;
    }

    public Collection<Permission> getPermissions() {
        return this.permissions;
    }
}

