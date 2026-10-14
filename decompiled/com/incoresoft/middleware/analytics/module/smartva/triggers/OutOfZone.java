/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.triggers;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.objectinzone.ObjectType;
import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class OutOfZone
implements Trigger {
    @JSONField(name="dwell_time")
    @JsonProperty(value="dwell_time")
    @Schema(name="dwell_time", description="Dwell time in seconds", example="10")
    private final int dwellTime;
    @JSONField(name="object_types")
    @JsonProperty(value="object_types")
    @ArraySchema(arraySchema=@Schema(description="List of object types"), schema=@Schema(name="object_types", implementation=ObjectType.class))
    private final List<ObjectType> objectTypes;

    @JSONCreator
    @JsonCreator
    public OutOfZone(@JSONField(name="dwell_time") @JsonProperty(value="dwell_time") int dwellTime, @JSONField(name="object_types") @JsonProperty(value="object_types") List<ObjectType> objectTypes) {
        this.dwellTime = dwellTime;
        this.objectTypes = objectTypes;
    }

    @Override
    public TriggerType getType() {
        return TriggerType.OUT_OF_ZONE;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public List<ObjectType> getObjectTypes() {
        return this.objectTypes;
    }
}

