/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class GroupsResponse {
    @JSONField(serialize=false)
    private final StreamGroup streamGroup;
    @JSONField(name="groups")
    private final Collection<GroupsResponse> groups;

    public GroupsResponse(@NotNull StreamGroup streamGroup, @NotNull Collection<GroupsResponse> groups2) {
        this.streamGroup = Objects.requireNonNull(streamGroup);
        this.groups = List.copyOf(groups2);
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

    public Collection<GroupsResponse> getGroups() {
        return this.groups;
    }
}

