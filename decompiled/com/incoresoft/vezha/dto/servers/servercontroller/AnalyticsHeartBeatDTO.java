/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.servercontroller;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.analytics.status.AnalyticsHeartBeat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public final class AnalyticsHeartBeatDTO {
    @JSONField(name="server_id")
    @Schema(name="server_id", description="Server id", implementation=String.class, example="Server 1", nullable=true)
    private final String serverId;
    @JSONField(name="gpu_id")
    @Schema(name="gpu_id", description="GPU id", implementation=Integer.class, example="1")
    private final Integer gpuId;
    @JSONField(name="timestamp")
    @Schema(name="timestamp", description="Timestamp", implementation=Long.class)
    private final Instant timestamp;

    public AnalyticsHeartBeatDTO(AnalyticsHeartBeat entity) {
        this.serverId = entity.getServerId();
        this.gpuId = entity.getGpuId();
        this.timestamp = entity.getTimestamp();
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
        if (!(o instanceof AnalyticsHeartBeatDTO)) {
            return false;
        }
        AnalyticsHeartBeatDTO other = (AnalyticsHeartBeatDTO)o;
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
        return "AnalyticsHeartBeatDTO(serverId=" + this.getServerId() + ", gpuId=" + this.getGpuId() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public AnalyticsHeartBeatDTO(String serverId, Integer gpuId, Instant timestamp) {
        this.serverId = serverId;
        this.gpuId = gpuId;
        this.timestamp = timestamp;
    }
}

