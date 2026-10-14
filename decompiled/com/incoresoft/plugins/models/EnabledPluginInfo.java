/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;

@JsonDeserialize(builder=EnabledPluginInfoBuilder.class)
public class EnabledPluginInfo {
    @JsonProperty(value="plugin_id")
    private final String pluginId;
    @JsonProperty(value="enabled")
    private volatile boolean enabled;

    public EnabledPluginInfo(String pluginId, boolean enabled) {
        this.pluginId = Objects.requireNonNull(pluginId);
        this.enabled = enabled;
    }

    public static EnabledPluginInfoBuilder builder() {
        return new EnabledPluginInfoBuilder();
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @JsonProperty(value="enabled")
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EnabledPluginInfo)) {
            return false;
        }
        EnabledPluginInfo other = (EnabledPluginInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isEnabled() != other.isEnabled()) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        return !(this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EnabledPluginInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        return result;
    }

    public String toString() {
        return "EnabledPluginInfo(pluginId=" + this.getPluginId() + ", enabled=" + this.isEnabled() + ")";
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class EnabledPluginInfoBuilder {
        private String pluginId;
        private boolean enabled;

        EnabledPluginInfoBuilder() {
        }

        @JsonProperty(value="plugin_id")
        public EnabledPluginInfoBuilder pluginId(String pluginId) {
            this.pluginId = pluginId;
            return this;
        }

        @JsonProperty(value="enabled")
        public EnabledPluginInfoBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public EnabledPluginInfo build() {
            return new EnabledPluginInfo(this.pluginId, this.enabled);
        }

        public String toString() {
            return "EnabledPluginInfo.EnabledPluginInfoBuilder(pluginId=" + this.pluginId + ", enabled=" + this.enabled + ")";
        }
    }
}

