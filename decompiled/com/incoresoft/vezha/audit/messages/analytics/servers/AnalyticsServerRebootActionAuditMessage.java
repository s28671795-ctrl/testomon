/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit.messages.analytics.servers;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;

public final class AnalyticsServerRebootActionAuditMessage
extends AuditMessage {
    private final String id;
    private final String name;

    public AnalyticsServerRebootActionAuditMessage(FrontServerDTO server) {
        this.id = server.getId();
        this.name = server.getName();
    }

    @Override
    protected String getTemplateId() {
        return "ANALYTICS_SERVER_REBOOTED";
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "AnalyticsServerRebootActionAuditMessage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsServerRebootActionAuditMessage)) {
            return false;
        }
        AnalyticsServerRebootActionAuditMessage other = (AnalyticsServerRebootActionAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsServerRebootActionAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

