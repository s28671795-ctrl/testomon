/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.analytics.module.advancedsettings.AdvancedSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;

@Schema(name="FaceAdvancedSettings", description="Face recognition advanced settings class structure")
public final class FaceAdvancedSettings
implements AdvancedSettings {
    @JSONField(name="tracker_buffer_time", defaultValue="10")
    @Schema(name="tracker_buffer_time", description="The time the recognized object is stored in the buffer", implementation=Integer.class, example="10")
    @Min(value=10L, message="Buffer time should not be less than 10")
    private final @Min(value=10L, message="Buffer time should not be less than 10") int trackerBufferTime;

    @Override
    public int getTrackerBufferTime() {
        return this.trackerBufferTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaceAdvancedSettings)) {
            return false;
        }
        FaceAdvancedSettings other = (FaceAdvancedSettings)o;
        return this.getTrackerBufferTime() == other.getTrackerBufferTime();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getTrackerBufferTime();
        return result;
    }

    public String toString() {
        return "FaceAdvancedSettings(trackerBufferTime=" + this.getTrackerBufferTime() + ")";
    }

    public FaceAdvancedSettings(int trackerBufferTime) {
        this.trackerBufferTime = trackerBufferTime;
    }
}

