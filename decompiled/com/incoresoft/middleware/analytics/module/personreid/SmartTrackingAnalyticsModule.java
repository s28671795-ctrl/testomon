/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.personreid;

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
import java.util.Arrays;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="person_reid", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.person_reid)})
@Schema(name="SmartTrackingAnalyticsModule", description="Smart tracking analytics module class structure")
public class SmartTrackingAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="detect_delay")
    @Schema(name="detect_delay", description="Detect delay in seconds", implementation=Integer.class, example="1")
    @NotNull
    @Min(value=1L)
    @Max(value=60L)
    private @NotNull @Min(value=1L) @Max(value=60L) Integer detectDelay;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private AdvancedSettingsImpl advancedSettings;
    @JSONField(name="polygons")
    @ArraySchema(arraySchema=@Schema(name="polygons", description="ROI array"), schema=@Schema(implementation=TimePeriodsZone.class))
    @Valid
    @NotEmpty
    private TimePeriodsZone[] polygons;

    public Integer getDetectDelay() {
        return this.detectDelay;
    }

    public AdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public TimePeriodsZone[] getPolygons() {
        return this.polygons;
    }

    public void setDetectDelay(Integer detectDelay) {
        this.detectDelay = detectDelay;
    }

    public void setAdvancedSettings(AdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setPolygons(TimePeriodsZone[] polygons) {
        this.polygons = polygons;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SmartTrackingAnalyticsModule)) {
            return false;
        }
        SmartTrackingAnalyticsModule other = (SmartTrackingAnalyticsModule)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$detectDelay = this.getDetectDelay();
        Integer other$detectDelay = other.getDetectDelay();
        if (this$detectDelay == null ? other$detectDelay != null : !((Object)this$detectDelay).equals(other$detectDelay)) {
            return false;
        }
        AdvancedSettingsImpl this$advancedSettings = this.getAdvancedSettings();
        AdvancedSettingsImpl other$advancedSettings = other.getAdvancedSettings();
        if (this$advancedSettings == null ? other$advancedSettings != null : !((Object)this$advancedSettings).equals(other$advancedSettings)) {
            return false;
        }
        return Arrays.deepEquals(this.getPolygons(), other.getPolygons());
    }

    protected boolean canEqual(Object other) {
        return other instanceof SmartTrackingAnalyticsModule;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $detectDelay = this.getDetectDelay();
        result = result * 59 + ($detectDelay == null ? 43 : ((Object)$detectDelay).hashCode());
        AdvancedSettingsImpl $advancedSettings = this.getAdvancedSettings();
        result = result * 59 + ($advancedSettings == null ? 43 : ((Object)$advancedSettings).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getPolygons());
        return result;
    }

    public String toString() {
        return "SmartTrackingAnalyticsModule(detectDelay=" + this.getDetectDelay() + ", advancedSettings=" + String.valueOf(this.getAdvancedSettings()) + ", polygons=" + Arrays.deepToString(this.getPolygons()) + ")";
    }
}

