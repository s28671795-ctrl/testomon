/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.user;

import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.permission.Permission;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Objects;

public interface User
extends ClientId {
    public int getId();

    public String getEmail();

    public String getFullname();

    public int getRoleId();

    public boolean isSoundEnabled();

    public boolean hasPermission(Permission var1);

    public boolean hasAllPermissions(Collection<? extends Permission> var1);

    public boolean hasAnyPermission(Collection<? extends Permission> var1);

    public boolean isRoot();

    default public boolean hasAccessTo(ClientId item) {
        Objects.requireNonNull(item);
        return this.isRoot() || this.getClientId() == item.getClientId();
    }

    default public ZoneId getTimezone() {
        return ZoneId.systemDefault();
    }

    default public DayOfWeek getFirstDayOfWeek() {
        return DayOfWeek.MONDAY;
    }
}

