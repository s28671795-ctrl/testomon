/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.operatorai.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.module.operatorai.OperatorAiAnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="Analytics DTO for Operator AI module")
public class PluginAnalyticsDTO
extends AnalyticsDTO {
    @Override
    @JSONField(name="module")
    @JsonProperty(value="module")
    @Schema(name="module", description="Analytics module settings", implementation=OperatorAiAnalyticsModule.class)
    public AnalyticsModule getModule() {
        return super.getModule();
    }
}

