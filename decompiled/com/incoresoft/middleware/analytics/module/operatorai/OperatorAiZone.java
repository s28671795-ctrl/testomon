/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.operatorai;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.operatorai.OperatorAiDetectionSensitivities;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description="Operator AI analytics zone")
public class OperatorAiZone
extends TimePeriodsZone {
    @ArraySchema(arraySchema=@Schema(name="types", description="<div><p>Objects types allowed in analytics.</p><p>Valid values are:</p><ul><li>0 - person<li>1 - bicycle<li>2 - car<li>3 - motorbike<li>4 - bus<li>5 - truck<li>6 - animal<li>7 - bags</ul></div>", example="[0, 1, 2, 3]"), schema=@Schema(name="types", description="Types", implementation=Integer.class, example="1"))
    private final int[] types;
    @JSONField(name="positive_condition")
    @JsonProperty(value="positive_condition")
    @Schema(name="positive_condition", description="Condition that defines a positive result(tells AI what to look for)")
    @NotBlank
    private final String positiveCondition;
    @JSONField(name="context")
    @JsonProperty(value="context")
    @Schema(name="context", description="Additional context to help AI understand the scenario")
    @NotBlank
    private final String context;
    @JSONField(name="detection_interval_seconds")
    @JsonProperty(value="detection_interval_seconds")
    @Schema(name="detection_interval_seconds", description="Interval in seconds for AI detections", example="60")
    @Min(value=0L)
    private final @Min(value=0L) int detectionIntervalSeconds;
    @JSONField(name="detection_sensitivity")
    @JsonProperty(value="detection_sensitivity")
    @Schema(name="detection_sensitivity", description="Sensitivity level for AI detections")
    @NotNull
    private final OperatorAiDetectionSensitivities detectionSensitivity;

    @JSONCreator
    public OperatorAiZone(String id, String name, Point[] points, String color, TimePeriod[] timePeriods, int[] types, String positiveCondition, String context, int detectionIntervalSeconds, OperatorAiDetectionSensitivities detectionSensitivity) {
        super(id, name, points, color, timePeriods);
        this.types = types;
        this.positiveCondition = positiveCondition;
        this.context = context;
        this.detectionIntervalSeconds = detectionIntervalSeconds;
        this.detectionSensitivity = detectionSensitivity;
    }

    public int[] getTypes() {
        return this.types;
    }

    public String getPositiveCondition() {
        return this.positiveCondition;
    }

    public String getContext() {
        return this.context;
    }

    public int getDetectionIntervalSeconds() {
        return this.detectionIntervalSeconds;
    }

    public OperatorAiDetectionSensitivities getDetectionSensitivity() {
        return this.detectionSensitivity;
    }
}

