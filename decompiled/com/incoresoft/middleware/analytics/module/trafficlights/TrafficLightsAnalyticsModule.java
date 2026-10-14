/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.trafficlights;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.alpr.LprAdvancedSettings;
import com.incoresoft.middleware.analytics.module.alpr.LprRegion;
import com.incoresoft.middleware.analytics.module.alpr.SaveFrameOptions;
import com.incoresoft.middleware.analytics.module.trafficlights.Line;
import com.incoresoft.middleware.analytics.module.trafficlights.Point;
import com.incoresoft.middleware.analytics.module.trafficlights.validators.GreenLightSettingsAreRequired;
import com.incoresoft.middleware.analytics.module.trafficlights.validators.RedLightSettingsAreRequired;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RedLightSettingsAreRequired
@GreenLightSettingsAreRequired
@Module(analyticPluginName="traffic_lights", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.traffic_lights)})
@Schema(name="TrafficLightsAnalyticsModule", description="Traffic lights analytics module class structure")
public class TrafficLightsAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="line")
    @Valid
    @NotNull
    private Line line;
    @JSONField(name="points")
    @Valid
    @NotNull
    @Size(min=2, max=3)
    private @Valid @NotNull @Size(min=2, max=3) List<Point> points;
    @JSONField(name="region", defaultValue="GLOBAL")
    @JsonProperty(value="region")
    @Schema(name="region", description="GLOBAL, EUROPE, USA, AUSTRALIA, JAPAN, THAILAND, ARABIC, LATAM", implementation=LprRegion.class, example="GLOBAL")
    LprRegion region;
    @JSONField(name="vehicle_wo_number", defaultValue="false")
    @Schema(name="vehicle_wo_number", description="Record vehicles without license plate number", implementation=Boolean.class, example="false")
    boolean vehicle_wo_number;
    @JSONField(name="blur_in_preview", defaultValue="false")
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

    public Line getLine() {
        return this.line;
    }

    public List<Point> getPoints() {
        return this.points;
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

    public void setLine(Line line) {
        this.line = line;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @JsonProperty(value="region")
    public void setRegion(LprRegion region) {
        this.region = region;
    }

    public void setVehicle_wo_number(boolean vehicle_wo_number) {
        this.vehicle_wo_number = vehicle_wo_number;
    }

    public void setBlurInPreview(boolean blurInPreview) {
        this.blurInPreview = blurInPreview;
    }

    @JsonProperty(value="advanced_settings")
    public void setAdvancedSettings(LprAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }
}

