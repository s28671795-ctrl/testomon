/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.attributes.ObjectAttributesDTO;
import com.incoresoft.middleware.analytics.models.smartva.SmartVaObjectType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(name="DetectedObjectEncodingDTO", description="Detected object class structure")
public final class DetectedObjectEncodingDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Object Id", implementation=String.class, example="fd35fbaa-dac2-4259-97c4-1b5d812e03ed")
    private final String id;
    @JSONField(name="type")
    @Schema(name="type", description="Object type", implementation=SmartVaObjectType.class, example="0")
    private final SmartVaObjectType type;
    @JSONField(name="attributes")
    @Schema(name="attributes", description="Object attributes", implementation=ObjectAttributesDTO.class)
    private final ObjectAttributesDTO attributes;
    @JSONField(name="confidence")
    @Schema(name="confidence", description="Object confidence", implementation=Double.class, example="0.94")
    private final double confidence;
    @JSONField(name="box")
    @ArraySchema(schema=@Schema(name="box", description="Object bounding box(length must be 4), coordinates of two points", type="double", format="double"))
    private final double[] box;
    @JSONField(name="encoding")
    @Schema(name="encoding", description="Object encoding", type="object", example="[0.6200000047683716, ...{512 items}... ,0.8899999856948853]")
    private final float[] encoding;

    public String getId() {
        return this.id;
    }

    public SmartVaObjectType getType() {
        return this.type;
    }

    public ObjectAttributesDTO getAttributes() {
        return this.attributes;
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
        SmartVaObjectType this$type = this.getType();
        SmartVaObjectType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        ObjectAttributesDTO this$attributes = this.getAttributes();
        ObjectAttributesDTO other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !this$attributes.equals(other$attributes)) {
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
        SmartVaObjectType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        ObjectAttributesDTO $attributes = this.getAttributes();
        result = result * 59 + ($attributes == null ? 43 : $attributes.hashCode());
        result = result * 59 + Arrays.hashCode(this.getBox());
        result = result * 59 + Arrays.hashCode(this.getEncoding());
        return result;
    }

    public String toString() {
        return "DetectedObjectEncodingDTO(id=" + this.getId() + ", type=" + String.valueOf((Object)this.getType()) + ", attributes=" + String.valueOf(this.getAttributes()) + ", confidence=" + this.getConfidence() + ", box=" + Arrays.toString(this.getBox()) + ", encoding=" + Arrays.toString(this.getEncoding()) + ")";
    }

    public DetectedObjectEncodingDTO(String id, SmartVaObjectType type, ObjectAttributesDTO attributes, double confidence, double[] box, float[] encoding) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
        this.confidence = confidence;
        this.box = box;
        this.encoding = encoding;
    }
}

