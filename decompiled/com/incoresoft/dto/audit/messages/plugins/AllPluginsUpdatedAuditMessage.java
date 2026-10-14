/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.plugins;

import com.incoresoft.dto.audit.messages.plugins.PluginUpdatedAuditMessage;
import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import java.util.Collection;
import java.util.Collections;

public final class AllPluginsUpdatedAuditMessage
extends AuditMessage {
    private final Collection<PluginUpdatedAuditMessage> pluginUpdates;

    public AllPluginsUpdatedAuditMessage() {
        this(Collections.emptyList());
    }

    public AllPluginsUpdatedAuditMessage(Collection<PluginUpdatedAuditMessage> pluginUpdates) {
        this.pluginUpdates = pluginUpdates;
    }

    @Override
    protected String getTemplateId() {
        return "ALL_PLUGINS_UPDATED";
    }

    public Collection<PluginUpdatedAuditMessage> getPluginUpdates() {
        return this.pluginUpdates;
    }

    public String toString() {
        return "AllPluginsUpdatedAuditMessage(pluginUpdates=" + String.valueOf(this.getPluginUpdates()) + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllPluginsUpdatedAuditMessage)) {
            return false;
        }
        AllPluginsUpdatedAuditMessage other = (AllPluginsUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Collection<PluginUpdatedAuditMessage> this$pluginUpdates = this.getPluginUpdates();
        Collection<PluginUpdatedAuditMessage> other$pluginUpdates = other.getPluginUpdates();
        return !(this$pluginUpdates == null ? other$pluginUpdates != null : !((Object)this$pluginUpdates).equals(other$pluginUpdates));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AllPluginsUpdatedAuditMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Collection<PluginUpdatedAuditMessage> $pluginUpdates = this.getPluginUpdates();
        result = result * 59 + ($pluginUpdates == null ? 43 : ((Object)$pluginUpdates).hashCode());
        return result;
    }
}

