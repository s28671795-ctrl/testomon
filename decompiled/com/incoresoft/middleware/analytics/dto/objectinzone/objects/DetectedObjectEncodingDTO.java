/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.objectinzone.objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.objectinzone.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="DetectedObjectDTO", description="Detected object class structure")
public final class DetectedObjectEncodingDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Object Id", implementation=String.class, example="fd35fbaa-dac2-4259-97c4-1b5d812e03ed")
    private final String id;
    @JSONField(name="type")
    @Schema(name="type", description="Object type", implementation=ObjectType.class, example="0")
    private final ObjectType type;
    @JSONField(name="confidence")
    @Schema(name="confidence", description="Object confidence", implementation=Double.class, example="0.94")
    private final double confidence;
    @JSONField(name="box")
    @Schema(name="box", description="Object bounding box", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]")
    private final double[] box;
    @JSONField(name="encoding")
    @Schema(name="encoding", description="Object encoding", type="object", example="[0.6200000047683716, ...{512 items}... ,0.8899999856948853]")
    private final float[] encoding;

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

    public float[] getEncoding() {
        return this.encoding;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectedObjectEncodingDTO)) {
            return false;
        }
        DetectedObjectEncodingDTO other = (DetectedObjectEncodingDTO)o;
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
        if (!Arrays.equals(this.getBox(), other.getBox())) {
            return false;
        }
        return Arrays.equals(this.getEncoding(), other.getEncoding());
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
        result = result * 59 + Arrays.hashCode(this.getEncoding());
        return result;
    }

    public String toString() {
        return "DetectedObjectEncodingDTO(id=" + this.getId() + ", type=" + String.valueOf((Object)this.getType()) + ", confidence=" + this.getConfidence() + ", box=" + Arrays.toString(this.getBox()) + ", encoding=" + Arrays.toString(this.getEncoding()) + ")";
    }

    public DetectedObjectEncodingDTO(String id, ObjectType type, double confidence, double[] box, float[] encoding) {
        this.id = id;
        this.type = type;
        this.confidence = confidence;
        this.box = box;
        this.encoding = encoding;
    }
}

