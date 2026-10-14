/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.AnalyticsModes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.alpr.LprAdvancedSettings;
import com.incoresoft.middleware.analytics.module.alpr.LprRegion;
import com.incoresoft.middleware.analytics.module.alpr.SaveFrameOptions;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="alpr", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.alpr)})
@Schema(name="AlprAnalyticsModule", description="ALPR analytics module class structure")
public class AlprAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="mode", defaultValue="alert")
    @JsonProperty(value="mode", defaultValue="alert")
    @Schema(name="mode", description="Analytics mode", example="alert", implementation=AnalyticsModes.class)
    @NotNull
    private AnalyticsModes mode = AnalyticsModes.alert;
    @JSONField(name="polygons")
    @JsonProperty(value="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="ROI array"), schema=@Schema(implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;
    @JSONField(name="region", defaultValue="GLOBAL")
    @JsonProperty(value="region")
    @Schema(name="region", description="GLOBAL, EUROPE, USA, AUSTRALIA, JAPAN, THAILAND, ARABIC, LATAM", implementation=LprRegion.class, example="GLOBAL")
    LprRegion region;
    @JSONField(name="vehicle_wo_number", defaultValue="false")
    @JsonProperty(value="vehicle_wo_number")
    @Schema(name="vehicle_wo_number", description="Record vehicles without license plate number", implementation=Boolean.class, example="false")
    boolean vehicle_wo_number;
    @JSONField(name="blur_in_preview", defaultValue="false")
    @JsonProperty(value="blur_in_preview")
    @Schema(name="blur_in_preview", description="Blur license plate number in live preview", implementation=Boolean.class, example="false")
    boolean blurInPreview;
    @JSONField(name="advanced_settings")
    @JsonProperty(value="advanced_settings")
    @Schema(name="advanced_settings", description="LPR advanced settings", implementation=LprAdvancedSettings.class)
    @Valid
    LprAdvancedSettings advancedSettings;

    public LprAdvancedSettings getAdvancedSettings() {
        return Objects.requireNonNullElseGet(this.advancedSettings, () -> new LprAdvancedSettings(60, 15, 60, "1", 4, 15, false, new ArrayList<String>(), Float.valueOf(0.5f), SaveFrameOptions.plate, 0));
    }

    public AnalyticsModes getMode() {
        return this.mode;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public LprRegion getRegion() {
        return this.region;
    }

    public boolean isVehicle_wo_number() {
        return this.vehicle_wo_number;
    }

    public boolean isBlurInPreview() {
        return this.blurInPreview;
    }

    @JsonProperty(value="mode", defaultValue="alert")
    public void setMode(AnalyticsModes mode) {
        this.mode = mode;
    }

    @JsonProperty(value="polygons")
    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }

    @JsonProperty(value="region")
    public void setRegion(LprRegion region) {
        this.region = region;
    }

    @JsonProperty(value="vehicle_wo_number")
    public void setVehicle_wo_number(boolean vehicle_wo_number) {
        this.vehicle_wo_number = vehicle_wo_number;
    }

    @JsonProperty(value="blur_in_preview")
    public void setBlurInPreview(boolean blurInPreview) {
        this.blurInPreview = blurInPreview;
    }

    @JsonProperty(value="advanced_settings")
    public void setAdvancedSettings(LprAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }
}

