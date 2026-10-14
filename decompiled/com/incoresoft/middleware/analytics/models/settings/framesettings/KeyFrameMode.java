/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.settings.framesettings;

import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettingsMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="KeyFrameMode", description="Key frame mode class structure")
public final class KeyFrameMode
implements FrameRateSettings {
    @Schema(name="mode", description="Frame settings mode type", required=true, example="key_frame", implementation=String.class, allowableValues={"key_frame"})
    private final FrameRateSettingsMode mode = FrameRateSettingsMode.key_frame;

    @Override
    public FrameRateSettingsMode getMode() {
        return this.mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof KeyFrameMode)) {
            return false;
        }
        KeyFrameMode other = (KeyFrameMode)o;
        FrameRateSettingsMode this$mode = this.getMode();
        FrameRateSettingsMode other$mode = other.getMode();
        return !(this$mode == null ? other$mode != null : !((Object)((Object)this$mode)).equals((Object)other$mode));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        FrameRateSettingsMode $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : ((Object)((Object)$mode)).hashCode());
        return result;
    }

    public String toString() {
        return "KeyFrameMode(mode=" + String.valueOf((Object)this.getMode()) + ")";
    }
}

