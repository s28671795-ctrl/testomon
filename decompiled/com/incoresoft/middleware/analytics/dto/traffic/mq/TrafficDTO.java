/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.traffic.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.traffic.mq.TrafficCountDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@AnalyticsToken(value="detection")
@Schema(name="TrafficDTO", description="Traffic class structure")
public final class TrafficDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    @Schema(name="va_id", description="Video analytics ID", implementation=Integer.class, example="1")
    private final int vaId;
    @JSONField(name="timestamp")
    @Schema(name="timestamp", description="Date and time of event as unix timestamp(in milliseconds)", implementation=Long.class, example="1234567890")
    private final Instant timestamp;
    @JSONField(name="data")
    private final List<TrafficCountDTO> data;
    @JSONField(name="millis_from_start")
    private final long millisFromStart;

    @Override
    public Instant getTimestamp() {
        return this.timestamp == null ? Instant.now() : this.timestamp;
    }

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public List<TrafficCountDTO> getData() {
        return this.data;
    }

    public long getMillisFromStart() {
        return this.millisFromStart;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrafficDTO)) {
            return false;
        }
        TrafficDTO other = (TrafficDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getMillisFromStart() != other.getMillisFromStart()) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        List<TrafficCountDTO> this$data = this.getData();
        List<TrafficCountDTO> other$data = other.getData();
        return !(this$data == null ? other$data != null : !((Object)this$data).equals(other$data));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        long $millisFromStart = this.getMillisFromStart();
        result = result * 59 + (int)($millisFromStart >>> 32 ^ $millisFromStart);
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        List<TrafficCountDTO> $data = this.getData();
        result = result * 59 + ($data == null ? 43 : ((Object)$data).hashCode());
        return result;
    }

    public String toString() {
        return "TrafficDTO(vaId=" + this.getVaId() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", data=" + String.valueOf(this.getData()) + ", millisFromStart=" + this.getMillisFromStart() + ")";
    }

    public TrafficDTO(int vaId, Instant timestamp, List<TrafficCountDTO> data, long millisFromStart) {
        this.vaId = vaId;
        this.timestamp = timestamp;
        this.data = data;
        this.millisFromStart = millisFromStart;
    }
}

