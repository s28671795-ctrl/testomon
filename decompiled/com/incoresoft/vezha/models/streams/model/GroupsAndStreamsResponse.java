/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class GroupsAndStreamsResponse {
    @JSONField(serialize=false)
    private final StreamGroup streamGroup;
    @JSONField(name="groups")
    private final Collection<GroupsAndStreamsResponse> groups;
    @JSONField(name="streams")
    private final Collection<FullStreamDTO> streams;

    public GroupsAndStreamsResponse(@NotNull StreamGroup streamGroup, @NotNull Collection<GroupsAndStreamsResponse> groups2, @NotNull Collection<FullStreamDTO> streams) {
        this.streamGroup = Objects.requireNonNull(streamGroup);
        this.groups = List.copyOf(groups2);
        this.streams = List.copyOf(streams);
    }

    @JSONField(name="id")
    public int getId() {
        return this.streamGroup.getId();
    }

    @JSONField(name="parent_id")
    public int getParentId() {
        return this.streamGroup.getParentId();
    }

    @JSONField(name="name")
    public String getName() {
        return this.streamGroup.getName();
    }

    public Collection<GroupsAndStreamsResponse> getGroups() {
        return this.groups;
    }

    public Collection<FullStreamDTO> getStreams() {
        return this.streams;
    }
}

