/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.Status;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GroupAddUpdateRemoveInfo {
    @JsonProperty(value="group")
    private String group;
    @JsonProperty(value="groupStatus")
    private Status groupStatus;
    @JsonProperty(value="members")
    private List<String> members;
    @JsonProperty(value="isRemove")
    private boolean isRemove;

    public GroupAddUpdateRemoveInfo(@Nonnull @JsonProperty(value="group") String group, @Nullable @JsonProperty(value="groupStatus") Status groupStatus, @Nullable @JsonProperty(value="members") List<String> members, @Nullable @JsonProperty(value="isRemove") boolean isRemove) {
        this.group = Objects.requireNonNull(group, "Group must be provided");
        this.groupStatus = groupStatus;
        this.members = members != null ? Collections.unmodifiableList(members) : null;
        this.isRemove = isRemove;
    }

    public String group() {
        return this.group;
    }

    public Status groupStatus() {
        return this.groupStatus;
    }

    public List<String> members() {
        return Collections.unmodifiableList(this.members == null ? new LinkedList() : this.members);
    }

    public boolean isRemove() {
        return this.isRemove;
    }
}

