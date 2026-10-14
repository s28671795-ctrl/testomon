/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.gundetection;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.gundetection.GunDetectionAdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.module.gundetection.GunDetectionZone;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Module(analyticPluginName="gun_detection", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.gun_detection)})
@Schema(name="GunDetectionAnalyticsModule", description="Gun detection module class structure")
public class GunDetectionAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="alert_delay")
    @Schema(name="alert_delay", description="Delay between alerts", example="alert", implementation=Integer.class)
    @Min(value=1L)
    @Max(value=300L)
    private @Min(value=1L) @Max(value=300L) int alertDelay = 1;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=GunDetectionAnalyticsModule.class)
    @Valid
    private GunDetectionAdvancedSettingsImpl advancedSettings;
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="Zone polygons"), schema=@Schema(name="polygons", implementation=GunDetectionZone.class))
    @Valid
    @NotEmpty
    private GunDetectionZone[] rules;

    public int getAlertDelay() {
        return this.alertDelay;
    }

    public GunDetectionAdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public GunDetectionZone[] getRules() {
        return this.rules;
    }

    public void setAlertDelay(int alertDelay) {
        this.alertDelay = alertDelay;
    }

    public void setAdvancedSettings(GunDetectionAdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setRules(GunDetectionZone[] rules) {
        this.rules = rules;
    }
}

