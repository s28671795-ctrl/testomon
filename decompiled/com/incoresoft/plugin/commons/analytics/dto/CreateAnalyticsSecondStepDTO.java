/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.settings.HardwareSettingsImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CreateAnalyticsSecondStepDTO {
    @JSONField(name="module")
    @Schema(name="module", description="Analytics module")
    @Valid
    @NotNull
    private SecondStepModule module;

    public SecondStepModule getModule() {
        return this.module;
    }

    public void setModule(SecondStepModule module) {
        this.module = module;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateAnalyticsSecondStepDTO)) {
            return false;
        }
        CreateAnalyticsSecondStepDTO other = (CreateAnalyticsSecondStepDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        SecondStepModule this$module = this.getModule();
        SecondStepModule other$module = other.getModule();
        return !(this$module == null ? other$module != null : !((Object)this$module).equals(other$module));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CreateAnalyticsSecondStepDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        SecondStepModule $module = this.getModule();
        result = result * 59 + ($module == null ? 43 : ((Object)$module).hashCode());
        return result;
    }

    public String toString() {
        return "CreateAnalyticsSecondStepDTO(module=" + String.valueOf(this.getModule()) + ")";
    }

    private static class SecondStepModule {
        @JSONField(name="hardware_settings")
        @Schema(name="hardware_settings", description="Hardware settings", implementation=HardwareSettingsImpl.class)
        @Valid
        @NotNull
        private HardwareSettingsImpl hardwareSettings;

        public HardwareSettingsImpl getHardwareSettings() {
            return this.hardwareSettings;
        }

        public void setHardwareSettings(HardwareSettingsImpl hardwareSettings) {
            this.hardwareSettings = hardwareSettings;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof SecondStepModule)) {
                return false;
            }
            SecondStepModule other = (SecondStepModule)o;
            if (!other.canEqual(this)) {
                return false;
            }
            HardwareSettingsImpl this$hardwareSettings = this.getHardwareSettings();
            HardwareSettingsImpl other$hardwareSettings = other.getHardwareSettings();
            return !(this$hardwareSettings == null ? other$hardwareSettings != null : !((Object)this$hardwareSettings).equals(other$hardwareSettings));
        }

        protected boolean canEqual(Object other) {
            return other instanceof SecondStepModule;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            HardwareSettingsImpl $hardwareSettings = this.getHardwareSettings();
            result = result * 59 + ($hardwareSettings == null ? 43 : ((Object)$hardwareSettings).hashCode());
            return result;
        }

        public String toString() {
            return "CreateAnalyticsSecondStepDTO.SecondStepModule(hardwareSettings=" + String.valueOf(this.getHardwareSettings()) + ")";
        }
    }
}

