/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.rules;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.smartva.SmartVaObjectType;
import com.incoresoft.middleware.analytics.models.smartva.TimePeriod;
import com.incoresoft.middleware.analytics.module.smartva.RuleType;
import com.incoresoft.middleware.analytics.module.smartva.SmartVaRule;
import com.incoresoft.middleware.analytics.module.smartva.rules.line.LineDirection;
import io.swagger.v3.oas.annotations.media.Schema;

public class Line
extends SmartVaRule {
    @JSONField(name="direction")
    @Schema(name="direction", description="Direction", implementation=LineDirection.class)
    private final LineDirection direction;

    @JSONCreator
    public Line(String id, String name, String color, Point[] points, TimePeriod[] timePeriods, SmartVaObjectType[] objectTypes, LineDirection direction) {
        super(id, name, color, RuleType.LINE, points, timePeriods, objectTypes);
        this.direction = direction;
    }

    public LineDirection getDirection() {
        return this.direction;
    }
}

