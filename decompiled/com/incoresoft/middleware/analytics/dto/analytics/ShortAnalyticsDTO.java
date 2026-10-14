/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public class ShortAnalyticsDTO {
    @Schema(name="id", description="Analytics id", implementation=Integer.class, example="1")
    @JsonProperty(value="id")
    @JSONField(name="id")
    private Integer id;
    @Schema(name="name", description="Analytics name", implementation=String.class)
    @JsonProperty(value="name")
    @JSONField(name="name")
    private String name;
    @Deprecated
    @Schema(name="stream_id", description="Stream ID Field", example="1", implementation=Integer.class)
    @JsonProperty(value="stream_id")
    @JSONField(name="stream_id")
    private Integer streamId;
    @Schema(name="stream_uuid", description="Stream UUID", example="1917f6af-43bd-47fa-b1f2-a7584173ea91", implementation=UUID.class)
    @JsonProperty(value="stream_uuid")
    @JSONField(name="stream_uuid")
    private UUID streamUuid;
    @Schema(name="status", description="Analytics status", implementation=AnalyticsStatus.class)
    @JsonProperty(value="status")
    @JSONField(name="status")
    private String status;

    public ShortAnalyticsDTO(Analytics analytics) {
        this.id = analytics.getId();
        this.name = analytics.getName();
        this.streamId = analytics.getStreamId();
        this.streamUuid = analytics.getStreamUUID();
        this.status = analytics.getStatus().toString();
    }

    public ShortAnalyticsDTO(int id) {
        this.id = id;
        this.name = "Deleted analytics";
        this.streamId = null;
        this.streamUuid = null;
        this.status = AnalyticsStatus.deleted.name();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public Integer getStreamId() {
        return this.streamId;
    }

    public UUID getStreamUuid() {
        return this.streamUuid;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonProperty(value="id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty(value="name")
    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    @JsonProperty(value="stream_id")
    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    @JsonProperty(value="stream_uuid")
    public void setStreamUuid(UUID streamUuid) {
        this.streamUuid = streamUuid;
    }

    @JsonProperty(value="status")
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ShortAnalyticsDTO)) {
            return false;
        }
        ShortAnalyticsDTO other = (ShortAnalyticsDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$streamId = this.getStreamId();
        Integer other$streamId = other.getStreamId();
        if (this$streamId == null ? other$streamId != null : !((Object)this$streamId).equals(other$streamId)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        UUID this$streamUuid = this.getStreamUuid();
        UUID other$streamUuid = other.getStreamUuid();
        if (this$streamUuid == null ? other$streamUuid != null : !((Object)this$streamUuid).equals(other$streamUuid)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ShortAnalyticsDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $streamId = this.getStreamId();
        result = result * 59 + ($streamId == null ? 43 : ((Object)$streamId).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        UUID $streamUuid = this.getStreamUuid();
        result = result * 59 + ($streamUuid == null ? 43 : ((Object)$streamUuid).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "ShortAnalyticsDTO(id=" + this.getId() + ", name=" + this.getName() + ", streamId=" + this.getStreamId() + ", streamUuid=" + String.valueOf(this.getStreamUuid()) + ", status=" + this.getStatus() + ")";
    }

    public ShortAnalyticsDTO() {
    }

    public ShortAnalyticsDTO(Integer id, String name, Integer streamId, UUID streamUuid, String status) {
        this.id = id;
        this.name = name;
        this.streamId = streamId;
        this.streamUuid = streamUuid;
        this.status = status;
    }
}

