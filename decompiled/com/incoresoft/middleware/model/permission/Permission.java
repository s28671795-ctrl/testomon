/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.permission;

import io.javalin.security.RouteRole;
import java.util.Set;

public interface Permission
extends RouteRole {
    public String getId();

    public String getLocaleKey();

    public Set<String> getRequired();
}

