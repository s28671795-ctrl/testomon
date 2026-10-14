/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smokefire;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class SmokeFireAdvancedSettings {
    @JSONField(name="sensitivity", defaultValue="5")
    @Schema(name="sensitivity", description="Sensitivity parameter is responsible for the probability threshold of the object detection", implementation=Integer.class, example="8")
    @Min(value=1L)
    @Max(value=10L)
    private final @Min(value=1L) @Max(value=10L) int sensitivity;
    @JSONField(name="alert_delay")
    @Schema(name="alert_delay", description="Delay between alerts", example="alert", implementation=Integer.class)
    @NotNull
    @Min(value=0L)
    @Max(value=300L)
    private final @NotNull @Min(value=0L) @Max(value=300L) Integer alertDelay;

    public int getSensitivity() {
        return this.sensitivity;
    }

    public Integer getAlertDelay() {
        return this.alertDelay;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SmokeFireAdvancedSettings)) {
            return false;
        }
        SmokeFireAdvancedSettings other = (SmokeFireAdvancedSettings)o;
        if (this.getSensitivity() != other.getSensitivity()) {
            return false;
        }
        Integer this$alertDelay = this.getAlertDelay();
        Integer other$alertDelay = other.getAlertDelay();
        return !(this$alertDelay == null ? other$alertDelay != null : !((Object)this$alertDelay).equals(other$alertDelay));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getSensitivity();
        Integer $alertDelay = this.getAlertDelay();
        result = result * 59 + ($alertDelay == null ? 43 : ((Object)$alertDelay).hashCode());
        return result;
    }

    public String toString() {
        return "SmokeFireAdvancedSettings(sensitivity=" + this.getSensitivity() + ", alertDelay=" + this.getAlertDelay() + ")";
    }

    public SmokeFireAdvancedSettings(int sensitivity, Integer alertDelay) {
        this.sensitivity = sensitivity;
        this.alertDelay = alertDelay;
    }
}

