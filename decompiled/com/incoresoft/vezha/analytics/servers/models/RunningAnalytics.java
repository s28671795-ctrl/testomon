/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.models;

import com.incoresoft.vezha.analytics.errors.AnalyticsSideError;
import com.incoresoft.vezha.analytics.servers.dto.RunningAnalyticsDTO;
import com.incoresoft.vezha.analytics.servers.models.AnalyticsProcessStatus;
import com.incoresoft.vezha.analytics.servers.models.AnalyticsStreamStatus;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public final class RunningAnalytics {
    private final int id;
    private final long upTime;
    private final int gpuId;
    private final Set<AnalyticsSideError> errors;
    private final AnalyticsProcessStatus status;
    private final AnalyticsStreamStatus streamStatus;

    public RunningAnalytics(RunningAnalyticsDTO dto) {
        this.id = dto.getId();
        this.upTime = dto.getUpTime();
        this.gpuId = dto.getGpuId();
        this.errors = this.convertErrors(dto.getErrors());
        this.status = AnalyticsProcessStatus.fromWire(dto.getStatus());
        this.streamStatus = AnalyticsStreamStatus.fromWire(dto.getStreamStatus());
    }

    public boolean isConsumingGpuMemory() {
        if (this.status == null && this.streamStatus == null) {
            return true;
        }
        if (this.streamStatus == AnalyticsStreamStatus.offline) {
            return false;
        }
        return this.status == null || this.status == AnalyticsProcessStatus.working;
    }

    private Set<AnalyticsSideError> convertErrors(Set<String> errorStrings) {
        if (errorStrings == null) {
            return Collections.emptySet();
        }
        return errorStrings.stream().filter(error -> !error.equals("ERROR_PROCESS_MANAGER_UNKNOWN_ERROR")).map(AnalyticsSideError::new).collect(Collectors.toSet());
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

    public Set<AnalyticsSideError> getErrors() {
        return this.errors;
    }

    public AnalyticsProcessStatus getStatus() {
        return this.status;
    }

    public AnalyticsStreamStatus getStreamStatus() {
        return this.streamStatus;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RunningAnalytics)) {
            return false;
        }
        RunningAnalytics other = (RunningAnalytics)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getUpTime() != other.getUpTime()) {
            return false;
        }
        if (this.getGpuId() != other.getGpuId()) {
            return false;
        }
        Set<AnalyticsSideError> this$errors = this.getErrors();
        Set<AnalyticsSideError> other$errors = other.getErrors();
        if (this$errors == null ? other$errors != null : !((Object)this$errors).equals(other$errors)) {
            return false;
        }
        AnalyticsProcessStatus this$status = this.getStatus();
        AnalyticsProcessStatus other$status = other.getStatus();
        if (this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status)) {
            return false;
        }
        AnalyticsStreamStatus this$streamStatus = this.getStreamStatus();
        AnalyticsStreamStatus other$streamStatus = other.getStreamStatus();
        return !(this$streamStatus == null ? other$streamStatus != null : !((Object)((Object)this$streamStatus)).equals((Object)other$streamStatus));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $upTime = this.getUpTime();
        result = result * 59 + (int)($upTime >>> 32 ^ $upTime);
        result = result * 59 + this.getGpuId();
        Set<AnalyticsSideError> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        AnalyticsProcessStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        AnalyticsStreamStatus $streamStatus = this.getStreamStatus();
        result = result * 59 + ($streamStatus == null ? 43 : ((Object)((Object)$streamStatus)).hashCode());
        return result;
    }

    public String toString() {
        return "RunningAnalytics(id=" + this.getId() + ", upTime=" + this.getUpTime() + ", gpuId=" + this.getGpuId() + ", errors=" + String.valueOf(this.getErrors()) + ", status=" + String.valueOf((Object)this.getStatus()) + ", streamStatus=" + String.valueOf((Object)this.getStreamStatus()) + ")";
    }
}

