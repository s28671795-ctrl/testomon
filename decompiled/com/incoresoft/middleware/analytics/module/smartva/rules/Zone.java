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
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Zone
extends SmartVaRule {
    @JSONField(name="exclusion_zones")
    @Size(max=3)
    @Schema(name="exclusion_zones", description="Exclusion zones")
    private final @Size(max=3) List<@NotNull List<@NotNull Point>> exclusionZones;
    @JSONField(name="trigger")
    @Schema(name="trigger", description="Trigger of rule", implementation=Trigger.class)
    private final Trigger trigger;

    @JSONCreator
    public Zone(String id, String name, String color, Point[] points, TimePeriod[] timePeriods, SmartVaObjectType[] objectTypes, List<List<Point>> exclusionZones, Trigger trigger) {
        super(id, name, color, RuleType.ZONE, points, timePeriods, objectTypes);
        this.exclusionZones = exclusionZones;
        this.trigger = trigger;
    }

    public List<@NotNull List<@NotNull Point>> getExclusionZones() {
        return this.exclusionZones;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }
}

