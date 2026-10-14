/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.poseestimation;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.poseestimation.PoseEstimationType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(name="PoseEstimationRule", description="Pose estimation rule")
public final class PoseEstimationRule {
    @JSONField(name="pose")
    @Schema(name="pose", description="Pose estimation person pose", implementation=PoseEstimationType.class)
    private final PoseEstimationType pose;
    @JSONField(name="dwell_time")
    @Schema(name="dwell_time", description="Dwell time", implementation=Integer.class, example="5")
    @Min(value=1L)
    @NotNull
    private final @Min(value=1L) @NotNull Integer dwellTime;

    public PoseEstimationType getPose() {
        return this.pose;
    }

    public Integer getDwellTime() {
        return this.dwellTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PoseEstimationRule)) {
            return false;
        }
        PoseEstimationRule other = (PoseEstimationRule)o;
        Integer this$dwellTime = this.getDwellTime();
        Integer other$dwellTime = other.getDwellTime();
        if (this$dwellTime == null ? other$dwellTime != null : !((Object)this$dwellTime).equals(other$dwellTime)) {
            return false;
        }
        PoseEstimationType this$pose = this.getPose();
        PoseEstimationType other$pose = other.getPose();
        return !(this$pose == null ? other$pose != null : !((Object)((Object)this$pose)).equals((Object)other$pose));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $dwellTime = this.getDwellTime();
        result = result * 59 + ($dwellTime == null ? 43 : ((Object)$dwellTime).hashCode());
        PoseEstimationType $pose = this.getPose();
        result = result * 59 + ($pose == null ? 43 : ((Object)((Object)$pose)).hashCode());
        return result;
    }

    public String toString() {
        return "PoseEstimationRule(pose=" + String.valueOf((Object)this.getPose()) + ", dwellTime=" + this.getDwellTime() + ")";
    }

    public PoseEstimationRule(PoseEstimationType pose, Integer dwellTime) {
        this.pose = pose;
        this.dwellTime = dwellTime;
    }
}

