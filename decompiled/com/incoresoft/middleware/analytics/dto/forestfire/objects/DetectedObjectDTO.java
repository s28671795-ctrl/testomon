/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.forestfire.objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.smokefire.DetectedObject;
import com.incoresoft.middleware.analytics.models.smokefire.ObjectsType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="DetectedObjectDTO", description="Detected object class structure")
public final class DetectedObjectDTO {
    @JSONField(name="type")
    @Schema(name="type", description="<div><p>ObjectsType enum.</p><p>Valid values are:</p><ul><li>0 - smoke<li>1 - fire</ul></div>", implementation=Integer.class, example="0")
    private final ObjectsType type;
    @JSONField(name="confidence")
    @Schema(name="confidence", description="Object confidence", implementation=Double.class, example="8")
    private final double confidence;
    @JSONField(name="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;

    public DetectedObjectDTO(DetectedObject entity) {
        this.type = entity.getType();
        this.confidence = entity.getConfidence();
        this.box = entity.getBox();
    }

    public ObjectsType getType() {
        return this.type;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public double[] getBox() {
        return this.box;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectedObjectDTO)) {
            return false;
        }
        DetectedObjectDTO other = (DetectedObjectDTO)o;
        if (Double.compare(this.getConfidence(), other.getConfidence()) != 0) {
            return false;
        }
        ObjectsType this$type = this.getType();
        ObjectsType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        return Arrays.equals(this.getBox(), other.getBox());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $confidence = Double.doubleToLongBits(this.getConfidence());
        result = result * 59 + (int)($confidence >>> 32 ^ $confidence);
        ObjectsType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        return result;
    }

    public String toString() {
        return "DetectedObjectDTO(type=" + String.valueOf((Object)this.getType()) + ", confidence=" + this.getConfidence() + ", box=" + Arrays.toString(this.getBox()) + ")";
    }

    public DetectedObjectDTO(ObjectsType type, double confidence, double[] box) {
        this.type = type;
        this.confidence = confidence;
        this.box = box;
    }
}

