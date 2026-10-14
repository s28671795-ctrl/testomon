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

public class Crowd
implements Trigger {
    @JSONField(name="dwell_time")
    @JsonProperty(value="dwell_time")
    @Schema(name="dwell_time", description="Dwell time in seconds", example="10")
    private final int dwellTime;
    @JSONField(name="counter")
    @JsonProperty(value="counter")
    @Schema(name="counter", description="Counter", example="1")
    private final int counter;

    @JSONCreator
    @JsonCreator
    public Crowd(@JSONField(name="dwell_time") @JsonProperty(value="dwell_time") int dwellTime, @JSONField(name="counter") @JsonProperty(value="counter") int counter) {
        this.dwellTime = dwellTime;
        this.counter = counter;
    }

    @Override
    public TriggerType getType() {
        return TriggerType.CROWD;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public int getCounter() {
        return this.counter;
    }
}

