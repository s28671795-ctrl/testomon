/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name="ShortListDTO", description="License plates list class structure")
@JsonDeserialize(builder=IntegrationShortListDTOBuilder.class)
public final class IntegrationShortListDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="List ID", implementation=Integer.class, example="1")
    private final Integer id;
    @JSONField(name="name")
    @JsonProperty(value="name")
    @Schema(name="name", description="List name", implementation=String.class, example="White list")
    private final String name;
    @JsonProperty(value="enabled")
    @JSONField(name="enabled")
    @Schema(name="enabled", description="Boolean value that shows if list is enabled", implementation=Boolean.class, example="true")
    private final boolean enabled;
    @JSONField(name="comment")
    @JsonProperty(value="comment")
    @Schema(name="comment", description="List comment", implementation=String.class, example="Some comment")
    private final String comment;
    @JSONField(name="color")
    @JsonProperty(value="color")
    @Schema(name="color", description="List color", implementation=String.class, example="#ffffff")
    private final String color;
    @JSONField(name="analytics_ids")
    @JsonProperty(value="analytics_ids")
    @ArraySchema(schema=@Schema(name="analytics_ids", description="List of video analytics ids", implementation=Integer.class))
    private final Set<Integer> analyticsIds;
    @JSONField(name="show_popup_for_internal_notifications")
    @JsonProperty(value="show_popup_for_internal_notifications")
    @Schema(name="show_popup_for_internal_notifications", description="Show popup for internal notifications", implementation=Boolean.class)
    private final boolean showPopupForInternalNotifications;

    public static IntegrationShortListDTOBuilder builder() {
        return new IntegrationShortListDTOBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getComment() {
        return this.comment;
    }

    public String getColor() {
        return this.color;
    }

    public Set<Integer> getAnalyticsIds() {
        return this.analyticsIds;
    }

    public boolean isShowPopupForInternalNotifications() {
        return this.showPopupForInternalNotifications;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegrationShortListDTO)) {
            return false;
        }
        IntegrationShortListDTO other = (IntegrationShortListDTO)o;
        if (this.isEnabled() != other.isEnabled()) {
            return false;
        }
        if (this.isShowPopupForInternalNotifications() != other.isShowPopupForInternalNotifications()) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
            return false;
        }
        String this$color = this.getColor();
        String other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) {
            return false;
        }
        Set<Integer> this$analyticsIds = this.getAnalyticsIds();
        Set<Integer> other$analyticsIds = other.getAnalyticsIds();
        return !(this$analyticsIds == null ? other$analyticsIds != null : !((Object)this$analyticsIds).equals(other$analyticsIds));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        result = result * 59 + (this.isShowPopupForInternalNotifications() ? 79 : 97);
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        String $color = this.getColor();
        result = result * 59 + ($color == null ? 43 : $color.hashCode());
        Set<Integer> $analyticsIds = this.getAnalyticsIds();
        result = result * 59 + ($analyticsIds == null ? 43 : ((Object)$analyticsIds).hashCode());
        return result;
    }

    public String toString() {
        return "IntegrationShortListDTO(id=" + this.getId() + ", name=" + this.getName() + ", enabled=" + this.isEnabled() + ", comment=" + this.getComment() + ", color=" + this.getColor() + ", analyticsIds=" + String.valueOf(this.getAnalyticsIds()) + ", showPopupForInternalNotifications=" + this.isShowPopupForInternalNotifications() + ")";
    }

    public IntegrationShortListDTO(Integer id, String name, boolean enabled, String comment, String color, Set<Integer> analyticsIds, boolean showPopupForInternalNotifications) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.comment = comment;
        this.color = color;
        this.analyticsIds = analyticsIds;
        this.showPopupForInternalNotifications = showPopupForInternalNotifications;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class IntegrationShortListDTOBuilder {
        private Integer id;
        private String name;
        private boolean enabled;
        private String comment;
        private String color;
        private Set<Integer> analyticsIds;
        private boolean showPopupForInternalNotifications;

        IntegrationShortListDTOBuilder() {
        }

        @JsonProperty(value="id")
        public IntegrationShortListDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="name")
        public IntegrationShortListDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty(value="enabled")
        public IntegrationShortListDTOBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @JsonProperty(value="comment")
        public IntegrationShortListDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        @JsonProperty(value="color")
        public IntegrationShortListDTOBuilder color(String color) {
            this.color = color;
            return this;
        }

        @JsonProperty(value="analytics_ids")
        public IntegrationShortListDTOBuilder analyticsIds(Set<Integer> analyticsIds) {
            this.analyticsIds = analyticsIds;
            return this;
        }

        @JsonProperty(value="show_popup_for_internal_notifications")
        public IntegrationShortListDTOBuilder showPopupForInternalNotifications(boolean showPopupForInternalNotifications) {
            this.showPopupForInternalNotifications = showPopupForInternalNotifications;
            return this;
        }

        public IntegrationShortListDTO build() {
            return new IntegrationShortListDTO(this.id, this.name, this.enabled, this.comment, this.color, this.analyticsIds, this.showPopupForInternalNotifications);
        }

        public String toString() {
            return "IntegrationShortListDTO.IntegrationShortListDTOBuilder(id=" + this.id + ", name=" + this.name + ", enabled=" + this.enabled + ", comment=" + this.comment + ", color=" + this.color + ", analyticsIds=" + String.valueOf(this.analyticsIds) + ", showPopupForInternalNotifications=" + this.showPopupForInternalNotifications + ")";
        }
    }
}

