/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public class AnalyticsWithGroupsParams {
    @JSONField(name="status")
    @JsonProperty(value="status")
    String status;
    @JSONField(name="plugins")
    @JsonProperty(value="plugins")
    Set<String> plugins;
    @JSONField(name="stream_uuids")
    @JsonProperty(value="stream_uuids")
    Set<UUID> streamUuids;

    public String getStatus() {
        return this.status;
    }

    public Set<String> getPlugins() {
        return this.plugins;
    }

    public Set<UUID> getStreamUuids() {
        return this.streamUuids;
    }

    @JsonProperty(value="status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(value="plugins")
    public void setPlugins(Set<String> plugins) {
        this.plugins = plugins;
    }

    @JsonProperty(value="stream_uuids")
    public void setStreamUuids(Set<UUID> streamUuids) {
        this.streamUuids = streamUuids;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsWithGroupsParams)) {
            return false;
        }
        AnalyticsWithGroupsParams other = (AnalyticsWithGroupsParams)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        Set<String> this$plugins = this.getPlugins();
        Set<String> other$plugins = other.getPlugins();
        if (this$plugins == null ? other$plugins != null : !((Object)this$plugins).equals(other$plugins)) {
            return false;
        }
        Set<UUID> this$streamUuids = this.getStreamUuids();
        Set<UUID> other$streamUuids = other.getStreamUuids();
        return !(this$streamUuids == null ? other$streamUuids != null : !((Object)this$streamUuids).equals(other$streamUuids));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsWithGroupsParams;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Set<String> $plugins = this.getPlugins();
        result = result * 59 + ($plugins == null ? 43 : ((Object)$plugins).hashCode());
        Set<UUID> $streamUuids = this.getStreamUuids();
        result = result * 59 + ($streamUuids == null ? 43 : ((Object)$streamUuids).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsWithGroupsParams(status=" + this.getStatus() + ", plugins=" + String.valueOf(this.getPlugins()) + ", streamUuids=" + String.valueOf(this.getStreamUuids()) + ")";
    }
}

