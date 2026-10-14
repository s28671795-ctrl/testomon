/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.plugin.commons.analytics.dto.validations.AnalyticsGroupMustNotBeParentToItself;
import com.incoresoft.plugin.commons.analytics.dto.validations.AnalyticsGroupNameMustBeUnique;
import com.incoresoft.plugin.commons.analytics.dto.validations.AnalyticsParentMustExist;
import com.incoresoft.plugin.commons.analytics.dto.validations.GroupAndParentMustNotBeFromDifferentPlugins;
import com.incoresoft.plugin.commons.analytics.dto.validations.ParentIdValidatable;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

@AnalyticsGroupNameMustBeUnique
@AnalyticsParentMustExist
@AnalyticsGroupMustNotBeParentToItself
@GroupAndParentMustNotBeFromDifferentPlugins
public class AnalyticsGroupDTO
implements ParentIdValidatable {
    @Schema(hidden=true)
    @JsonProperty(value="id")
    @JSONField(name="id")
    private Integer id;
    @Schema(name="name", description="Group name", implementation=String.class)
    @JsonProperty(value="name")
    @JSONField(name="name")
    @NotEmpty
    private String name;
    @Schema(name="plugin_name", description="Plugin name", implementation=String.class)
    @JsonProperty(value="plugin_name")
    @JSONField(name="plugin_name")
    @NotEmpty
    private String pluginName;
    @Schema(name="parent_id", description="Parent group id", example="1", implementation=Integer.class)
    @JsonProperty(value="parent_id")
    @JSONField(name="parent_id")
    private int parentId;
    @Schema(name="user_id", description="User id", example="1", implementation=Integer.class)
    @JsonProperty(value="user_id")
    @JsonIgnore
    @JSONField(name="user_id", serialize=false)
    private Integer userId;

    public AnalyticsGroupDTO(AnalyticsGroup group) {
        this.id = group.getId();
        this.name = group.getName();
        this.pluginName = group.getPluginName();
        this.parentId = group.getParentId();
        this.userId = null;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    @Override
    public int getParentId() {
        return this.parentId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    @JsonProperty(value="id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty(value="name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="plugin_name")
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @JsonProperty(value="parent_id")
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @JsonProperty(value="user_id")
    @JsonIgnore
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsGroupDTO)) {
            return false;
        }
        AnalyticsGroupDTO other = (AnalyticsGroupDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getParentId() != other.getParentId()) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$userId = this.getUserId();
        Integer other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !((Object)this$userId).equals(other$userId)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$pluginName = this.getPluginName();
        String other$pluginName = other.getPluginName();
        return !(this$pluginName == null ? other$pluginName != null : !this$pluginName.equals(other$pluginName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsGroupDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getParentId();
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : ((Object)$userId).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $pluginName = this.getPluginName();
        result = result * 59 + ($pluginName == null ? 43 : $pluginName.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsGroupDTO(id=" + this.getId() + ", name=" + this.getName() + ", pluginName=" + this.getPluginName() + ", parentId=" + this.getParentId() + ", userId=" + this.getUserId() + ")";
    }

    public AnalyticsGroupDTO() {
    }

    public static final class Fields {
        public static final String id = "id";
        public static final String name = "name";
        public static final String pluginName = "pluginName";
        public static final String parentId = "parentId";
        public static final String userId = "userId";
    }
}

