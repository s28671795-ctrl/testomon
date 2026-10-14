/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.models.analyticswsmessages;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.models.analyticswsmessages.AnalyticsWSStatusDTO;
import java.util.Objects;

public class AnalyticsNotificationStatusDTO
extends AnalyticsWSStatusDTO {
    @JSONField(serialize=false)
    private final AnalyticsStatus status;

    public AnalyticsNotificationStatusDTO(BaseAnalytics analytics) {
        super(analytics.getId(), analytics.getClientId());
        this.status = Objects.requireNonNull(analytics.getStatus());
    }

    @Override
    public String getMethod() {
        return "analytics_" + this.status.name();
    }

    public AnalyticsStatus getStatus() {
        return this.status;
    }
}

