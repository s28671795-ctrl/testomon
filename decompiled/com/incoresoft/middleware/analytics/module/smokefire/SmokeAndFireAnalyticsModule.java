/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smokefire;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.settings.AdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.smokefire.SmokeFireAdvancedSettings;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Module(analyticPluginName="smoke_fire", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.smoke_fire)})
@Schema(name="SmokeFireAnalyticsModule", description="Smoke and fire detection module class structure")
public class SmokeAndFireAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private SmokeFireAdvancedSettings advancedSettings;
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="Zone polygons"), schema=@Schema(name="polygons", implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;

    public SmokeFireAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public void setAdvancedSettings(SmokeFireAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }
}

