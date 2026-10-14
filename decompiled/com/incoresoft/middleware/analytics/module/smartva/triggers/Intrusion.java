/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.triggers;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import io.swagger.v3.oas.annotations.media.Schema;

public class Intrusion
implements Trigger {
    @JSONField(name="dwell_time")
    @JsonProperty(value="dwell_time")
    @Schema(name="dwell_time", description="Dwell time in seconds", example="10")
    private final int dwellTime;

    @JSONCreator
    @JsonCreator
    public Intrusion(@JSONField(name="dwell_time") @JsonProperty(value="dwell_time") int dwellTime) {
        this.dwellTime = dwellTime;
    }

    @Override
    public TriggerType getType() {
        return TriggerType.INTRUSION;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }
}

