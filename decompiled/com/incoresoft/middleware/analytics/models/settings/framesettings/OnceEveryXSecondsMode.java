/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.settings.framesettings;

import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettingsMode;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;

@Schema(name="OnceEveryXSecondsMode", description="\"Once every X seconds\" mode class structure")
public final class OnceEveryXSecondsMode
implements FrameRateSettings {
    @Min(value=1L)
    @Schema(name="interval", description="Interval(in seconds)", required=true, example="25")
    private final @Min(value=1L) int interval;
    @Schema(name="mode", description="Frame settings mode type", required=true, example="once_every_x_seconds", implementation=String.class, allowableValues={"once_every_x_seconds"})
    private final FrameRateSettingsMode mode = FrameRateSettingsMode.once_every_x_seconds;

    public OnceEveryXSecondsMode(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return this.interval;
    }

    @Override
    public FrameRateSettingsMode getMode() {
        return this.mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnceEveryXSecondsMode)) {
            return false;
        }
        OnceEveryXSecondsMode other = (OnceEveryXSecondsMode)o;
        if (this.getInterval() != other.getInterval()) {
            return false;
        }
        FrameRateSettingsMode this$mode = this.getMode();
        FrameRateSettingsMode other$mode = other.getMode();
        return !(this$mode == null ? other$mode != null : !((Object)((Object)this$mode)).equals((Object)other$mode));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getInterval();
        FrameRateSettingsMode $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : ((Object)((Object)$mode)).hashCode());
        return result;
    }

    public String toString() {
        return "OnceEveryXSecondsMode(interval=" + this.getInterval() + ", mode=" + String.valueOf((Object)this.getMode()) + ")";
    }
}

