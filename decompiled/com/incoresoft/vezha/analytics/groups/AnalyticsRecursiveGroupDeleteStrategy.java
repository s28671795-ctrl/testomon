/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.groups;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.vezha.database.AnalyticsGroupsRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsRecursiveGroupDeleteStrategy {
    private final AnalyticsGroupsRepository groupRepository;
    private final AnalyticsManager analyticsManager;

    public void delete(int groupId) {
        Map<Integer, List<AnalyticsGroup>> parentIdGroupsMap = this.groupRepository.getAll().stream().collect(Collectors.groupingBy(AnalyticsGroup::getParentId));
        Map<Integer, List<Analytics>> parentIdItemsMap = this.analyticsManager.getAll().stream().collect(Collectors.groupingBy(Analytics::getParentId, Collectors.toList()));
        this.delete(groupId, parentIdGroupsMap, parentIdItemsMap);
    }

    private void delete(int id, Map<Integer, List<AnalyticsGroup>> parentIdGroupsMap, Map<Integer, List<Analytics>> parentIdItemsMap) {
        if (parentIdItemsMap.containsKey(id)) {
            parentIdItemsMap.get(id).forEach(item -> this.analyticsManager.delete(item.getId()));
        }
        if (parentIdGroupsMap.containsKey(id)) {
            parentIdGroupsMap.get(id).forEach(group -> this.delete(group.getId(), parentIdGroupsMap, parentIdItemsMap));
        }
        this.groupRepository.delete(id);
    }

    public AnalyticsRecursiveGroupDeleteStrategy(AnalyticsGroupsRepository groupRepository, AnalyticsManager analyticsManager) {
        this.groupRepository = groupRepository;
        this.analyticsManager = analyticsManager;
    }
}

