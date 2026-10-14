/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.itemaccess;

import com.incoresoft.middleware.model.itemaccess.ItemAccessRestrictions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.user.User;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface RoleUserRestrictions
extends ItemAccessRestrictions {
    public Optional<User> itemCreator();

    public Set<Permission> eligiblePermissions();

    public Map<Role, Set<Permission>> grantedRoleRestrictions();

    public Map<User, Set<Permission>> grantedUserRestrictions();

    public Set<Permission> grantedDefaultRestrictions();
}

