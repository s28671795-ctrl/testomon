/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.traffic;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.settings.TrackerType;
import com.incoresoft.middleware.model.analytics.module.advancedsettings.AdvancedSettings;
import com.incoresoft.middleware.model.analytics.module.advancedsettings.ModelOptions;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(name="TrafficAdvancedSettings", description="Advanced settings class structure for Traffic Analytics Module")
public final class TrafficAdvancedSettings
implements AdvancedSettings {
    @JSONField(name="sensitivity", defaultValue="5")
    @Schema(name="sensitivity", description="Responsible for the sensitivity of object detection. Higher values result in more false positives, but lesser probability to miss an object.")
    @DecimalMin(value="1.0", message="Sensitivity should not be less than 1")
    @DecimalMax(value="10.0")
    @NotNull
    private final @DecimalMin(value="1.0", message="Sensitivity should not be less than 1") @DecimalMax(value="10.0") @NotNull Float sensitivity;
    @JSONField(name="model", defaultValue="quality", serializeUsing=ModelOptions.Serializer.class, deserializeUsing=ModelOptions.Serializer.class)
    @Schema(name="model", description="Model type", implementation=ModelOptions.class, example="quality")
    private final ModelOptions model;
    @JSONField(name="tracker", defaultValue="normal")
    @Schema(name="tracker", description="Tracker type", implementation=TrackerType.class, example="normal")
    private final TrackerType tracker;
    @JSONField(name="tracker_buffer_time", defaultValue="10")
    @Schema(name="tracker_buffer_time", description="The time the recognized object is stored in the buffer", implementation=Integer.class, example="10")
    @Min(value=10L, message="Buffer time should not be less than 10")
    @NotNull
    private final @Min(value=10L, message="Buffer time should not be less than 10") @NotNull Integer trackerBufferTime;
    @JSONField(name="alarm_filtration")
    @Schema(name="alarm_filtration", description="The AI alarm filtration compares the last 10 alarms.", implementation=Boolean.class, example="true")
    private final boolean alarmFiltration;
    @JSONField(name="min_height", defaultValue="25")
    @Schema(name="min_height", description="Min object height", implementation=Integer.class, example="25")
    @Min(value=25L)
    @NotNull
    private final @Min(value=25L) @NotNull Integer minHeight;
    @JSONField(name="min_width", defaultValue="25")
    @Schema(name="min_width", description="Min object width", implementation=Integer.class, example="25")
    @Min(value=25L)
    @NotNull
    private final @Min(value=25L) @NotNull Integer minWidth;
    @JSONField(name="crossing_timeout", defaultValue="60")
    @Schema(name="crossing_timeout", implementation=Integer.class, example="60")
    @Min(value=0L)
    @Max(value=600L)
    @NotNull
    private final @Min(value=0L) @Max(value=600L) @NotNull Integer crossingTimeout;

    @Override
    public int getMinWidth() {
        return this.minWidth;
    }

    @Override
    public int getMinHeight() {
        return this.minHeight;
    }

    @Override
    public float getSensitivity() {
        return this.sensitivity.floatValue();
    }

    @Override
    public int getTrackerBufferTime() {
        return this.trackerBufferTime;
    }

    @Override
    public ModelOptions getModel() {
        return this.model;
    }

    public TrackerType getTracker() {
        return this.tracker;
    }

    public boolean isAlarmFiltration() {
        return this.alarmFiltration;
    }

    public Integer getCrossingTimeout() {
        return this.crossingTimeout;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrafficAdvancedSettings)) {
            return false;
        }
        TrafficAdvancedSettings other = (TrafficAdvancedSettings)o;
        if (this.isAlarmFiltration() != other.isAlarmFiltration()) {
            return false;
        }
        if (Float.compare(this.getSensitivity(), other.getSensitivity()) != 0) {
            return false;
        }
        if (this.getTrackerBufferTime() != other.getTrackerBufferTime()) {
            return false;
        }
        if (this.getMinHeight() != other.getMinHeight()) {
            return false;
        }
        if (this.getMinWidth() != other.getMinWidth()) {
            return false;
        }
        Integer this$crossingTimeout = this.getCrossingTimeout();
        Integer other$crossingTimeout = other.getCrossingTimeout();
        if (this$crossingTimeout == null ? other$crossingTimeout != null : !((Object)this$crossingTimeout).equals(other$crossingTimeout)) {
            return false;
        }
        ModelOptions this$model = this.getModel();
        ModelOptions other$model = other.getModel();
        if (this$model == null ? other$model != null : !((Object)((Object)this$model)).equals((Object)other$model)) {
            return false;
        }
        TrackerType this$tracker = this.getTracker();
        TrackerType other$tracker = other.getTracker();
        return !(this$tracker == null ? other$tracker != null : !((Object)((Object)this$tracker)).equals((Object)other$tracker));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAlarmFiltration() ? 79 : 97);
        result = result * 59 + Float.floatToIntBits(this.getSensitivity());
        result = result * 59 + this.getTrackerBufferTime();
        result = result * 59 + this.getMinHeight();
        result = result * 59 + this.getMinWidth();
        Integer $crossingTimeout = this.getCrossingTimeout();
        result = result * 59 + ($crossingTimeout == null ? 43 : ((Object)$crossingTimeout).hashCode());
        ModelOptions $model = this.getModel();
        result = result * 59 + ($model == null ? 43 : ((Object)((Object)$model)).hashCode());
        TrackerType $tracker = this.getTracker();
        result = result * 59 + ($tracker == null ? 43 : ((Object)((Object)$tracker)).hashCode());
        return result;
    }

    public String toString() {
        return "TrafficAdvancedSettings(sensitivity=" + this.getSensitivity() + ", model=" + String.valueOf((Object)this.getModel()) + ", tracker=" + String.valueOf((Object)this.getTracker()) + ", trackerBufferTime=" + this.getTrackerBufferTime() + ", alarmFiltration=" + this.isAlarmFiltration() + ", minHeight=" + this.getMinHeight() + ", minWidth=" + this.getMinWidth() + ", crossingTimeout=" + this.getCrossingTimeout() + ")";
    }

    public TrafficAdvancedSettings(Float sensitivity, ModelOptions model, TrackerType tracker, Integer trackerBufferTime, boolean alarmFiltration, Integer minHeight, Integer minWidth, Integer crossingTimeout) {
        this.sensitivity = sensitivity;
        this.model = model;
        this.tracker = tracker;
        this.trackerBufferTime = trackerBufferTime;
        this.alarmFiltration = alarmFiltration;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.crossingTimeout = crossingTimeout;
    }
}

