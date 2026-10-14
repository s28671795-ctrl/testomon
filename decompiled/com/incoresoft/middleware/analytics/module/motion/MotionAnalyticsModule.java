/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.motion;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.motion.MotionRule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Module(analyticPluginName="motion", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.motion)})
@Schema(name="MotionAnalyticsModule", description="Motion analytics module class structure")
public class MotionAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="rules")
    @ArraySchema(schema=@Schema(name="rules", description="Array of motion rules", implementation=MotionRule.class))
    @NotEmpty
    @Valid
    private MotionRule[] rules;
    @JSONField(name="activate_by_motion")
    @Schema(name="activate_by_motion", description="Determines whether analytics will be triggered by motion", example="true", implementation=Boolean.class)
    private boolean activateByMotion;

    public MotionRule[] getRules() {
        return this.rules;
    }

    public boolean isActivateByMotion() {
        return this.activateByMotion;
    }

    public void setRules(MotionRule[] rules) {
        this.rules = rules;
    }

    public void setActivateByMotion(boolean activateByMotion) {
        this.activateByMotion = activateByMotion;
    }
}

