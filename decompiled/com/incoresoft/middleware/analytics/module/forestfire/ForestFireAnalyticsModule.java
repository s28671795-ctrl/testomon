/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.forestfire;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.settings.AdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="forest_fire", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.smoke_fire)})
@Schema(name="ForestFireAnalyticsModule", description="Forest fire detection module class structure")
public class ForestFireAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="detect_delay", defaultValue="1")
    @Min(value=1L)
    @Max(value=300L)
    private @Min(value=1L) @Max(value=300L) int detectDelay;
    @JSONField(name="alert_delay", defaultValue="1")
    @Min(value=1L)
    @Max(value=300L)
    private @Min(value=1L) @Max(value=300L) int alertDelay;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private AdvancedSettingsImpl advancedSettings;
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="Zone polygons"), schema=@Schema(name="polygons", implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;
    @JSONField(name="onvif_port")
    @Schema(name="onvif_port", description="Onvif port", implementation=String.class, example="80")
    private int onvifPort;
    @JSONField(name="token")
    @Schema(name="token", description="Token", implementation=String.class, example="token")
    @NotNull
    private String token;
    @JSONField(name="min_radius", defaultValue="25")
    @Schema(name="min_radius", description="Min object radius", implementation=Integer.class, example="25")
    @Min(value=0L)
    private @Min(value=0L) int minRadius;
    @JSONField(name="max_radius", defaultValue="25")
    @Schema(name="max_radius", description="Max object radius", implementation=Integer.class, example="25")
    @Min(value=0L)
    private @Min(value=0L) int maxRadius;
    @JSONField(name="view_angle", defaultValue="25")
    @Schema(name="view_angle", description="View angle", implementation=Integer.class, example="25")
    @Min(value=0L)
    private @Min(value=0L) int minViewAngle;
    @JSONField(name="max_view_angle", defaultValue="25")
    @Schema(name="max_view_angle", description="Max view angle", implementation=Integer.class, example="25")
    @Min(value=0L)
    private @Min(value=0L) int maxViewAngle;
    @JSONField(name="invert_direction", defaultValue="false")
    @Schema(name="invert_direction", description="Invert direction", implementation=Boolean.class, example="false")
    private boolean invertDirection;
    @JSONField(name="zero_azimuth_pan")
    @Schema(name="zero_azimuth_pan", description="Zero azimuth pan", implementation=Double.class, example="0.0")
    private double zeroAzimuthPan;
    @JSONField(name="zero_azimuth_offset")
    @Schema(name="zero_azimuth_offset", description="Zero azimuth offset", implementation=Double.class, example="0.0")
    private int zeroAzimuthOffset;

    public int getDetectDelay() {
        return this.detectDelay;
    }

    public int getAlertDelay() {
        return this.alertDelay;
    }

    public AdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public int getOnvifPort() {
        return this.onvifPort;
    }

    public String getToken() {
        return this.token;
    }

    public int getMinRadius() {
        return this.minRadius;
    }

    public int getMaxRadius() {
        return this.maxRadius;
    }

    public int getMinViewAngle() {
        return this.minViewAngle;
    }

    public int getMaxViewAngle() {
        return this.maxViewAngle;
    }

    public boolean isInvertDirection() {
        return this.invertDirection;
    }

    public double getZeroAzimuthPan() {
        return this.zeroAzimuthPan;
    }

    public int getZeroAzimuthOffset() {
        return this.zeroAzimuthOffset;
    }

    public void setDetectDelay(int detectDelay) {
        this.detectDelay = detectDelay;
    }

    public void setAlertDelay(int alertDelay) {
        this.alertDelay = alertDelay;
    }

    public void setAdvancedSettings(AdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }

    public void setOnvifPort(int onvifPort) {
        this.onvifPort = onvifPort;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMinRadius(int minRadius) {
        this.minRadius = minRadius;
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
    }

    public void setMinViewAngle(int minViewAngle) {
        this.minViewAngle = minViewAngle;
    }

    public void setMaxViewAngle(int maxViewAngle) {
        this.maxViewAngle = maxViewAngle;
    }

    public void setInvertDirection(boolean invertDirection) {
        this.invertDirection = invertDirection;
    }

    public void setZeroAzimuthPan(double zeroAzimuthPan) {
        this.zeroAzimuthPan = zeroAzimuthPan;
    }

    public void setZeroAzimuthOffset(int zeroAzimuthOffset) {
        this.zeroAzimuthOffset = zeroAzimuthOffset;
    }
}

