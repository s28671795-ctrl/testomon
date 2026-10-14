/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.genderage.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.genderage.mq.GenderAgeDataDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;
import java.util.List;

@AnalyticsToken(value="detection")
public final class DetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="zone")
    private final Zone zone;
    @JSONField(name="data")
    private final List<GenderAgeDataDTO> data;
    @JSONField(name="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public Zone getZone() {
        return this.zone;
    }

    public List<GenderAgeDataDTO> getData() {
        return this.data;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DetectionDTO)) {
            return false;
        }
        DetectionDTO other = (DetectionDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
            return false;
        }
        List<GenderAgeDataDTO> this$data = this.getData();
        List<GenderAgeDataDTO> other$data = other.getData();
        if (this$data == null ? other$data != null : !((Object)this$data).equals(other$data)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        return !(this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        List<GenderAgeDataDTO> $data = this.getData();
        result = result * 59 + ($data == null ? 43 : ((Object)$data).hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "DetectionDTO(vaId=" + this.getVaId() + ", zone=" + String.valueOf(this.getZone()) + ", data=" + String.valueOf(this.getData()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }

    public DetectionDTO(int vaId, Zone zone, List<GenderAgeDataDTO> data, Instant timestamp) {
        this.vaId = vaId;
        this.zone = zone;
        this.data = data;
        this.timestamp = timestamp;
    }
}

