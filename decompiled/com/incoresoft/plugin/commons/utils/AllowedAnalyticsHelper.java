/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.params.AnalyticsDetectionParams;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AllowedAnalyticsHelper {
    private final String pluginName;
    private final Permission viewAnalyticsPermission;
    private final AnalyticsManager analyticsManager;

    public AllowedAnalyticsHelper(@NotNull String pluginName, @NotNull AnalyticsManager analyticsManager, @NotNull Permission viewAnalyticsPermission) {
        this.pluginName = pluginName;
        this.analyticsManager = analyticsManager;
        this.viewAnalyticsPermission = viewAnalyticsPermission;
    }

    public Set<Integer> getAnalyticsAllowedForEvents(@NotNull User user) {
        return this.getAnalyticsAllowedForEvents((Set<Integer>)null, user);
    }

    public Set<Integer> getAnalyticsAllowedForEvents(@Nullable AnalyticsDetectionParams params, @NotNull User user) {
        return this.getAnalyticsAllowedForEvents((Set<Integer>)Optional.ofNullable(params).map(AnalyticsDetectionParams::getAnalyticsIds).orElse(null), user);
    }

    public Set<Integer> getAnalyticsAllowedForEvents(@Nullable Set<Integer> selectedAnalyticsIds, @NotNull User user) {
        return this.getAllowedAnalytics(selectedAnalyticsIds, user).stream().filter(it -> it.getRestrictions().isAllowed(user, CorePermissions.ViewAnalyticsEvents)).map(Analytics::getId).collect(Collectors.toSet());
    }

    public Set<Integer> getAllowedAnalyticsIds(User user) {
        return this.getAllowedAnalyticsIds(null, user);
    }

    public Set<Integer> getAllowedAnalyticsIds(@Nullable Set<Integer> selectedAnalyticsIds, @NotNull User user) {
        return this.getAllowedAnalytics(selectedAnalyticsIds, user).stream().map(Analytics::getId).collect(Collectors.toSet());
    }

    public List<Analytics> getAllowedAnalytics(@NotNull User user) {
        return this.getAllowedAnalytics(null, user);
    }

    public List<Analytics> getAllowedAnalytics(@Nullable Set<Integer> selectedAnalyticsIds, @NotNull User user) {
        if (!user.hasPermission(this.viewAnalyticsPermission)) {
            return Collections.emptyList();
        }
        return CollectionUtils.union(this.analyticsManager.getAll(user), this.analyticsManager.getAnalyticsArchive().getAll(user)).stream().filter(analytics -> Objects.equals(analytics.getPluginName(), this.pluginName)).filter(it -> selectedAnalyticsIds == null || selectedAnalyticsIds.contains(it.getId())).toList();
    }
}

