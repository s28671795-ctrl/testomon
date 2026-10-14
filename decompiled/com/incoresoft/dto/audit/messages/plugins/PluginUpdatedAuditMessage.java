/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.plugins;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class PluginUpdatedAuditMessage
extends AuditMessage {
    private final String id;
    private final String from;
    private final String to;

    @Override
    protected String getTemplateId() {
        return "PLUGIN_UPDATED";
    }

    public PluginUpdatedAuditMessage(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return this.id;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String toString() {
        return "PluginUpdatedAuditMessage(id=" + this.getId() + ", from=" + this.getFrom() + ", to=" + this.getTo() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginUpdatedAuditMessage)) {
            return false;
        }
        PluginUpdatedAuditMessage other = (PluginUpdatedAuditMessage)o;
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
        String this$from = this.getFrom();
        String other$from = other.getFrom();
        if (this$from == null ? other$from != null : !this$from.equals(other$from)) {
            return false;
        }
        String this$to = this.getTo();
        String other$to = other.getTo();
        return !(this$to == null ? other$to != null : !this$to.equals(other$to));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PluginUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $from = this.getFrom();
        result = result * 59 + ($from == null ? 43 : $from.hashCode());
        String $to = this.getTo();
        result = result * 59 + ($to == null ? 43 : $to.hashCode());
        return result;
    }
}

