/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.poseestimation;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.poseestimation.PoseEstimationAdvancedSettings;
import com.incoresoft.middleware.analytics.module.poseestimation.PoseEstimationZone;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Module(analyticPluginName="fallen_people", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.fallen_people)})
@Schema(name="PoseEstimationAnalyticsModule", description="Pose estimation module class structure")
public class PoseEstimationAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @Valid
    @NotEmpty
    private PoseEstimationZone[] polygons;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=PoseEstimationAdvancedSettings.class)
    @Valid
    private PoseEstimationAdvancedSettings advancedSettings;

    public PoseEstimationZone[] getPolygons() {
        return this.polygons;
    }

    public PoseEstimationAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public void setPolygons(PoseEstimationZone[] polygons) {
        this.polygons = polygons;
    }

    public void setAdvancedSettings(PoseEstimationAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }
}

