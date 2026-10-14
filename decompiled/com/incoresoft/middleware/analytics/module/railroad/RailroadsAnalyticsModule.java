/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.railroad;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.railroad.Model;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.railroad.validators.MaxLpHeightIsGreaterThanMinLpHeight;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@MaxLpHeightIsGreaterThanMinLpHeight(message="maxLpHeight must be greater than minLpHeight")
@Module(analyticPluginName="railroad", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.railroad)})
public class RailroadsAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="polygons", defaultValue="[{\"id\":\"default-zone-id\",\"points\":[{\"x\":0.0,\"y\":0.0},{\"x\":0.0,\"y\":1.0},{\"x\":1.0,\"y\":1.0},{\"x\":1.0,\"y\":0.0}]}]")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="ROI array"), schema=@Schema(implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;
    @JSONField(name="min_height")
    @Schema(name="min_height", description="min number height", implementation=Integer.class, example="15")
    @Min(value=15L)
    private @Min(value=15L) int minLpHeight;
    @JSONField(name="max_height")
    @Schema(name="max_height", description="max number height", implementation=Integer.class, example="70")
    @Min(value=16L)
    private @Min(value=16L) int maxLpHeight;
    @JSONField(name="min_average_character_height")
    @Schema(name="min_average_character_height", description="Minimal average character height", implementation=Integer.class, example="70")
    @Min(value=0L)
    private @Min(value=0L) int minAverageCharacterHeight;
    @JSONField(name="check_digit", defaultValue="false")
    @Schema(name="check_digit", description="CRC control by last digit", implementation=Boolean.class, example="true")
    private boolean checkDigit;
    @JSONField(name="model", defaultValue="UIC")
    @Schema(name="model", description="Model type ", implementation=Model.class, example="UIC")
    private Model model;

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public int getMinLpHeight() {
        return this.minLpHeight;
    }

    public int getMaxLpHeight() {
        return this.maxLpHeight;
    }

    public int getMinAverageCharacterHeight() {
        return this.minAverageCharacterHeight;
    }

    public boolean isCheckDigit() {
        return this.checkDigit;
    }

    public Model getModel() {
        return this.model;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }

    public void setMinLpHeight(int minLpHeight) {
        this.minLpHeight = minLpHeight;
    }

    public void setMaxLpHeight(int maxLpHeight) {
        this.maxLpHeight = maxLpHeight;
    }

    public void setMinAverageCharacterHeight(int minAverageCharacterHeight) {
        this.minAverageCharacterHeight = minAverageCharacterHeight;
    }

    public void setCheckDigit(boolean checkDigit) {
        this.checkDigit = checkDigit;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}

