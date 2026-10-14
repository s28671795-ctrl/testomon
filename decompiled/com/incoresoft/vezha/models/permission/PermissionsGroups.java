/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.permission;

import com.incoresoft.middleware.model.permission.CorePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PermissionsGroups {
    Settings("SETTINGS", new CorePermissions[]{CorePermissions.ManageGeneralSettings, CorePermissions.ManageBranding, CorePermissions.ManageLicenses, CorePermissions.ManageSystemUpdatesAndPlugins, CorePermissions.ViewUsers, CorePermissions.ManageUsers, CorePermissions.ViewRoles, CorePermissions.ManageRoles, CorePermissions.EditNotificationSounds, CorePermissions.ViewStreams, CorePermissions.ManageStreams, CorePermissions.ViewServers, CorePermissions.ManageAnalyticsServers, CorePermissions.ViewLogs, CorePermissions.ManageEvents});

    private final String localeKey;
    private final CorePermissions[] permission;

    private PermissionsGroups(String localeKey, CorePermissions[] permissions) {
        this.localeKey = localeKey;
        this.permission = permissions;
    }

    public List<CorePermissions> getPermissions() {
        ArrayList<CorePermissions> list = new ArrayList<CorePermissions>();
        if (this.permission != null) {
            list.addAll(Arrays.asList(this.permission));
        }
        return list;
    }

    public String getLocaleKey() {
        return this.localeKey;
    }
}

