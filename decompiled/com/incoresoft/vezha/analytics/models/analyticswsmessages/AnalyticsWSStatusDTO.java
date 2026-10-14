/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.models.analyticswsmessages;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class AnalyticsWSStatusDTO {
    @JSONField(name="id")
    private final int vaId;
    @JSONField(name="client_id")
    private final int clientId;

    @JSONField(name="method")
    public abstract String getMethod();

    public int getVaId() {
        return this.vaId;
    }

    public int getClientId() {
        return this.clientId;
    }

    public AnalyticsWSStatusDTO(int vaId, int clientId) {
        this.vaId = vaId;
        this.clientId = clientId;
    }
}

