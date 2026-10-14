/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.itemaccess;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.user.User;

public interface ItemAccessRestrictions {
    public boolean isAllowed(User var1, Permission var2);

    public boolean isAllowed(User var1, String var2);
}

