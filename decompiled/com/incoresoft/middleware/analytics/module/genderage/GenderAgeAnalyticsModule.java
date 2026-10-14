/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.genderage;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import com.incoresoft.middleware.analytics.module.genderage.validator.MaxFaceHeightIsGreaterThanMinFaceHeight;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@MaxFaceHeightIsGreaterThanMinFaceHeight
@Module(analyticPluginName="gender_age", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.gender_age)})
@Schema(name="GenderAgeAnalyticsModule", description="Gender age module class structure")
public class GenderAgeAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="push_delay", defaultValue="10")
    @Schema(name="push_delay", description="Push metadata delay, sec", implementation=Integer.class, example="1")
    @Min(value=1L)
    @Max(value=100L)
    private @Min(value=1L) @Max(value=100L) int pushDelay;
    @JSONField(name="min_face_height", defaultValue="50")
    @Schema(name="min_face_height", description="Minimal face height, px", implementation=Integer.class, example="50")
    @Min(value=50L)
    private @Min(value=50L) int minFaceHeight;
    @JSONField(name="max_face_height", defaultValue="400")
    @Schema(name="max_face_height", description="Maximal face height, px", implementation=Integer.class, example="400")
    @Min(value=80L)
    private @Min(value=80L) int maxFaceHeight;
    @JSONField(name="polygons", defaultValue="[]")
    @ArraySchema(schema=@Schema(name="polygons", description="Polygons array", implementation=Zone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;

    public int getPushDelay() {
        return this.pushDelay;
    }

    public int getMinFaceHeight() {
        return this.minFaceHeight;
    }

    public int getMaxFaceHeight() {
        return this.maxFaceHeight;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public void setPushDelay(int pushDelay) {
        this.pushDelay = pushDelay;
    }

    public void setMinFaceHeight(int minFaceHeight) {
        this.minFaceHeight = minFaceHeight;
    }

    public void setMaxFaceHeight(int maxFaceHeight) {
        this.maxFaceHeight = maxFaceHeight;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }
}

