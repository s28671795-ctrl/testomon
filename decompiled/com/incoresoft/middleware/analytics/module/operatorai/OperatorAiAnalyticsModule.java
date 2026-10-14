/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.operatorai;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.operatorai.OperatorAiAdvancedSettings;
import com.incoresoft.middleware.analytics.module.operatorai.OperatorAiZone;
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

@Module(analyticPluginName="operator_ai", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.operator_ai)})
public class OperatorAiAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="polygons")
    @JsonProperty(value="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="ROI array"), schema=@Schema(implementation=OperatorAiZone.class))
    @Valid
    @NotEmpty
    private OperatorAiZone[] polygons;
    @JSONField(name="show_popup_for_internal_notifications")
    @JsonProperty(value="show_popup_for_internal_notifications")
    @Schema(name="show_popup_for_internal_notifications", description="Show popup for internal notifications", implementation=Boolean.class)
    boolean showPopupForInternalNotifications;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=OperatorAiAdvancedSettings.class)
    @Valid
    private OperatorAiAdvancedSettings advancedSettings;
    @JSONField(name="detect_delay")
    @Schema(name="detect_delay", description="Detect delay in seconds", implementation=Integer.class, example="30")
    @NotNull
    @Min(value=1L)
    @Max(value=60L)
    private @NotNull @Min(value=1L) @Max(value=60L) Integer detectDelay;

    public OperatorAiZone[] getPolygons() {
        return this.polygons;
    }

    public boolean isShowPopupForInternalNotifications() {
        return this.showPopupForInternalNotifications;
    }

    public OperatorAiAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public Integer getDetectDelay() {
        return this.detectDelay;
    }

    @JsonProperty(value="polygons")
    public void setPolygons(OperatorAiZone[] polygons) {
        this.polygons = polygons;
    }

    @JsonProperty(value="show_popup_for_internal_notifications")
    public void setShowPopupForInternalNotifications(boolean showPopupForInternalNotifications) {
        this.showPopupForInternalNotifications = showPopupForInternalNotifications;
    }

    public void setAdvancedSettings(OperatorAiAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setDetectDelay(Integer detectDelay) {
        this.detectDelay = detectDelay;
    }
}

