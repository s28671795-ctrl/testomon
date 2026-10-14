/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictionsConverter;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class VezhaAnalyticsRestrictionsConverter
extends AnalyticsRestrictionsConverter {
    @Inject
    public VezhaAnalyticsRestrictionsConverter(@NotNull RolesManager roleManager, @NotNull UsersManager usersManager) {
        super(roleManager, usersManager, Set.of(CorePermissions.StartAnalytics, CorePermissions.StopAnalytics, CorePermissions.EditAnalytics, CorePermissions.ViewAnalyticsLive, CorePermissions.ViewAnalyticsEvents));
    }
}

