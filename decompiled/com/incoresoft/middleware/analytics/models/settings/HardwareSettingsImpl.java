/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.settings;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.settings.framesettings.FpsMode;
import com.incoresoft.middleware.analytics.models.settings.framesettings.KeyFrameMode;
import com.incoresoft.middleware.analytics.models.settings.framesettings.OnceEveryXSecondsMode;
import com.incoresoft.middleware.analytics.serialization.FrameRateSettingsSerializer;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.Hardware;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareAcceleration;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareDecode;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.serializer.HardwareAccelerationSerializer;
import com.incoresoft.middleware.model.analytics.module.hardware.serializer.HardwareDecodeSerializer;
import com.incoresoft.middleware.model.analytics.module.hardware.serializer.HardwareSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(name="HardwareSettingsImpl", description="Hardware settings class structure")
public final class HardwareSettingsImpl
implements HardwareSettings {
    @JSONField(name="hardware", serializeUsing=HardwareSerializer.class, deserializeUsing=HardwareSerializer.class, defaultValue="cpu")
    @Schema(name="hardware", description="Hardware type", implementation=Hardware.class, example="gpu")
    @NotNull
    private final Hardware hardware;
    @JSONField(name="acceleration", serializeUsing=HardwareAccelerationSerializer.class, deserializeUsing=HardwareAccelerationSerializer.class, defaultValue="openvino")
    @Schema(name="acceleration", description="Hardware acceleration", implementation=HardwareAcceleration.class, example="trt")
    private final HardwareAcceleration acceleration;
    @JSONField(name="decoding", serializeUsing=HardwareDecodeSerializer.class, deserializeUsing=HardwareDecodeSerializer.class, defaultValue="nvidia")
    @Schema(name="decoding", description="Hardware decoding", implementation=HardwareDecode.class, example="nvidia")
    @NotNull
    private final HardwareDecode decoding;
    @JSONField(name="frame_rate_settings", deserializeUsing=FrameRateSettingsSerializer.class)
    @Schema(name="frame_rate_settings", description="Frame rate settings", oneOf={FpsMode.class, KeyFrameMode.class, OnceEveryXSecondsMode.class})
    @NotNull
    @Valid
    private final FrameRateSettings frameRateSettings;
    @JSONField(name="motion")
    @Schema(name="motion", description="Motion frame processing", implementation=Boolean.class, example="true")
    private final boolean motion;
    @JSONField(name="resize")
    @Schema(name="resize", description="Determines whether the analytics will resize video stream to Full HD", implementation=Boolean.class, example="true")
    private final boolean resize;

    @Override
    public FrameRateSettings getFrameRateSettings() {
        return Objects.requireNonNullElseGet(this.frameRateSettings, () -> new FpsMode(25));
    }

    @Override
    public Hardware getHardware() {
        return this.hardware;
    }

    @Override
    public HardwareAcceleration getAcceleration() {
        return this.acceleration;
    }

    @Override
    public HardwareDecode getDecoding() {
        return this.decoding;
    }

    public boolean isMotion() {
        return this.motion;
    }

    public boolean isResize() {
        return this.resize;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HardwareSettingsImpl)) {
            return false;
        }
        HardwareSettingsImpl other = (HardwareSettingsImpl)o;
        if (this.isMotion() != other.isMotion()) {
            return false;
        }
        if (this.isResize() != other.isResize()) {
            return false;
        }
        Hardware this$hardware = this.getHardware();
        Hardware other$hardware = other.getHardware();
        if (this$hardware == null ? other$hardware != null : !((Object)((Object)this$hardware)).equals((Object)other$hardware)) {
            return false;
        }
        HardwareAcceleration this$acceleration = this.getAcceleration();
        HardwareAcceleration other$acceleration = other.getAcceleration();
        if (this$acceleration == null ? other$acceleration != null : !((Object)((Object)this$acceleration)).equals((Object)other$acceleration)) {
            return false;
        }
        HardwareDecode this$decoding = this.getDecoding();
        HardwareDecode other$decoding = other.getDecoding();
        if (this$decoding == null ? other$decoding != null : !((Object)((Object)this$decoding)).equals((Object)other$decoding)) {
            return false;
        }
        FrameRateSettings this$frameRateSettings = this.getFrameRateSettings();
        FrameRateSettings other$frameRateSettings = other.getFrameRateSettings();
        return !(this$frameRateSettings == null ? other$frameRateSettings != null : !this$frameRateSettings.equals(other$frameRateSettings));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isMotion() ? 79 : 97);
        result = result * 59 + (this.isResize() ? 79 : 97);
        Hardware $hardware = this.getHardware();
        result = result * 59 + ($hardware == null ? 43 : ((Object)((Object)$hardware)).hashCode());
        HardwareAcceleration $acceleration = this.getAcceleration();
        result = result * 59 + ($acceleration == null ? 43 : ((Object)((Object)$acceleration)).hashCode());
        HardwareDecode $decoding = this.getDecoding();
        result = result * 59 + ($decoding == null ? 43 : ((Object)((Object)$decoding)).hashCode());
        FrameRateSettings $frameRateSettings = this.getFrameRateSettings();
        result = result * 59 + ($frameRateSettings == null ? 43 : $frameRateSettings.hashCode());
        return result;
    }

    public String toString() {
        return "HardwareSettingsImpl(hardware=" + String.valueOf((Object)this.getHardware()) + ", acceleration=" + String.valueOf((Object)this.getAcceleration()) + ", decoding=" + String.valueOf((Object)this.getDecoding()) + ", frameRateSettings=" + String.valueOf(this.getFrameRateSettings()) + ", motion=" + this.isMotion() + ", resize=" + this.isResize() + ")";
    }

    public HardwareSettingsImpl(Hardware hardware, HardwareAcceleration acceleration, HardwareDecode decoding, FrameRateSettings frameRateSettings, boolean motion, boolean resize) {
        this.hardware = hardware;
        this.acceleration = acceleration;
        this.decoding = decoding;
        this.frameRateSettings = frameRateSettings;
        this.motion = motion;
        this.resize = resize;
    }
}

