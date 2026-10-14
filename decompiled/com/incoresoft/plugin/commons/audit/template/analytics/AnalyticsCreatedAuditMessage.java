/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.analytics;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public class AnalyticsCreatedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    @Override
    protected String getTemplateId() {
        return "ANALYTICS_CREATED";
    }

    public AnalyticsCreatedAuditMessage(Analytics analytics) {
        this.id = analytics.getId();
        this.name = analytics.getName();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsCreatedAuditMessage)) {
            return false;
        }
        AnalyticsCreatedAuditMessage other = (AnalyticsCreatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsCreatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsCreatedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}

