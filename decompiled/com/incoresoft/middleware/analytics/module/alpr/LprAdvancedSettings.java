/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.alpr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.module.alpr.SaveFrameOptions;
import com.incoresoft.middleware.analytics.module.alpr.validations.CountriesMustBeValid;
import com.incoresoft.middleware.analytics.module.alpr.validations.Max;
import com.incoresoft.middleware.analytics.module.alpr.validations.Min;
import com.incoresoft.middleware.analytics.module.alpr.validations.MinPlateLengthMustBeLowerThanMaxPlateLength;
import com.incoresoft.middleware.model.analytics.module.advancedsettings.ModelOptions;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@CountriesMustBeValid
@MinPlateLengthMustBeLowerThanMaxPlateLength
@Schema(name="LprAdvancedSettings", description="License plates advanced settings class structure")
public final class LprAdvancedSettings {
    @JSONField(name="min_plate_width", defaultValue="40")
    @JsonProperty(value="min_plate_width")
    @Schema(name="min_plate_width", description="Minimum width plate number value", implementation=Integer.class, example="40")
    @javax.validation.constraints.Min(value=40L)
    @NotNull
    private final @javax.validation.constraints.Min(value=40L) @NotNull Integer minPlateWidth;
    @JSONField(name="min_plate_height", defaultValue="15")
    @JsonProperty(value="min_plate_height")
    @Schema(name="min_plate_height", description="Minimum height plate number value", implementation=Integer.class, example="15")
    @javax.validation.constraints.Min(value=15L)
    @NotNull
    private final @javax.validation.constraints.Min(value=15L) @NotNull Integer minPlateHeight;
    @JSONField(name="frames_to_detect", defaultValue="60")
    @JsonProperty(value="frames_to_detect")
    @Schema(name="frames_to_detect", description="Value of frames to detect ", implementation=Integer.class, example="60")
    @javax.validation.constraints.Min(value=3L)
    @javax.validation.constraints.Max(value=1000L)
    @NotNull
    private final @javax.validation.constraints.Min(value=3L) @javax.validation.constraints.Max(value=1000L) @NotNull Integer framesToDetect;
    @JSONField(name="ocr_model", defaultValue="1")
    @JsonProperty(value="ocr_model")
    @Schema(name="model", description="OCR model type", implementation=ModelOptions.class, example="0")
    private final String ocrModel;
    @JSONField(name="min_plate_length", defaultValue="4")
    @JsonProperty(value="min_plate_length")
    @Schema(name="min_plate_length", description="Min plate length", implementation=Integer.class, example="4")
    @javax.validation.constraints.Min(value=1L)
    @javax.validation.constraints.Max(value=15L)
    private final @javax.validation.constraints.Min(value=1L) @javax.validation.constraints.Max(value=15L) int minPlateLength;
    @JSONField(name="max_plate_length", defaultValue="15")
    @JsonProperty(value="max_plate_length")
    @Schema(name="max_plate_length", description="Max plate length", implementation=Integer.class, example="10")
    @javax.validation.constraints.Min(value=1L)
    @javax.validation.constraints.Max(value=15L)
    private final @javax.validation.constraints.Min(value=1L) @javax.validation.constraints.Max(value=15L) int maxPlateLength;
    @JSONField(name="template_matching", defaultValue="false")
    @JsonProperty(value="template_matching")
    @Schema(name="template_matching", description="Save only license plates which match country template", implementation=Boolean.class, example="false")
    @NotNull
    private final boolean templateMatching;
    @JSONField(name="countries", defaultValue="[]")
    @JsonProperty(value="countries")
    @ArraySchema(arraySchema=@Schema(name="countries", description="Countries"), schema=@Schema(name="countries", implementation=String.class, example="UA"))
    private final List<String> countries;
    @JSONField(name="sensitivity", defaultValue="0.5")
    @Schema(name="sensitivity", description="Responsible for the sensitivity of object detection. Higher values result in more false positives, but lesser probability to miss an object.")
    @DecimalMin(value="0.0")
    @DecimalMax(value="1.0")
    @NotNull
    private final @DecimalMin(value="0.0") @DecimalMax(value="1.0") @NotNull Float sensitivity;
    @JSONField(name="save_frame")
    @JsonProperty(value="save_frame")
    @Schema(name="save_frame")
    private final SaveFrameOptions saveFrame;
    @JSONField(name="timeout")
    @JsonProperty(value="timeout")
    @Schema(name="timeout")
    @Min(value=10L)
    @Max(value=1000L)
    private final int timeout;

    public Integer getMinPlateWidth() {
        return this.minPlateWidth;
    }

    public Integer getMinPlateHeight() {
        return this.minPlateHeight;
    }

    public Integer getFramesToDetect() {
        return this.framesToDetect;
    }

    public String getOcrModel() {
        return this.ocrModel;
    }

    public int getMinPlateLength() {
        return this.minPlateLength;
    }

    public int getMaxPlateLength() {
        return this.maxPlateLength;
    }

    public boolean isTemplateMatching() {
        return this.templateMatching;
    }

    public List<String> getCountries() {
        return this.countries;
    }

    public Float getSensitivity() {
        return this.sensitivity;
    }

