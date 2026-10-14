/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.permission;

import com.incoresoft.middleware.model.permission.Permission;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum CorePermissions implements Permission,
RouteRole
{
    Unknown("PERMISSION_UNKNOWN", new CorePermissions[0]),
    ViewStreams("PERMISSION_VIEW_STREAMS", new CorePermissions[0]),
    ManageStreams("PERMISSION_MANAGE_STREAMS", ViewStreams),
    ViewServers("PERMISSION_VIEW_SERVERS", new CorePermissions[0]),
    ManageAnalyticsServers("PERMISSION_MANAGE_ANALYTICS_SERVERS", ViewServers),
    ViewLogs("PERMISSION_VIEW_LOGS", new CorePermissions[0]),
    EditNotificationSounds("PERMISSION_EDIT_NOTIFICATION_SOUNDS", new CorePermissions[0]),
    ViewUsers("PERMISSION_VIEW_USERS", new CorePermissions[0]),
    ManageUsers("PERMISSION_MANAGE_USERS", ViewUsers),
    ViewRoles("PERMISSION_VIEW_ROLES", new CorePermissions[0]),
    ManageRoles("PERMISSION_MANAGE_ROLES", ViewRoles),
    ManageLicenses("PERMISSION_MANAGE_LICENSES", new CorePermissions[0]),
    ManageGeneralSettings("PERMISSION_MANAGE_GENERAL_SETTINGS", new CorePermissions[0]),
    ManageBranding("PERMISSION_MANAGE_BRANDING", ManageGeneralSettings),
    ManageEvents("PERMISSION_MANAGE_EVENTS", new CorePermissions[0]),
    StartAnalytics("PERMISSION_START_ANALYTICS", new CorePermissions[0]),
    StopAnalytics("PERMISSION_STOP_ANALYTICS", new CorePermissions[0]),
    EditAnalytics("PERMISSION_EDIT_ANALYTICS", new CorePermissions[0]),
    ViewAnalyticsLive("PERMISSION_VIEW_ANALYTICS_LIVE", new CorePermissions[0]),
    ViewAnalyticsEvents("PERMISSION_VIEW_ANALYTICS_EVENTS", new CorePermissions[0]),
    ManageSystemUpdatesAndPlugins("PERMISSION_MANAGE_SYSTEM_UPDATES_AND_PLUGINS", new CorePermissions[0]);

    private final String localeKey;
    private final Set<Permission> required;

    private CorePermissions(String localeKey, CorePermissions ... required) {
        this.localeKey = localeKey;
        this.required = Arrays.stream(required).collect(Collectors.toSet());
    }

    @Override
    public String getId() {
        return this.name();
    }

    @Override
    public Set<String> getRequired() {
        return this.required.stream().map(Permission::getId).collect(Collectors.toSet());
    }

    @Override
    public String getLocaleKey() {
        return this.localeKey;
    }
}

