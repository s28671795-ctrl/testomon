/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.models.AnalyticsModes;
import com.incoresoft.middleware.analytics.models.module.DefaultAnalyticsModule;
import com.incoresoft.middleware.analytics.models.settings.AdvancedSettingsImpl;
import com.incoresoft.middleware.analytics.module.smartva.SmartVaRule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.annotations.Module;
import com.incoresoft.middleware.model.analytics.module.annotations.ServiceRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Module(analyticPluginName="smart_va", serviceRequirements={@ServiceRequirement(value=AnalyticsTypes.smart_va)})
@Schema(name="SmartVaModule", description="Smart-VA module class structure")
public class SmartVaModule
extends DefaultAnalyticsModule
implements AnalyticsModule {
    @JSONField(name="mode", defaultValue="alert")
    @Schema(name="mode", description="Analytics mode", example="alert", implementation=AnalyticsModes.class)
    @NotNull
    private AnalyticsModes mode = AnalyticsModes.alert;
    @JSONField(name="advanced_settings")
    @Schema(name="advanced_settings", description="Advanced settings", implementation=AdvancedSettingsImpl.class)
    @Valid
    private AdvancedSettingsImpl advancedSettings;
    @JSONField(name="rules", defaultValue="[]")
    @ArraySchema(arraySchema=@Schema(name="rules", description="Smart VA rules"), schema=@Schema(name="rules", implementation=SmartVaRule.class))
    private @NotNull List<@Valid SmartVaRule> rules;

    public AnalyticsModes getMode() {
        return this.mode;
    }

    public AdvancedSettingsImpl getAdvancedSettings() {
        return this.advancedSettings;
    }

    public List<@Valid SmartVaRule> getRules() {
        return this.rules;
    }

    public void setMode(AnalyticsModes mode) {
        this.mode = mode;
    }

    public void setAdvancedSettings(AdvancedSettingsImpl advancedSettings) {
        this.advancedSettings = advancedSettings;
    }

    public void setRules(List<@Valid SmartVaRule> rules) {
        this.rules = rules;
    }
}

