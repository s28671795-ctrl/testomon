/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.analytics;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public class AnalyticsUpdatedAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    @Override
    protected String getTemplateId() {
        return "ANALYTICS_UPDATED";
    }

    public AnalyticsUpdatedAuditMessage(Analytics analytics) {
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
        if (!(o instanceof AnalyticsUpdatedAuditMessage)) {
            return false;
        }
        AnalyticsUpdatedAuditMessage other = (AnalyticsUpdatedAuditMessage)o;
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
        return other instanceof AnalyticsUpdatedAuditMessage;
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
        return "AnalyticsUpdatedAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}

