/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.permissions;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.plugin.commons.vms.permissions.dto.PluginPermissionDTO;
import java.util.Set;

public record PluginPermission(String id, String localeKey, Set<String> required) implements Permission
{
    public PluginPermission(PluginPermissionDTO permissionDTO) {
        this(permissionDTO.id(), permissionDTO.localeKey(), permissionDTO.required());
    }

    @Override
    public String getId() {
        return this.id();
    }

    @Override
    public String getLocaleKey() {
        return this.localeKey();
    }

    @Override
    public Set<String> getRequired() {
        return this.required();
    }
}

