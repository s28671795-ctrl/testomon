/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="BoundingBoxDTO", description="Bounding box with UUID class structure")
public final class BoundingBoxDTO {
    @JSONField(name="box")
    @JsonProperty(value="box")
    @Schema(name="box", description="License plate bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="uuid")
    @JsonProperty(value="uuid")
    @Schema(name="uuid", description="License plate UUID", implementation=String.class, example="1dee2937-f160-4d00-bc82-61454dd99997")
    private final String uuid;

    public double[] getBox() {
        return this.box;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BoundingBoxDTO)) {
            return false;
        }
        BoundingBoxDTO other = (BoundingBoxDTO)o;
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        return !(this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Arrays.hashCode(this.getBox());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        return result;
    }

    public String toString() {
        return "BoundingBoxDTO(box=" + Arrays.toString(this.getBox()) + ", uuid=" + this.getUuid() + ")";
    }

    public BoundingBoxDTO(double[] box, String uuid) {
        this.box = box;
        this.uuid = uuid;
    }
}

