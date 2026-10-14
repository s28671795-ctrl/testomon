/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.ws.message.restore.server.ServerBackupRestoreWSMessage;

public class AllPluginsInstallationFailedMessage
implements ServerBackupRestoreWSMessage {
    @JSONField(name="plugin_type")
    private PluginType pluginType;

    public PluginType getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllPluginsInstallationFailedMessage)) {
            return false;
        }
        AllPluginsInstallationFailedMessage other = (AllPluginsInstallationFailedMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        PluginType this$pluginType = this.getPluginType();
        PluginType other$pluginType = other.getPluginType();
        return !(this$pluginType == null ? other$pluginType != null : !((Object)((Object)this$pluginType)).equals((Object)other$pluginType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AllPluginsInstallationFailedMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        PluginType $pluginType = this.getPluginType();
        result = result * 59 + ($pluginType == null ? 43 : ((Object)((Object)$pluginType)).hashCode());
        return result;
    }

    public String toString() {
        return "AllPluginsInstallationFailedMessage(pluginType=" + String.valueOf((Object)this.getPluginType()) + ")";
    }

    public AllPluginsInstallationFailedMessage() {
    }

    public AllPluginsInstallationFailedMessage(PluginType pluginType) {
        this.pluginType = pluginType;
    }
}

