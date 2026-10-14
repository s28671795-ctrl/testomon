/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.model.folders.Folder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@Schema(name="GroupsAndAnalyticsDTO", description="Groups and analytics class structure")
public class GroupsAndAnalyticsDTO {
    @JSONField(serialize=false)
    @Schema(hidden=true)
    private final Folder folder;
    @JSONField(name="plugin_name")
    @Schema(name="plugin_name", description="Plugin name", implementation=String.class, example="alpr")
    private final String pluginName;
    @JSONField(name="groups")
    @ArraySchema(arraySchema=@Schema(name="groups", description="Analytics grouped by folders"), schema=@Schema(implementation=GroupsAndAnalyticsDTO.class))
    private final Collection<GroupsAndAnalyticsDTO> groups;
    @JSONField(name="analytics")
    @ArraySchema(arraySchema=@Schema(name="analytics", description="Analytics in root folder (without folder ownership / folder_id is 0)"), schema=@Schema(implementation=AnalyticsDTO.class))
    private final Collection<AnalyticsDTO> analytics;

    public GroupsAndAnalyticsDTO(@NotNull Folder folder, @NotNull Collection<GroupsAndAnalyticsDTO> groups2, @NotNull Collection<AnalyticsDTO> analytics) {
        this.folder = Objects.requireNonNull(folder);
        this.groups = List.copyOf(groups2);
        this.analytics = List.copyOf(analytics);
        this.pluginName = "";
    }

    public GroupsAndAnalyticsDTO(@NotNull AnalyticsGroup folder, @NotNull Collection<GroupsAndAnalyticsDTO> groups2, @NotNull Collection<AnalyticsDTO> analytics) {
        this.folder = Objects.requireNonNull(folder);
        this.groups = List.copyOf(groups2);
        this.analytics = List.copyOf(analytics);
        this.pluginName = folder.getPluginName();
    }

    @JSONField(name="id")
    @Schema(name="id", description="Folder Id", implementation=Integer.class, example="0")
    public int getId() {
        return this.folder.getId();
    }

    @JSONField(name="parent_id")
    @Schema(name="parent_id", description="Parent Id", implementation=Integer.class, example="null", nullable=true)
    public Integer getParentId() {
        return this.folder.getParent() != null ? Integer.valueOf(this.folder.getParent().getId()) : null;
    }

    @JSONField(name="name")
    @Schema(name="name", description="Name of the root location of folders and analytics ", implementation=String.class, example="root")
    public String getName() {
        return this.folder.getName();
    }

    @JSONField(serialize=false)
    @Schema(hidden=true)
    public boolean notEmpty() {
        return !this.analytics.isEmpty() || this.groups.stream().anyMatch(GroupsAndAnalyticsDTO::notEmpty);
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public Collection<GroupsAndAnalyticsDTO> getGroups() {
        return this.groups;
    }

    public Collection<AnalyticsDTO> getAnalytics() {
        return this.analytics;
    }
}

