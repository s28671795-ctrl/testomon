/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.rules;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.smartva.SmartVaObjectType;
import com.incoresoft.middleware.analytics.models.smartva.TimePeriod;
import com.incoresoft.middleware.analytics.module.smartva.RuleType;
import com.incoresoft.middleware.analytics.module.smartva.SmartVaRule;
import io.swagger.v3.oas.annotations.media.Schema;

public class Path
extends SmartVaRule {
    @JSONField(name="trajectory_accuracy")
    @Schema(name="trajectory_accuracy", description="Trajectory accuracy", implementation=Double.class, example="0.5")
    private final float trajectoryAccuracy;

    public Path(String id, String name, String color, Point[] points, TimePeriod[] timePeriods, SmartVaObjectType[] objectTypes, float trajectoryAccuracy) {
        super(id, name, color, RuleType.PATH, points, timePeriods, objectTypes);
        this.trajectoryAccuracy = trajectoryAccuracy;
    }

    public float getTrajectoryAccuracy() {
        return this.trajectoryAccuracy;
    }
}

