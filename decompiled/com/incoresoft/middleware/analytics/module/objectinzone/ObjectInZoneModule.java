/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.objectinzone;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.AnalyticsModes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.settings.AdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.module.objectinzone.ObjectInZonePolygon;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="object_in_zone", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.object_in_zone)})
@Schema(name="ObjectInZoneModule", description="Object in zone module class structure")
public class ObjectInZoneModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="mode", defaultValue="alert")
    @Schema(name="mode", description="Analytics mode", example="alert", implementation=AnalyticsModes.class)
    @NotNull
    private AnalyticsModes mode = AnalyticsModes.alert;
    @JSONField(name="show_popup_for_internal_notifications")
    @JsonProperty(value="show_popup_for_internal_notifications")
    @Schema(name="show_popup_for_internal_notifications", description="Show popup for internal notifications", implementation=Boolean.class)
    boolean showPopupForInternalNotifications;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private AdvancedSettingsImpl advancedSettings;
    @JSONField(name="polygons", defaultValue="[]")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="Zone polygons"), schema=@Schema(name="polygons", implementation=ObjectInZonePolygon.class))
    private @NotEmpty List<@Valid ObjectInZonePolygon> polygons;

    public AnalyticsModes getMode() {
        return this.mode;
    }

    public boolean isShowPopupForInternalNotifications() {
        return this.showPopupForInternalNotifications;
    }

    public AdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public List<@Valid ObjectInZonePolygon> getPolygons() {
        return this.polygons;
    }

    public void setMode(AnalyticsModes mode) {
        this.mode = mode;
    }

    @JsonProperty(value="show_popup_for_internal_notifications")
    public void setShowPopupForInternalNotifications(boolean showPopupForInternalNotifications) {
        this.showPopupForInternalNotifications = showPopupForInternalNotifications;
    }

    public void setAdvancedSettings(AdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(List<@Valid ObjectInZonePolygon> polygons) {
        this.polygons = polygons;
    }
}

