/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.analytics.groups;

import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;

public final class AnalyticsGroupsUpdatedAuditMessage
extends ObjectUpdateAuditMessage<AnalyticsGroup> {
    private final int id;

    @Override
    protected String getTemplateId() {
        return "ANALYTICS_GROUP_UPDATED";
    }

    public AnalyticsGroupsUpdatedAuditMessage(AnalyticsGroup old, AnalyticsGroup updated) {
        super(old, updated);
        this.id = updated.getId();
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "AnalyticsGroupsUpdatedAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsGroupsUpdatedAuditMessage)) {
            return false;
        }
        AnalyticsGroupsUpdatedAuditMessage other = (AnalyticsGroupsUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.getId() == other.getId();
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsGroupsUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        return result;
    }
}

