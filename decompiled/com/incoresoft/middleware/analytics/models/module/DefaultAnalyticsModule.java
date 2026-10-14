/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.settings.HardwareSettingsImpl;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Schema(name="DefaultAnalyticsModule", description="Default analytics module class structure")
public class DefaultAnalyticsModule {
    public static final String DEFAULT_ZONE = "[{\"id\":\"default-zone-id\",\"points\":[{\"x\":0.0,\"y\":0.0},{\"x\":0.0,\"y\":1.0},{\"x\":1.0,\"y\":1.0},{\"x\":1.0,\"y\":0.0}]}]";
    @JSONField(name="hardware_settings")
    @Schema(name="hardware_settings", description="Hardware settings", implementation=HardwareSettingsImpl.class)
    @Valid
    private HardwareSettingsImpl hardwareSettings;

    private Module getModule() {
        Module module = this.getClass().getAnnotation(Module.class);
        if (module == null) {
            throw new RuntimeException(String.format("Annotation @Module not found, did you forget to annotate your module class %s ?", this.getClass().getSimpleName()));
        }
        return module;
    }

    @JSONField(serialize=false)
    @Schema(hidden=true)
    public List<AnalyticsTypes> getAnalyticsTypes() {
        return Arrays.stream(this.getModule().serviceRequirements()).map(ServiceRequirement::value).collect(Collectors.toList());
    }

    @JSONField(serialize=false)
    @Schema(hidden=true)
    public String getAnalyticPluginName() {
        return this.getModule().analyticPluginName();
    }

    public void setHardwareSettings(HardwareSettingsImpl hardwareSettings) {
        this.hardwareSettings = hardwareSettings;
    }

    public HardwareSettingsImpl getHardwareSettings() {
        return this.hardwareSettings;
    }
}

