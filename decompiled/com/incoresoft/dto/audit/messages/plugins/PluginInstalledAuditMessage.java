/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.plugins;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public final class PluginInstalledAuditMessage
extends AuditMessage {
    private final String id;

    @Override
    protected String getTemplateId() {
        return "PLUGIN_INSTALLED";
    }

    public PluginInstalledAuditMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return "PluginInstalledAuditMessage(id=" + this.getId() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginInstalledAuditMessage)) {
            return false;
        }
        PluginInstalledAuditMessage other = (PluginInstalledAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PluginInstalledAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}

