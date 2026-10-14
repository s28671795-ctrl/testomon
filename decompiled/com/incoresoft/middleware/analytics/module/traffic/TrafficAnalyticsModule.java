/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.traffic;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.AnalyticsModes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.module.traffic.Line;
import com.incoresoft.middleware.analytics.module.traffic.TrafficAdvancedSettings;
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

@Module(analyticPluginName="traffic", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.traffic)})
@Schema(name="TrafficAnalyticsModule", description="Traffic module class structure")
public class TrafficAnalyticsModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="push_delay")
    @Schema(name="push_delay", description="Push delay: its delay between MQ packages. Analytics will send each X seconds results.", example="1", implementation=Integer.class)
    @Min(value=0L)
    @Max(value=100L)
    @NotNull
    private @Min(value=0L) @Max(value=100L) @NotNull Integer pushDelay;
    @JSONField(name="mode", defaultValue="alert", serializeUsing=AnalyticsModes.Serializer.class, deserializeUsing=AnalyticsModes.Serializer.class)
    @Schema(name="mode", description="Analytics mode", example="alert", implementation=AnalyticsModes.class)
    @NotNull
    private AnalyticsModes mode = AnalyticsModes.alert;
    @JSONField(name="show_popup_for_internal_notifications")
    @JsonProperty(value="show_popup_for_internal_notifications")
    @Schema(name="show_popup_for_internal_notifications", description="Show popup for internal notifications", implementation=Boolean.class)
    boolean showPopupForInternalNotifications;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=TrafficAdvancedSettings.class)
    @Valid
    private TrafficAdvancedSettings advancedSettings;
    @JSONField(name="lines")
    @ArraySchema(arraySchema=@Schema(name="lines", description="Lines"), schema=@Schema(name="lines", implementation=Line.class))
    @Valid
    @NotEmpty
    private Line[] lines;

    public void setLines(@Valid @NotEmpty Line[] lines) {
        if (lines != null) {
            for (int i = 0; i < lines.length; ++i) {
                lines[i].setId(i);
            }
        }
        this.lines = lines;
    }

    public Integer getPushDelay() {
        return this.pushDelay;
    }

    public AnalyticsModes getMode() {
        return this.mode;
    }

    public boolean isShowPopupForInternalNotifications() {
        return this.showPopupForInternalNotifications;
    }

    public TrafficAdvancedSettings getAdvancedSettings() {
        return this.advancedSettings;
    }

    public Line[] getLines() {
        return this.lines;
    }

    public void setPushDelay(Integer pushDelay) {
        this.pushDelay = pushDelay;
    }

    public void setMode(AnalyticsModes mode) {
        this.mode = mode;
    }

    @JsonProperty(value="show_popup_for_internal_notifications")
    public void setShowPopupForInternalNotifications(boolean showPopupForInternalNotifications) {
        this.showPopupForInternalNotifications = showPopupForInternalNotifications;
    }

    public void setAdvancedSettings(TrafficAdvancedSettings advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrafficAnalyticsModule)) {
            return false;
        }
        TrafficAnalyticsModule other = (TrafficAnalyticsModule)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isShowPopupForInternalNotifications() != other.isShowPopupForInternalNotifications()) {
            return false;
        }
        Integer this$pushDelay = this.getPushDelay();
        Integer other$pushDelay = other.getPushDelay();
        if (this$pushDelay == null ? other$pushDelay != null : !((Object)this$pushDelay).equals(other$pushDelay)) {
            return false;
        }
        AnalyticsModes this$mode = this.getMode();
        AnalyticsModes other$mode = other.getMode();
        if (this$mode == null ? other$mode != null : !((Object)((Object)this$mode)).equals((Object)other$mode)) {
            return false;
        }
        TrafficAdvancedSettings this$advancedSettings = this.getAdvancedSettings();
        TrafficAdvancedSettings other$advancedSettings = other.getAdvancedSettings();
        if (this$advancedSettings == null ? other$advancedSettings != null : !((Object)this$advancedSettings).equals(other$advancedSettings)) {
            return false;
        }
        return Arrays.deepEquals(this.getLines(), other.getLines());
    }

    protected boolean canEqual(Object other) {
        return other instanceof TrafficAnalyticsModule;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isShowPopupForInternalNotifications() ? 79 : 97);
        Integer $pushDelay = this.getPushDelay();
        result = result * 59 + ($pushDelay == null ? 43 : ((Object)$pushDelay).hashCode());
        AnalyticsModes $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : ((Object)((Object)$mode)).hashCode());
        TrafficAdvancedSettings $advancedSettings = this.getAdvancedSettings();
        result = result * 59 + ($advancedSettings == null ? 43 : ((Object)$advancedSettings).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getLines());
        return result;
    }

    public String toString() {
        return "TrafficAnalyticsModule(pushDelay=" + this.getPushDelay() + ", mode=" + String.valueOf((Object)this.getMode()) + ", showPopupForInternalNotifications=" + this.isShowPopupForInternalNotifications() + ", advancedSettings=" + String.valueOf(this.getAdvancedSettings()) + ", lines=" + Arrays.deepToString(this.getLines()) + ")";
    }
}

