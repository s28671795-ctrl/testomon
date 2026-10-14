/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.alpr.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.alpr.mq.BoundingBoxDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="track")
public final class PlateTrackDTO
implements AnalyticsMetadata {
    @JSONField(name="ms_type", deserialize=false)
    @JsonProperty(value="ms_type", access=JsonProperty.Access.READ_ONLY)
    private final String msType = "track";
    @JSONField(name="va_id")
    @JsonProperty(value="va_id")
    private final Integer vaId;
    @JSONField(name="timestamp")
    @JsonProperty(value="timestamp")
    private final Instant timestamp;
    @JSONField(name="objects")
    @JsonProperty(value="objects")
    private final BoundingBoxDTO[] objects;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public String getMsType() {
        return this.msType;
    }

    public Integer getVaId() {
        return this.vaId;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public BoundingBoxDTO[] getObjects() {
        return this.objects;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PlateTrackDTO)) {
            return false;
        }
        PlateTrackDTO other = (PlateTrackDTO)o;
        Integer this$vaId = this.getVaId();
        Integer other$vaId = other.getVaId();
        if (this$vaId == null ? other$vaId != null : !((Object)this$vaId).equals(other$vaId)) {
            return false;
        }
        String this$msType = this.getMsType();
        String other$msType = other.getMsType();
        if (this$msType == null ? other$msType != null : !this$msType.equals(other$msType)) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        return Arrays.deepEquals(this.getObjects(), other.getObjects());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $vaId = this.getVaId();
        result = result * 59 + ($vaId == null ? 43 : ((Object)$vaId).hashCode());
        String $msType = this.getMsType();
        result = result * 59 + ($msType == null ? 43 : $msType.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        return result;
    }

    public String toString() {
        return "PlateTrackDTO(msType=" + this.getMsType() + ", vaId=" + this.getVaId() + ", timestamp=" + String.valueOf(this.getTimestamp()) + ", objects=" + Arrays.deepToString(this.getObjects()) + ")";
    }

    public PlateTrackDTO(Integer vaId, Instant timestamp, BoundingBoxDTO[] objects) {
        this.vaId = vaId;
        this.timestamp = timestamp;
        this.objects = objects;
    }
}

