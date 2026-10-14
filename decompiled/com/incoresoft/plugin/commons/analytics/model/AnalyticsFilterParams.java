/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.user.User;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class AnalyticsFilterParams {
    private final AnalyticsManager analyticsManager;
    private final User user;

    public AnalyticsFilterParams(@NotNull AnalyticsManager analyticsManager, @NotNull User user) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.user = Objects.requireNonNull(user);
    }

    public boolean allAnalyticsAllowed() {
        return this.user.isRoot();
    }

    public Set<Integer> getAllowedAnalytics(String pluginName) {
        Predicate<Analytics> filter2 = analytics -> Objects.equals(analytics.getPluginName(), pluginName);
        if (!this.allAnalyticsAllowed()) {
            filter2 = filter2.and(analytics -> analytics.getRestrictions().isAllowed(this.user, "ViewAnalyticsEvents"));
        }
        return this.analyticsManager.getAll(filter2).stream().map(Analytics::getId).collect(Collectors.toUnmodifiableSet());
    }
}

