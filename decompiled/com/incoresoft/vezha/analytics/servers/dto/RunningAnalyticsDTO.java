/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsError;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(name="RunningAnalyticsDTO", description="Running analytics class structure")
public final class RunningAnalyticsDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Analytics ID", implementation=Integer.class, example="1")
    private final int id;
    @JSONField(name="up_time")
    @Schema(name="up_time", description="Video analytics uptime in seconds", implementation=Long.class, example="1000")
    private final long upTime;
    @JSONField(name="gpu_id")
    @Schema(name="gpu_id", description="Video analytics started GPU ID", implementation=Integer.class, example="1")
    private final int gpuId;
    @JSONField(name="errors")
    @ArraySchema(schema=@Schema(name="errors", description="Video analytics errors", implementation=String.class, example="SOME_ERROR_LOCALE_KEY"))
    private final Set<String> errors;
    @JSONField(name="status")
    @Schema(name="status", description="Analytics process status reported by the analytics server", implementation=String.class, example="working", nullable=true)
    private final String status;
    @JSONField(name="stream_status")
    @Schema(name="stream_status", description="Source stream status reported by the analytics server", implementation=String.class, example="online", nullable=true)
    private final String streamStatus;

    public RunningAnalyticsDTO(RunningAnalytics entity) {
        this.id = entity.getId();
        this.upTime = entity.getUpTime();
        this.gpuId = entity.getGpuId();
        this.errors = entity.getErrors().stream().map(AnalyticsError::getLocaleKey).collect(Collectors.toSet());
        this.status = entity.getStatus() == null ? null : entity.getStatus().name();
        this.streamStatus = entity.getStreamStatus() == null ? null : entity.getStreamStatus().name();
    }

    public int getId() {
        return this.id;
    }

    public long getUpTime() {
        return this.upTime;
    }

    public int getGpuId() {
        return this.gpuId;
    }

    public Set<String> getErrors() {
        return this.errors;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStreamStatus() {
        return this.streamStatus;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RunningAnalyticsDTO)) {
            return false;
        }
        RunningAnalyticsDTO other = (RunningAnalyticsDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getUpTime() != other.getUpTime()) {
            return false;
        }
        if (this.getGpuId() != other.getGpuId()) {
            return false;
        }
        Set<String> this$errors = this.getErrors();
        Set<String> other$errors = other.getErrors();
        if (this$errors == null ? other$errors != null : !((Object)this$errors).equals(other$errors)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        String this$streamStatus = this.getStreamStatus();
        String other$streamStatus = other.getStreamStatus();
        return !(this$streamStatus == null ? other$streamStatus != null : !this$streamStatus.equals(other$streamStatus));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $upTime = this.getUpTime();
        result = result * 59 + (int)($upTime >>> 32 ^ $upTime);
        result = result * 59 + this.getGpuId();
        Set<String> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $streamStatus = this.getStreamStatus();
        result = result * 59 + ($streamStatus == null ? 43 : $streamStatus.hashCode());
        return result;
    }

    public String toString() {
        return "RunningAnalyticsDTO(id=" + this.getId() + ", upTime=" + this.getUpTime() + ", gpuId=" + this.getGpuId() + ", errors=" + String.valueOf(this.getErrors()) + ", status=" + this.getStatus() + ", streamStatus=" + this.getStreamStatus() + ")";
    }

    public RunningAnalyticsDTO(int id, long upTime, int gpuId, Set<String> errors2, String status, String streamStatus) {
        this.id = id;
        this.upTime = upTime;
        this.gpuId = gpuId;
        this.errors = errors2;
        this.status = status;
        this.streamStatus = streamStatus;
    }
}

