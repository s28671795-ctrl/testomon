/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.objectinzone.objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.objectinzone.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="DetectedObjectDTO", description="Detected object class structure")
public final class DetectedObjectIdDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Object Id", implementation=String.class, example="fd35fbaa-dac2-4259-97c4-1b5d812e03ed")
    private final String id;
    @JSONField(name="type")
    @Schema(name="type", description="Object type", implementation=ObjectType.class, example="person")
    private final ObjectType type;
    @JSONField(name="confidence")
    @Schema(name="confidence", description="Object confidence", implementation=Double.class, example="0.8")
    private final double confidence;
    @JSONField(name="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;

    public String getId() {
        return this.id;
    }

    public ObjectType getType() {
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
        if (!(o instanceof DetectedObjectIdDTO)) {
            return false;
        }
        DetectedObjectIdDTO other = (DetectedObjectIdDTO)o;
        if (Double.compare(this.getConfidence(), other.getConfidence()) != 0) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        ObjectType this$type = this.getType();
        ObjectType other$type = other.getType();
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
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        ObjectType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        return result;
    }

    public String toString() {
        return "DetectedObjectIdDTO(id=" + this.getId() + ", type=" + String.valueOf((Object)this.getType()) + ", confidence=" + this.getConfidence() + ", box=" + Arrays.toString(this.getBox()) + ")";
    }

    public DetectedObjectIdDTO(String id, ObjectType type, double confidence, double[] box) {
        this.id = id;
        this.type = type;
        this.confidence = confidence;
        this.box = box;
    }
}

