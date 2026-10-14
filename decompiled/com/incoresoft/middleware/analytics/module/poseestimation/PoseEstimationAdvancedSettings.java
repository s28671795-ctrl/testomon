/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.poseestimation;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.analytics.module.advancedsettings.AdvancedSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Schema(name="PoseEstimationAdvancedSettings", description="Pose estimation advanced settings class structure")
public final class PoseEstimationAdvancedSettings
implements AdvancedSettings {
    @JSONField(name="sensitivity", defaultValue="5")
    @Schema(name="sensitivity", description="Responsible for the sensitivity of object detection. Higher values result in more false positives, but lesser probability to miss an object.")
    @DecimalMin(value="1.0", message="Sensitivity should not be less than 1")
    @DecimalMax(value="10.0")
    private final @DecimalMin(value="1.0", message="Sensitivity should not be less than 1") @DecimalMax(value="10.0") float sensitivity;

    @Override
    public float getSensitivity() {
        return this.sensitivity;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PoseEstimationAdvancedSettings)) {
            return false;
        }
        PoseEstimationAdvancedSettings other = (PoseEstimationAdvancedSettings)o;
        return Float.compare(this.getSensitivity(), other.getSensitivity()) == 0;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getSensitivity());
        return result;
    }

    public String toString() {
        return "PoseEstimationAdvancedSettings(sensitivity=" + this.getSensitivity() + ")";
    }

    public PoseEstimationAdvancedSettings(float sensitivity) {
        this.sensitivity = sensitivity;
    }
}

