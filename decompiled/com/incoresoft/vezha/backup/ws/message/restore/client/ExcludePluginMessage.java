/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.client;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.ws.message.restore.client.ClientBackupRestoreWSMessage;

public class ExcludePluginMessage
implements ClientBackupRestoreWSMessage {
    @JSONField(name="plugin_id")
    private String pluginId;
    @JSONField(name="backup_hash")
    private String backupHash;
    @JSONField(name="plugin_type")
    private PluginType pluginType;

    public String getPluginId() {
        return this.pluginId;
    }

    @Override
    public String getBackupHash() {
        return this.backupHash;
    }

    public PluginType getPluginType() {
        return this.pluginType;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public void setBackupHash(String backupHash) {
        this.backupHash = backupHash;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExcludePluginMessage)) {
            return false;
        }
        ExcludePluginMessage other = (ExcludePluginMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        String this$backupHash = this.getBackupHash();
        String other$backupHash = other.getBackupHash();
        if (this$backupHash == null ? other$backupHash != null : !this$backupHash.equals(other$backupHash)) {
            return false;
        }
        PluginType this$pluginType = this.getPluginType();
        PluginType other$pluginType = other.getPluginType();
        return !(this$pluginType == null ? other$pluginType != null : !((Object)((Object)this$pluginType)).equals((Object)other$pluginType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExcludePluginMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        String $backupHash = this.getBackupHash();
        result = result * 59 + ($backupHash == null ? 43 : $backupHash.hashCode());
        PluginType $pluginType = this.getPluginType();
        result = result * 59 + ($pluginType == null ? 43 : ((Object)((Object)$pluginType)).hashCode());
        return result;
    }

    public String toString() {
        return "ExcludePluginMessage(pluginId=" + this.getPluginId() + ", backupHash=" + this.getBackupHash() + ", pluginType=" + String.valueOf((Object)this.getPluginType()) + ")";
    }
}

