/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsErrorDTO;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@Schema(name="RunningAnalyticsFrontDTO", description="Running analytics class structure")
public class RunningAnalyticsFrontDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Analytics ID", example="30")
    private final int id;
    @JSONField(name="up_time")
    @Schema(name="up_time", description="Analytics uptime in milliseconds", implementation=Long.class, example="123000")
    private final long upTime;
    @JSONField(name="gpu_id")
    @Schema(name="gpu_id", description="Id of GPU analytics is being run on(-1 if analytics works on CPU)", implementation=Integer.class, example="2")
    private final int gpuId;
    @JSONField(name="name")
    @Schema(name="name", description="Analytics name", implementation=String.class, example="LPR Test")
    private String name;
    @JSONField(name="type")
    @Schema(name="type", description="Analytics type", implementation=String.class, example="alpr")
    private String type;
    @JSONField(name="url_prefix")
    @Schema(name="url_prefix", description="Url prefix of plugin which created this analytics", implementation=String.class, example="alpr")
    private String urlPrefix;
    @JSONField(name="errors")
    @Schema(name="errors", description="Analytics errors")
    private List<AnalyticsErrorDTO> errors;
    @JSONField(name="status")
    @Schema(name="status", description="Analytics process status reported by the analytics server. Null when the server does not report it.", implementation=String.class, example="working", nullable=true)
    private final String status;
    @JSONField(name="stream_status")
    @Schema(name="stream_status", description="Source stream status reported by the analytics server. Null when the server does not report it.", implementation=String.class, example="online", nullable=true)
    private final String streamStatus;

    public RunningAnalyticsFrontDTO(@NotNull RunningAnalytics runningAnalytics, AnalyticsManager analyticsManager) {
        this.id = runningAnalytics.getId();
        this.upTime = runningAnalytics.getUpTime();
        this.gpuId = runningAnalytics.getGpuId();
        this.status = runningAnalytics.getStatus() == null ? null : runningAnalytics.getStatus().name();
        this.streamStatus = runningAnalytics.getStreamStatus() == null ? null : runningAnalytics.getStreamStatus().name();
        Optional.ofNullable((Analytics)analyticsManager.get(this.id)).ifPresent(analytics -> {
            this.name = analytics.getName();
            this.type = analytics.getPluginName();
            this.urlPrefix = analytics.getPluginName();
            this.errors = analytics.getErrors().stream().map(AnalyticsErrorDTO::new).toList();
        });
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

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getUrlPrefix() {
        return this.urlPrefix;
    }

    public List<AnalyticsErrorDTO> getErrors() {
        return this.errors;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStreamStatus() {
        return this.streamStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void setErrors(List<AnalyticsErrorDTO> errors2) {
        this.errors = errors2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RunningAnalyticsFrontDTO)) {
            return false;
        }
        RunningAnalyticsFrontDTO other = (RunningAnalyticsFrontDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getUpTime() != other.getUpTime()) {
            return false;
        }
        if (this.getGpuId() != other.getGpuId()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$urlPrefix = this.getUrlPrefix();
        String other$urlPrefix = other.getUrlPrefix();
        if (this$urlPrefix == null ? other$urlPrefix != null : !this$urlPrefix.equals(other$urlPrefix)) {
            return false;
        }
        List<AnalyticsErrorDTO> this$errors = this.getErrors();
        List<AnalyticsErrorDTO> other$errors = other.getErrors();
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

    protected boolean canEqual(Object other) {
        return other instanceof RunningAnalyticsFrontDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $upTime = this.getUpTime();
        result = result * 59 + (int)($upTime >>> 32 ^ $upTime);
        result = result * 59 + this.getGpuId();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $urlPrefix = this.getUrlPrefix();
        result = result * 59 + ($urlPrefix == null ? 43 : $urlPrefix.hashCode());
        List<AnalyticsErrorDTO> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        String $streamStatus = this.getStreamStatus();
        result = result * 59 + ($streamStatus == null ? 43 : $streamStatus.hashCode());
        return result;
    }

    public String toString() {
        return "RunningAnalyticsFrontDTO(id=" + this.getId() + ", upTime=" + this.getUpTime() + ", gpuId=" + this.getGpuId() + ", name=" + this.getName() + ", type=" + this.getType() + ", urlPrefix=" + this.getUrlPrefix() + ", errors=" + String.valueOf(this.getErrors()) + ", status=" + this.getStatus() + ", streamStatus=" + this.getStreamStatus() + ")";
    }
}

