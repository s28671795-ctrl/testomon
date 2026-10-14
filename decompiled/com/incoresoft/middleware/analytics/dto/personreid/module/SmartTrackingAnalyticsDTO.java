/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.personreid.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.analytics.module.personreid.SmartTrackingAnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsDTO", description="Analytics class structure for Smart tracking module")
public class SmartTrackingAnalyticsDTO
extends AnalyticsDTO {
    private Analytics inner;

    public SmartTrackingAnalyticsDTO(@NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress) {
        super(user, inner, stream, progress);
    }

    @Override
    @JSONField(name="module")
    @Schema(name="module", description="Module", implementation=SmartTrackingAnalyticsModule.class)
    public AnalyticsModule getModule() {
        return this.inner.getModule();
    }
}

