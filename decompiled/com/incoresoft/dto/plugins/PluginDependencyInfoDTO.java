/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugins.models.PluginDependencyInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="PluginDependencyInfoDTO", description="Plugin dependency info class structure")
public final class PluginDependencyInfoDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Plugin id", example="com.incoresoft.plugin.crowds", implementation=String.class)
    private final String id;
    @JsonProperty(value="version")
    @JSONField(name="version")
    @Schema(name="version", description="Plugin version", example="1.0.0", implementation=String.class)
    private final int majorVersion;

    public PluginDependencyInfoDTO(PluginDependencyInfo entity) {
        this.id = entity.getId();
        this.majorVersion = entity.getMajorVersion();
    }

    public String getId() {
        return this.id;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginDependencyInfoDTO)) {
            return false;
        }
        PluginDependencyInfoDTO other = (PluginDependencyInfoDTO)o;
        if (this.getMajorVersion() != other.getMajorVersion()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getMajorVersion();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public String toString() {
        return "PluginDependencyInfoDTO(id=" + this.getId() + ", majorVersion=" + this.getMajorVersion() + ")";
    }

    public PluginDependencyInfoDTO(String id, int majorVersion) {
        this.id = id;
        this.majorVersion = majorVersion;
    }
}