    public SaveFrameOptions getSaveFrame() {
        return this.saveFrame;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LprAdvancedSettings)) {
            return false;
        }
        LprAdvancedSettings other = (LprAdvancedSettings)o;
        if (this.getMinPlateLength() != other.getMinPlateLength()) {
            return false;
        }
        if (this.getMaxPlateLength() != other.getMaxPlateLength()) {
            return false;
        }
        if (this.isTemplateMatching() != other.isTemplateMatching()) {
            return false;
        }
        if (this.getTimeout() != other.getTimeout()) {
            return false;
        }
        Integer this$minPlateWidth = this.getMinPlateWidth();
        Integer other$minPlateWidth = other.getMinPlateWidth();
        if (this$minPlateWidth == null ? other$minPlateWidth != null : !((Object)this$minPlateWidth).equals(other$minPlateWidth)) {
            return false;
        }
        Integer this$minPlateHeight = this.getMinPlateHeight();
        Integer other$minPlateHeight = other.getMinPlateHeight();
        if (this$minPlateHeight == null ? other$minPlateHeight != null : !((Object)this$minPlateHeight).equals(other$minPlateHeight)) {
            return false;
        }
        Integer this$framesToDetect = this.getFramesToDetect();
        Integer other$framesToDetect = other.getFramesToDetect();
        if (this$framesToDetect == null ? other$framesToDetect != null : !((Object)this$framesToDetect).equals(other$framesToDetect)) {
            return false;
        }
        Float this$sensitivity = this.getSensitivity();
        Float other$sensitivity = other.getSensitivity();
        if (this$sensitivity == null ? other$sensitivity != null : !((Object)this$sensitivity).equals(other$sensitivity)) {
            return false;
        }
        String this$ocrModel = this.getOcrModel();
        String other$ocrModel = other.getOcrModel();
        if (this$ocrModel == null ? other$ocrModel != null : !this$ocrModel.equals(other$ocrModel)) {
            return false;
        }
        List<String> this$countries = this.getCountries();
        List<String> other$countries = other.getCountries();
        if (this$countries == null ? other$countries != null : !((Object)this$countries).equals(other$countries)) {
            return false;
        }
        SaveFrameOptions this$saveFrame = this.getSaveFrame();
        SaveFrameOptions other$saveFrame = other.getSaveFrame();
        return !(this$saveFrame == null ? other$saveFrame != null : !((Object)((Object)this$saveFrame)).equals((Object)other$saveFrame));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getMinPlateLength();
        result = result * 59 + this.getMaxPlateLength();
        result = result * 59 + (this.isTemplateMatching() ? 79 : 97);
        result = result * 59 + this.getTimeout();
        Integer $minPlateWidth = this.getMinPlateWidth();
        result = result * 59 + ($minPlateWidth == null ? 43 : ((Object)$minPlateWidth).hashCode());
        Integer $minPlateHeight = this.getMinPlateHeight();
        result = result * 59 + ($minPlateHeight == null ? 43 : ((Object)$minPlateHeight).hashCode());
        Integer $framesToDetect = this.getFramesToDetect();
        result = result * 59 + ($framesToDetect == null ? 43 : ((Object)$framesToDetect).hashCode());
        Float $sensitivity = this.getSensitivity();
        result = result * 59 + ($sensitivity == null ? 43 : ((Object)$sensitivity).hashCode());
        String $ocrModel = this.getOcrModel();
        result = result * 59 + ($ocrModel == null ? 43 : $ocrModel.hashCode());
        List<String> $countries = this.getCountries();
        result = result * 59 + ($countries == null ? 43 : ((Object)$countries).hashCode());
        SaveFrameOptions $saveFrame = this.getSaveFrame();
        result = result * 59 + ($saveFrame == null ? 43 : ((Object)((Object)$saveFrame)).hashCode());
        return result;
    }

    public String toString() {
        return "LprAdvancedSettings(minPlateWidth=" + this.getMinPlateWidth() + ", minPlateHeight=" + this.getMinPlateHeight() + ", framesToDetect=" + this.getFramesToDetect() + ", ocrModel=" + this.getOcrModel() + ", minPlateLength=" + this.getMinPlateLength() + ", maxPlateLength=" + this.getMaxPlateLength() + ", templateMatching=" + this.isTemplateMatching() + ", countries=" + String.valueOf(this.getCountries()) + ", sensitivity=" + this.getSensitivity() + ", saveFrame=" + String.valueOf((Object)this.getSaveFrame()) + ", timeout=" + this.getTimeout() + ")";
    }

    public LprAdvancedSettings(Integer minPlateWidth, Integer minPlateHeight, Integer framesToDetect, String ocrModel, int minPlateLength, int maxPlateLength, boolean templateMatching, List<String> countries, Float sensitivity, SaveFrameOptions saveFrame, int timeout2) {
        this.minPlateWidth = minPlateWidth;
        this.minPlateHeight = minPlateHeight;
        this.framesToDetect = framesToDetect;
        this.ocrModel = ocrModel;
        this.minPlateLength = minPlateLength;
        this.maxPlateLength = maxPlateLength;
        this.templateMatching = templateMatching;
        this.countries = countries;
        this.sensitivity = sensitivity;
        this.saveFrame = saveFrame;
        this.timeout = timeout2;
    }
}

