/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.plugins.dto.gundetection;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.gundetection.DetectedObject;
import com.incoresoft.middleware.analytics.models.gundetection.WeaponType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="DetectedObjectDTO", description="Detected object class structure")
public record DetectedObjectDTO(@JsonProperty(value="type") @JSONField(name="type") @Schema(name="type", description="Object type allowed in analytics", implementation=Integer.class, example="0") WeaponType type, @JsonProperty(value="confidence") @JSONField(name="confidence") @Schema(name="confidence", description="Analytics sensitivity parameter is responsible for the probability threshold of the object detection", implementation=Integer.class, example="8") double confidence, @JsonProperty(value="box") @JSONField(name="box") @Schema(name="box", description="Detection object box coordinates list", type="object", example="[0.6200000047683716,0.2800000011920929,0.8299999833106995,0.8899999856948853]") double[] box) {
    public DetectedObjectDTO(DetectedObject object) {
        this(object.getType(), object.getConfidence(), object.getBox());
    }
}

