/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.ws.message.restore.server.ServerBackupRestoreWSMessage;

public class PluginExclusionCompletedMessage
implements ServerBackupRestoreWSMessage {
    @JSONField(name="plugin_id")
    private String pluginId;
    @JSONField(name="plugin_type")
    private PluginType pluginType;

    public String getPluginId() {
        return this.pluginId;
    }

    public PluginType getPluginType() {
        return this.pluginType;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginExclusionCompletedMessage)) {
            return false;
        }
        PluginExclusionCompletedMessage other = (PluginExclusionCompletedMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        PluginType this$pluginType = this.getPluginType();
        PluginType other$pluginType = other.getPluginType();
        return !(this$pluginType == null ? other$pluginType != null : !((Object)((Object)this$pluginType)).equals((Object)other$pluginType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PluginExclusionCompletedMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        PluginType $pluginType = this.getPluginType();
        result = result * 59 + ($pluginType == null ? 43 : ((Object)((Object)$pluginType)).hashCode());
        return result;
    }

    public String toString() {
        return "PluginExclusionCompletedMessage(pluginId=" + this.getPluginId() + ", pluginType=" + String.valueOf((Object)this.getPluginType()) + ")";
    }

    public PluginExclusionCompletedMessage() {
    }

    public PluginExclusionCompletedMessage(String pluginId, PluginType pluginType) {
        this.pluginId = pluginId;
        this.pluginType = pluginType;
    }
}

