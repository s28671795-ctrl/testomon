/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.status;

import java.time.Instant;

public final class AnalyticsHeartBeat {
    private final String serverId;
    private final Integer gpuId;
    private final Instant timestamp;

    public AnalyticsHeartBeat(String serverId, int gpuId) {
        this.serverId = serverId;
        this.gpuId = gpuId;
        this.timestamp = Instant.now();
    }

    public String getServerId() {
        return this.serverId;
    }

    public Integer getGpuId() {
        return this.gpuId;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsHeartBeat)) {
            return false;
        }
        AnalyticsHeartBeat other = (AnalyticsHeartBeat)o;
        Integer this$gpuId = this.getGpuId();
        Integer other$gpuId = other.getGpuId();
        if (this$gpuId == null ? other$gpuId != null : !((Object)this$gpuId).equals(other$gpuId)) {
            return false;
        }
        String this$serverId = this.getServerId();
        String other$serverId = other.getServerId();
        if (this$serverId == null ? other$serverId != null : !this$serverId.equals(other$serverId)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $gpuId = this.getGpuId();
        result = result * 59 + ($gpuId == null ? 43 : ((Object)$gpuId).hashCode());
        String $serverId = this.getServerId();
        result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsHeartBeat(serverId=" + this.getServerId() + ", gpuId=" + this.getGpuId() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public AnalyticsHeartBeat(String serverId, Integer gpuId, Instant timestamp) {
        this.serverId = serverId;
        this.gpuId = gpuId;
        this.timestamp = timestamp;
    }
}

