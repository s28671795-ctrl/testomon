/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.models.PluginType;

public class PluginInstallationProgressEvent
implements PluginEvent {
    @JsonProperty(value="plugin_id")
    @JSONField(name="plugin_id")
    private String pluginId;
    @JsonProperty(value="plugin_type")
    @JSONField(name="plugin_type")
    private PluginType pluginType;
    @JsonProperty(value="downloaded_part")
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

    @JsonProperty(value="plugin_id")
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    @JsonProperty(value="plugin_type")
    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    @JsonProperty(value="downloaded_part")
    public void setDownloadedPart(double downloadedPart) {
        this.downloadedPart = downloadedPart;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginInstallationProgressEvent)) {
            return false;
        }
        PluginInstallationProgressEvent other = (PluginInstallationProgressEvent)o;
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
        return other instanceof PluginInstallationProgressEvent;
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
        return "PluginInstallationProgressEvent(pluginId=" + this.getPluginId() + ", pluginType=" + String.valueOf((Object)this.getPluginType()) + ", downloadedPart=" + this.getDownloadedPart() + ")";
    }

    public PluginInstallationProgressEvent() {
    }

    public PluginInstallationProgressEvent(String pluginId, PluginType pluginType, double downloadedPart) {
        this.pluginId = pluginId;
        this.pluginType = pluginType;
        this.downloadedPart = downloadedPart;
    }
}

