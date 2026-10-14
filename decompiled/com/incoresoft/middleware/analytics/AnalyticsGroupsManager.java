/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupMapping;
import com.incoresoft.middleware.model.user.User;
import java.util.List;
import java.util.Optional;

public interface AnalyticsGroupsManager {
    public AnalyticsGroup getRootGroup();

    @Deprecated
    default public List<AnalyticsGroupMapping> getAnalyticsMappings(boolean isShowDeleted) {
        return List.of();
    }

    @Deprecated
    default public void addAnalyticsToGroup(int analyticsId, int groupId) {
    }

    @Deprecated
    default public void moveAnalyticsToGroup(int analyticsId, int groupId) {
    }

    public boolean exists(int var1);

    public List<AnalyticsGroup> getAll();

    public List<AnalyticsGroup> getAll(String var1, User var2);

    public boolean isGroupParentToItself(AnalyticsGroup var1);

    public AnalyticsGroup add(AnalyticsGroup var1);

    public Optional<AnalyticsGroup> get(int var1);

    public AnalyticsGroup update(AnalyticsGroup var1);

    public void delete(int var1);

    @Deprecated
    default public void deleteMappingByAnalyticsId(int analyticsId) {
    }

    public void startByGroup(int var1);

    public void stopByGroup(int var1);
}

