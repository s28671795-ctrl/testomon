/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.face.FaceAdvancedSettings;
import com.incoresoft.middleware.analytics.module.face.validators.FaceDimensions;
import com.incoresoft.middleware.analytics.module.face.validators.MaxFaceHeightIsGreaterThanMinFaceHeight;
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

@MaxFaceHeightIsGreaterThanMinFaceHeight(message="maxFaceHeight must be greater than minFaceHeight")
@Module(analyticPluginName="face", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.face)})
public class FaceAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule,
FaceDimensions {
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="ROI array"), schema=@Schema(implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;
    @JSONField(name="min_face_height")
    @Schema(name="min_face_height", description="Minimum face height value", implementation=Integer.class, example="20")
    @NotNull
    @Min(value=20L)
    private @NotNull @Min(value=20L) Integer minFaceHeight;
    @JSONField(name="max_face_height")
    @Schema(name="max_face_height", description="Maximum face height value", implementation=Integer.class, example="400")
    @NotNull
    @Min(value=0L)
    private @NotNull @Min(value=0L) Integer maxFaceHeight;
    @JSONField(name="camera_buffer")
    @Schema(name="camera_buffer", description="Camera buffer", implementation=Integer.class, example="10")
    @NotNull
    @Min(value=0L)
    @Max(value=600L)
    private @NotNull @Min(value=0L) @Max(value=600L) Integer cameraBuffer;
    @JSONField(name="frames_to_detect")
    @Schema(name="frames_to_detect", description="Frames to detect", implementation=Integer.class, example="100")
    @NotNull
    @Min(value=5L)
    private @NotNull @Min(value=5L) Integer framesToDetect;
    @JSONField(name="blur_in_preview")
    @Schema(name="blur_in_preview", description="Enable or disable blur in preview", implementation=Boolean.class, example="false")
    private boolean blurInPreview;
    @JSONField(name="gender_age")
    @Schema(name="gender_age", description="Enable or disable age and gender detection", implementation=Boolean.class, example="true")
    private boolean genderAge;
    @JSONField(name="detector_sensitivity")
    @Schema(name="detector_sensitivity", description="Detector sensitivity: 0 - 60%, 1 - 70%, 2 - 80%", implementation=Integer.class, example="0")
    private int detectorSensitivity;
    @JSONField(name="mask", defaultValue="false")
    @Schema(name="mask", description="Enable or disable mask detection", implementation=Boolean.class, example="false")
    private boolean mask;
    @JSONField(name="race", defaultValue="false")
    @Schema(name="race", description="Enable or disable race detection", implementation=Boolean.class, example="false")
    private boolean race;
    @JSONField(name="spoofing", defaultValue="false")
    @Schema(name="spoofing", description="Enable or disable spoofing detection", implementation=Boolean.class, example="true")
    private boolean spoofed;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=FaceAdvancedSettings.class)
    @Valid
    private FaceAdvancedSettings advancedSettings;
    @JSONField(name="save_best_face")
    @Schema(name="save_best_face", description="Save face with best quality.", implementation=Boolean.class)
    boolean saveBestFace;

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    @Override
    public Integer getMinFaceHeight() {
        return this.minFaceHeight;
    }

    @Override
    public Integer getMaxFaceHeight() {
        return this.maxFaceHeight;
    }

    public Integer getCameraBuffer() {
        return this.cameraBuffer;
    }

    public Integer getFramesToDetect() {
        return this.framesToDetect;
    }

    public boolean isBlurInPreview() {
        return this.blurInPreview;
    }

    public boolean isGenderAge() {
        return this.genderAge;
    }

    public int getDetectorSensitivity() {
        return this.detectorSensitivity;
    }

    public boolean isMask() {
        return this.mask;
    }

    public boolean isRace() {
        return this.race;
    }

    public boolean isSpoofed() {
        return this.spoofed;
    }

    public FaceAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public boolean isSaveBestFace() {
        return this.saveBestFace;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }

    public void setMinFaceHeight(Integer minFaceHeight) {
        this.minFaceHeight = minFaceHeight;
    }

    public void setMaxFaceHeight(Integer maxFaceHeight) {
        this.maxFaceHeight = maxFaceHeight;
    }

    public void setCameraBuffer(Integer cameraBuffer) {
        this.cameraBuffer = cameraBuffer;
    }

    public void setFramesToDetect(Integer framesToDetect) {
        this.framesToDetect = framesToDetect;
    }

    public void setBlurInPreview(boolean blurInPreview) {
        this.blurInPreview = blurInPreview;
    }

    public void setGenderAge(boolean genderAge) {
        this.genderAge = genderAge;
    }

    public void setDetectorSensitivity(int detectorSensitivity) {
        this.detectorSensitivity = detectorSensitivity;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public void setRace(boolean race) {
        this.race = race;
    }

    public void setSpoofed(boolean spoofed) {
        this.spoofed = spoofed;
    }

    public void setAdvancedSettings(FaceAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setSaveBestFace(boolean saveBestFace) {
        this.saveBestFace = saveBestFace;
    }
}

