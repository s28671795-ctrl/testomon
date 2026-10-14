/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.backup.ws.message.restore.server.ServerBackupRestoreWSMessage;

public class PluginInstallationProgressMessage
implements ServerBackupRestoreWSMessage {
    @JSONField(name="plugin_id")
    private String pluginId;
    @JSONField(name="plugin_type")
    private PluginType pluginType;
    @JSONField(name="downloaded_part")
    private double downloadedPart;

    public String getPluginId() {
        return this.pluginId;
    }

    public PluginType getPluginType() {
        return this.pluginType;
    }

    public double getDownloadedPart() {
        return this.downloadedPart;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public void setDownloadedPart(double downloadedPart) {
        this.downloadedPart = downloadedPart;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginInstallationProgressMessage)) {
            return false;
        }
        PluginInstallationProgressMessage other = (PluginInstallationProgressMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getDownloadedPart(), other.getDownloadedPart()) != 0) {
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
        return other instanceof PluginInstallationProgressMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $downloadedPart = Double.doubleToLongBits(this.getDownloadedPart());
        result = result * 59 + (int)($downloadedPart >>> 32 ^ $downloadedPart);
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        PluginType $pluginType = this.getPluginType();
        result = result * 59 + ($pluginType == null ? 43 : ((Object)((Object)$pluginType)).hashCode());
        return result;
    }

    public String toString() {
        return "PluginInstallationProgressMessage(pluginId=" + this.getPluginId() + ", pluginType=" + String.valueOf((Object)this.getPluginType()) + ", downloadedPart=" + this.getDownloadedPart() + ")";
    }

    public PluginInstallationProgressMessage() {
    }

    public PluginInstallationProgressMessage(String pluginId, PluginType pluginType, double downloadedPart) {
        this.pluginId = pluginId;
        this.pluginType = pluginType;
        this.downloadedPart = downloadedPart;
    }
}

