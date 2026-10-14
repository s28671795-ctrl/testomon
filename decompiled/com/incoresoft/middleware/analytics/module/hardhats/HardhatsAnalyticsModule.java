/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.hardhats;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.hardhats.HardhatsAdvancedSettings;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Module(analyticPluginName="hardhat", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.hardhat)})
@Schema(name="HardhatsAnalyticsModule", description="Hardhats module class structure")
public class HardhatsAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=HardhatsAdvancedSettings.class)
    @Valid
    private HardhatsAdvancedSettings advancedSettings;
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="Zone polygons"), schema=@Schema(name="polygons", implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;

    public HardhatsAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public void setAdvancedSettings(HardhatsAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }
}

