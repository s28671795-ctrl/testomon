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

public class ZoneWithDirection
extends SmartVaRule {
    @JSONField(name="direction")
    @Schema(name="direction", description="Direction", implementation=int.class, example="90")
    private final int direction;

    public ZoneWithDirection(String id, String name, String color, Point[] points, TimePeriod[] timePeriods, SmartVaObjectType[] objectTypes, int direction) {
        super(id, name, color, RuleType.ZONE_WITH_DIRECTION, points, timePeriods, objectTypes);
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }
}

