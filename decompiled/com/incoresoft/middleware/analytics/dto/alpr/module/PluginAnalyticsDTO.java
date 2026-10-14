/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.analytics.module.alpr.AlprAnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsDTO", description="Analytics class structure for ALPR module")
public class PluginAnalyticsDTO
extends AnalyticsDTO {
    private Analytics inner;

    public PluginAnalyticsDTO(@NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress) {
        super(user, inner, stream, progress);
    }

    @Override
    @JSONField(name="module")
    @JsonProperty(value="module")
    @Schema(name="module", description="Analytics module settings", implementation=AlprAnalyticsModule.class)
    public AnalyticsModule getModule() {
        return this.inner.getModule();
    }
}

