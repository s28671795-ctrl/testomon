/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.streams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.model.stream.Resolution;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ResolutionDTO", description="Resolution class structure")
public class ResolutionDTO {
    @JSONField(name="width")
    @JsonProperty(value="width")
    @Schema(description="Width", example="1920")
    private int width;
    @JSONField(name="height")
    @JsonProperty(value="height")
    @Schema(description="Height", example="1080")
    private int height;

    @JSONField(serialize=false)
    @JsonIgnore
    @Schema(hidden=true)
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public ResolutionDTO(Resolution resolution) {
        this.width = resolution.getWidth();
        this.height = resolution.getHeight();
    }

    public static ResolutionDTO fromJSONDTO(String json) {
        return JSON.parseObject(json, ResolutionDTO.class);
    }

    public static Resolution fromJSON(String json) {
        ResolutionDTO dto = ResolutionDTO.fromJSONDTO(json);
        return new Resolution(dto.getWidth(), dto.getHeight());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @JsonProperty(value="width")
    public void setWidth(int width) {
        this.width = width;
    }

    @JsonProperty(value="height")
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ResolutionDTO)) {
            return false;
        }
        ResolutionDTO other = (ResolutionDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getWidth() != other.getWidth()) {
            return false;
        }
        return this.getHeight() == other.getHeight();
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResolutionDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getWidth();
        result = result * 59 + this.getHeight();
        return result;
    }

    public String toString() {
        return "ResolutionDTO(width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
    }

    public ResolutionDTO() {
    }

    public ResolutionDTO(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

