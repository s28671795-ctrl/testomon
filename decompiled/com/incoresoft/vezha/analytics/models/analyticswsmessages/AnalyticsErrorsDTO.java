/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.models.analyticswsmessages;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsErrorDTO;
import com.incoresoft.vezha.analytics.models.analyticswsmessages.AnalyticsWSStatusDTO;
import java.util.List;

public class AnalyticsErrorsDTO
extends AnalyticsWSStatusDTO {
    @JSONField(name="errors")
    private final List<AnalyticsErrorDTO> errors;

    public AnalyticsErrorsDTO(Analytics analytics) {
        super(analytics.getId(), analytics.getClientId());
        this.errors = analytics.getErrors().stream().map(AnalyticsErrorDTO::new).toList();
    }

    @Override
    public String getMethod() {
        return "analytics_error";
    }

    public List<AnalyticsErrorDTO> getErrors() {
        return this.errors;
    }
}

