/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.poseestimation;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.poseestimation.PoseEstimationRule;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Schema(name="PoseEstimationZone", description="Pose estimation zone class structure")
public class PoseEstimationZone
extends TimePeriodsZone {
    @JSONField(name="rules")
    @ArraySchema(schema=@Schema(name="rules", description="Pose estimation rule", implementation=PoseEstimationRule.class))
    @Valid
    @NotEmpty
    private final PoseEstimationRule[] rules;

    public PoseEstimationZone(String id, String name, Point[] points, String color, TimePeriod[] timePeriods, PoseEstimationRule[] rules) {
        super(id, name, points, color, timePeriods);
        this.rules = rules;
    }

    public PoseEstimationRule[] getRules() {
        return this.rules;
    }
}

