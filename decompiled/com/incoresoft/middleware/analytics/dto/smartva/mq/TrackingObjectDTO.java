/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.mq;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="TrackingObjectDTO", description="Tracking object class structure")
public final class TrackingObjectDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Object Id", implementation=String.class, example="fd35fbaa-dac2-4259-97c4-1b5d812e03ed")
    private final String id;
    @JSONField(name="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="position_changed")
    @Schema(name="position_changed", description="Flag indicating if object position has changed comparing to previous object tracking reading", example="true")
    private final boolean positionChanged;

    public String getId() {
        return this.id;
    }

    public double[] getBox() {
        return this.box;
    }

    public boolean isPositionChanged() {
        return this.positionChanged;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrackingObjectDTO)) {
            return false;
        }
        TrackingObjectDTO other = (TrackingObjectDTO)o;
        if (this.isPositionChanged() != other.isPositionChanged()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        return Arrays.equals(this.getBox(), other.getBox());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isPositionChanged() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        return result;
    }

    public String toString() {
        return "TrackingObjectDTO(id=" + this.getId() + ", box=" + Arrays.toString(this.getBox()) + ", positionChanged=" + this.isPositionChanged() + ")";
    }

    public TrackingObjectDTO(String id, double[] box, boolean positionChanged) {
        this.id = id;
        this.box = box;
        this.positionChanged = positionChanged;
    }
}

