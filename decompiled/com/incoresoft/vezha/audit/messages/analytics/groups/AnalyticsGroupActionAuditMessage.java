/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.analytics.groups;

import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class AnalyticsGroupActionAuditMessage
extends AuditMessage {
    private final int id;
    private final String name;

    public AnalyticsGroupActionAuditMessage(AnalyticsGroup list) {
        this.id = list.getId();
        this.name = list.getName();
    }

    @Override
    protected String getTemplateId() {
        return "ANALYTICS_GROUP_ACTION";
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "AnalyticsGroupActionAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsGroupActionAuditMessage)) {
            return false;
        }
        AnalyticsGroupActionAuditMessage other = (AnalyticsGroupActionAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
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
        return other instanceof AnalyticsGroupActionAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

