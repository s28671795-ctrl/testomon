/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.models.PluginType;

public class AllPluginsUpdatedEvent
implements PluginEvent {
    @JsonProperty(value="plugin_type")
    @JSONField(name="plugin_type")
    private PluginType pluginType;

    public PluginType getPluginType() {
        return this.pluginType;
    }

    @JsonProperty(value="plugin_type")
    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AllPluginsUpdatedEvent)) {
            return false;
        }
        AllPluginsUpdatedEvent other = (AllPluginsUpdatedEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        PluginType this$pluginType = this.getPluginType();
        PluginType other$pluginType = other.getPluginType();
        return !(this$pluginType == null ? other$pluginType != null : !((Object)((Object)this$pluginType)).equals((Object)other$pluginType));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AllPluginsUpdatedEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        PluginType $pluginType = this.getPluginType();
        result = result * 59 + ($pluginType == null ? 43 : ((Object)((Object)$pluginType)).hashCode());
        return result;
    }

    public String toString() {
        return "AllPluginsUpdatedEvent(pluginType=" + String.valueOf((Object)this.getPluginType()) + ")";
    }

    public AllPluginsUpdatedEvent() {
    }

    public AllPluginsUpdatedEvent(PluginType pluginType) {
        this.pluginType = pluginType;
    }
}

