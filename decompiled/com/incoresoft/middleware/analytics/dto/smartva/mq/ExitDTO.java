/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.objectinzone.objects.ObjectStatsDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="exit")
public final class ExitDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="objects")
    private final ObjectStatsDTO[] objects;
    @JSONField(name="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public ExitDTO(int vaId, ObjectStatsDTO[] objects, Instant timestamp) {
        this.vaId = vaId;
        this.objects = objects;
        this.timestamp = timestamp;
    }

    public int getVaId() {
        return this.vaId;
    }

    public ObjectStatsDTO[] getObjects() {
        return this.objects;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExitDTO)) {
            return false;
        }
        ExitDTO other = (ExitDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (!Arrays.deepEquals(this.getObjects(), other.getObjects())) {
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
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "ExitDTO(vaId=" + this.getVaId() + ", objects=" + Arrays.deepToString(this.getObjects()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

