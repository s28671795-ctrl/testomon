/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.streams.validations.GroupIsParentToItself;
import com.incoresoft.vezha.dto.streams.validations.GroupNameMustBeUnique;
import com.incoresoft.vezha.dto.streams.validations.ParentForGroupMustExist;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@GroupIsParentToItself
@GroupNameMustBeUnique
@ParentForGroupMustExist
@Schema(name="StreamGroupDTO", description="Stream group class structure")
public final class StreamGroupDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Stream group Id", implementation=String.class, example="2")
    private int id;
    @JSONField(name="parent_id")
    @Schema(name="parent_id", description="Stream group parent(folder) Id", implementation=String.class, example="1")
    private final int parentId;
    @JSONField(name="name")
    @Schema(name="name", description="Stream group name", implementation=String.class, example="Group 1")
    @NotEmpty
    private final String name;

    public StreamGroupDTO(StreamGroup group) {
        this.id = group.getId();
        this.name = group.getName();
        this.parentId = group.getParentId();
    }

    public StreamGroup toGroup(int clientId) {
        return new StreamGroup(this.getId(), this.getParentId(), this.getName(), clientId);
    }

    public static StreamGroupDTO fromGroup(@NotNull StreamGroup streamGroup) {
        return new StreamGroupDTO(streamGroup.getId(), streamGroup.getParentId(), streamGroup.getName());
    }

    public int getId() {
        return this.id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamGroupDTO)) {
            return false;
        }
        StreamGroupDTO other = (StreamGroupDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getParentId() != other.getParentId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getParentId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "StreamGroupDTO(id=" + this.getId() + ", parentId=" + this.getParentId() + ", name=" + this.getName() + ")";
    }

    public StreamGroupDTO(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}

