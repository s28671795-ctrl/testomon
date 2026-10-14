/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.role;

import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import java.util.Collection;

public interface RolesManager
extends ItemManager<Role> {
    public Collection<? extends Permission> getPermissions();
}

