/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.military.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.analytics.module.military.MilitaryAnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsDTO", description="Analytics class structure for Gun detector module")
public class PluginAnalyticsDTO
extends AnalyticsDTO {
    private Analytics inner;

    public PluginAnalyticsDTO(@NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress) {
        super(user, inner, stream, progress);
    }

    @Override
    @JSONField(name="module")
    @Schema(name="module", description="Module", implementation=MilitaryAnalyticsModule.class)
    public AnalyticsModule getModule() {
        return this.inner.getModule();
    }
}

