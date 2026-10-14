/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.crowds;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.AnalyticsModes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.settings.AdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.module.crowds.CrowdsZone;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="crowd", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.crowd)})
@Schema(name="CrowdsAnalyticsModule", description="Crowds module class structure")
public class CrowdsAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="mode", defaultValue="alert")
    @Schema(name="mode", description="Analytics mode", example="alert", implementation=AnalyticsModes.class)
    @NotNull
    private AnalyticsModes mode = AnalyticsModes.alert;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private AdvancedSettingsImpl advancedSettings;
    @NotEmpty
    @Valid
    private CrowdsZone[] polygons;

    public AnalyticsModes getMode() {
        return this.mode;
    }

    public AdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public CrowdsZone[] getPolygons() {
        return this.polygons;
    }

    public void setMode(AnalyticsModes mode) {
        this.mode = mode;
    }

    public void setAdvancedSettings(AdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(CrowdsZone[] polygons) {
        this.polygons = polygons;
    }
}

