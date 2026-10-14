/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.analytics.dto.GroupsAndAnalyticsDTO;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@Schema(name="GroupedAnalyticsResponseFormatter", description="GroupedAnalyticsResponseFormatter description")
public class GroupedAnalyticsResponseFormatter {
    @Schema(hidden=true)
    private final User user;
    private final StreamsManager streamsManager;
    private final VideoFileProgressTracker progressTracker;
    private final Map<Integer, List<Analytics>> analyticsParents;
    private final Map<Integer, List<Folder>> folderParents;

    public GroupedAnalyticsResponseFormatter(@NotNull User user, @NotNull StreamsManager streamsManager, @NotNull VideoFileProgressTracker progressTracker, @NotNull List<Folder> folders, @NotNull List<Analytics> analytics) {
        this.user = Objects.requireNonNull(user);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.progressTracker = Objects.requireNonNull(progressTracker);
        this.analyticsParents = this.parentToStreams(analytics);
        this.folderParents = GroupedAnalyticsResponseFormatter.parentToGroups(folders);
    }

    private Map<Integer, List<Analytics>> parentToStreams(List<Analytics> allAnalytics) {
        HashMap<Integer, List> parentToStreamMap = new HashMap<Integer, List>();
        for (Analytics analytics : allAnalytics) {
            parentToStreamMap.computeIfAbsent(((Stream)this.streamsManager.get(analytics.getStreamId())).getFolderId(), it -> new LinkedList()).add(analytics);
        }
        return Collections.unmodifiableMap(parentToStreamMap);
    }

    private static Map<Integer, List<Folder>> parentToGroups(List<Folder> allFolders) {
        HashMap<Integer, List> parentToGroupMap = new HashMap<Integer, List>();
        for (Folder group : allFolders) {
            parentToGroupMap.computeIfAbsent(group.getParent() != null ? group.getParent().getId() : -1, it -> new LinkedList()).add(group);
        }
        return Collections.unmodifiableMap(parentToGroupMap);
    }

    public GroupsAndAnalyticsDTO get() {
        return this.get(this.streamsManager.getStreamFolder(0));
    }

    public GroupsAndAnalyticsDTO get(Folder group) {
        List streamGroups = this.folderParents.getOrDefault(group.getId(), Collections.emptyList());
        List<GroupsAndAnalyticsDTO> groups2 = streamGroups.stream().map(this::get).filter(GroupsAndAnalyticsDTO::notEmpty).collect(Collectors.toUnmodifiableList());
        List analytics = this.analyticsParents.getOrDefault(group.getId(), Collections.emptyList());
        List<AnalyticsDTO> analyticsDTOS = analytics.stream().map(it -> new AnalyticsDTO(this.user, (Analytics)it, (Stream)this.streamsManager.get(it.getStreamId()), this.progressTracker.getProgress(it.getId()))).collect(Collectors.toUnmodifiableList());
        return new GroupsAndAnalyticsDTO(group, groups2, analyticsDTOS);
    }
}

