/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.roles;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.model.role.Role;
import java.util.Collection;

public interface VmsPluginRolesManager {
    public Collection<? extends Permission> getPermissions();

    public Role get(Integer var1);

    public Collection<? extends Role> getAll();

    public void register(String var1, PluginPermissions var2);

    public void unregister(String var1, PluginPermissions var2);
}

