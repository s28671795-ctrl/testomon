/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.settings.framesettings;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettingsMode;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;

@Schema(name="FpsMode", description="FPS mode class structure")
public final class FpsMode
implements FrameRateSettings {
    @JSONField(name="fps")
    @Schema(name="fps", description="FPS (number of frames per second)", required=true, example="25")
    @Min(value=1L)
    private final @Min(value=1L) int fps;
    @Schema(name="mode", description="Frame settings mode type", required=true, example="fps", implementation=String.class, allowableValues={"fps"})
    private final FrameRateSettingsMode mode = FrameRateSettingsMode.fps;

    public FpsMode(int fps) {
        this.fps = fps;
    }

    public int getFps() {
        return this.fps;
    }

    @Override
    public FrameRateSettingsMode getMode() {
        return this.mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FpsMode)) {
            return false;
        }
        FpsMode other = (FpsMode)o;
        if (this.getFps() != other.getFps()) {
            return false;
        }
        FrameRateSettingsMode this$mode = this.getMode();
        FrameRateSettingsMode other$mode = other.getMode();
        return !(this$mode == null ? other$mode != null : !((Object)((Object)this$mode)).equals((Object)other$mode));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getFps();
        FrameRateSettingsMode $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : ((Object)((Object)$mode)).hashCode());
        return result;
    }

    public String toString() {
        return "FpsMode(fps=" + this.getFps() + ", mode=" + String.valueOf((Object)this.getMode()) + ")";
    }
}

