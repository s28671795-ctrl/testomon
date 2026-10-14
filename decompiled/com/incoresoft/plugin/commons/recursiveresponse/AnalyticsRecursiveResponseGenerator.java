/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.recursiveresponse;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.AnalyticsGroupsManager;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.DeletedStream;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.analytics.dto.GroupsAndAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.plugin.commons.params.AnalyticsParams;
import com.incoresoft.plugin.commons.recursiveresponse.RecursiveResponseGenerator;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsRecursiveResponseGenerator
extends RecursiveResponseGenerator<AnalyticsGroup, Analytics, GroupsAndAnalyticsDTO> {
    public AnalyticsRecursiveResponseGenerator(@NotNull User user, @NotNull Predicate<Analytics> analyticsPredicate, @NotNull String pluginId, @NotNull boolean showDeleted, @NotNull StreamsManager streamsManager, @NotNull AnalyticsManager analyticsManager, @NotNull AnalyticsGroupsManager analyticsGroupsManager, @NotNull VideoFileProgressTracker progressTracker, @NotNull AnalyticsParams analyticsParams) {
        super(analyticsGroupsManager.getRootGroup(), AnalyticsRecursiveResponseGenerator.nonEmptyGroupsMap(analyticsParams.getMatchingText(), () -> analyticsGroupsManager.getAll(pluginId, user), AnalyticsRecursiveResponseGenerator.matchingAnalytics(analyticsPredicate, showDeleted, analyticsManager), analyticsGroupsManager.getRootGroup()), AnalyticsRecursiveResponseGenerator.matchingAnalytics(analyticsPredicate, showDeleted, analyticsManager).stream().collect(Collectors.groupingBy(Analytics::getParentId)), new AnalyticsResponseInstantiator(user, progressTracker, streamsManager), Analytics::getName, SortFields.ID, SortOrders.DESC);
    }

    public AnalyticsRecursiveResponseGenerator(@NotNull User user, @NotNull Predicate<Analytics> analyticsPredicate, @NotNull boolean showDeleted, @NotNull StreamsManager streamsManager, @NotNull AnalyticsManager analyticsManager, @NotNull AnalyticsGroupsManager analyticsGroupsManager, @NotNull VideoFileProgressTracker progressTracker) {
        super(analyticsGroupsManager.getRootGroup(), AnalyticsRecursiveResponseGenerator.nonEmptyGroupsMap("", analyticsGroupsManager::getAll, AnalyticsRecursiveResponseGenerator.matchingAnalytics(analyticsPredicate, showDeleted, analyticsManager), analyticsGroupsManager.getRootGroup()), AnalyticsRecursiveResponseGenerator.matchingAnalytics(analyticsPredicate, showDeleted, analyticsManager).stream().collect(Collectors.groupingBy(Analytics::getParentId)), new AnalyticsResponseInstantiator(user, progressTracker, streamsManager), Analytics::getName, SortFields.ID, SortOrders.DESC);
    }

    private static Map<Integer, List<AnalyticsGroup>> nonEmptyGroupsMap(String matchingText, Supplier<List<AnalyticsGroup>> groupsSupplier, List<Analytics> analytics, AnalyticsGroup rootGroup) {
        Map groupMap = groupsSupplier.get().stream().collect(Collectors.toMap(AnalyticsGroup::getId, Function.identity()));
        if (StringUtils.isEmpty(matchingText)) {
            return groupMap.values().stream().collect(Collectors.groupingBy(AnalyticsGroup::getParentId));
        }
        HashSet<Integer> nonEmptyGroupIds = new HashSet<Integer>();
        for (Analytics a : analytics) {
            int parentId = a.getParentId();
            while (parentId != rootGroup.getId()) {
                AnalyticsGroup group2 = (AnalyticsGroup)groupMap.get(parentId);
                nonEmptyGroupIds.add(parentId);
                parentId = group2.getParentId();
            }
        }
        return groupMap.values().stream().filter(group -> nonEmptyGroupIds.contains(group.getId())).collect(Collectors.groupingBy(AnalyticsGroup::getParentId));
    }

    private static List<Analytics> matchingAnalytics(Predicate<Analytics> analyticsPredicate, boolean showDeleted, AnalyticsManager analyticsManager) {
        return analyticsManager.getAll(showDeleted).stream().filter(analyticsPredicate).toList();
    }

    private static class AnalyticsResponseInstantiator
    implements RecursiveResponseGenerator.ResponseInstantiator<AnalyticsGroup, Analytics, GroupsAndAnalyticsDTO> {
        private final User user;
        private final VideoFileProgressTracker progressTracker;
        private final Map<UUID, Stream> streamsMap;

        public AnalyticsResponseInstantiator(User user, VideoFileProgressTracker progressTracker, StreamsManager streamsManager) {
            this.user = user;
            this.progressTracker = progressTracker;
            this.streamsMap = streamsManager.getAll().stream().collect(Collectors.toMap(Stream::getUUID, Function.identity()));
        }

        @Override
        public GroupsAndAnalyticsDTO instantiate(AnalyticsGroup analyticsGroup, List<GroupsAndAnalyticsDTO> groups2, List<Analytics> analytics) {
            return new GroupsAndAnalyticsDTO(analyticsGroup, groups2, analytics.stream().map(singleAnalytics -> new AnalyticsDTO(this.user, (Analytics)singleAnalytics, this.streamsMap.getOrDefault(singleAnalytics.getStreamUUID(), new DeletedStream(singleAnalytics.getStreamId())), this.progressTracker.getProgress(singleAnalytics.getId()))).toList());
        }
    }
}

