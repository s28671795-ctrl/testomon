/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.smartva.objects.AlertDetectedObjectEncodingDTO;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;
import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import com.incoresoft.middleware.analytics.models.zone.Zone;
import java.time.Instant;
import java.util.Arrays;

@AnalyticsToken(value="stats")
public final class StatsDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;
    @JSONField(name="trigger")
    private final TriggerType trigger;
    @JSONField(name="dwell_time")
    private final int dwellTime;
    @JSONField(name="objects")
    private final AlertDetectedObjectEncodingDTO[] objects;
    @JSONField(name="zone")
    private final Zone zone;
    @JSONField(name="timestamp")
    private final Instant timestamp;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public StatsDTO(int vaId, TriggerType trigger, int dwellTime, AlertDetectedObjectEncodingDTO[] objects, Zone zone, Instant timestamp) {
        this.vaId = vaId;
        this.trigger = trigger;
        this.dwellTime = dwellTime;
        this.objects = objects;
        this.zone = zone;
        this.timestamp = timestamp;
    }

    public int getVaId() {
        return this.vaId;
    }

    public TriggerType getTrigger() {
        return this.trigger;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public AlertDetectedObjectEncodingDTO[] getObjects() {
        return this.objects;
    }

    public Zone getZone() {
        return this.zone;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StatsDTO)) {
            return false;
        }
        StatsDTO other = (StatsDTO)o;
        if (this.getVaId() != other.getVaId()) {
            return false;
        }
        if (this.getDwellTime() != other.getDwellTime()) {
            return false;
        }
        TriggerType this$trigger = this.getTrigger();
        TriggerType other$trigger = other.getTrigger();
        if (this$trigger == null ? other$trigger != null : !((Object)((Object)this$trigger)).equals((Object)other$trigger)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getObjects(), other.getObjects())) {
            return false;
        }
        Zone this$zone = this.getZone();
        Zone other$zone = other.getZone();
        if (this$zone == null ? other$zone != null : !this$zone.equals(other$zone)) {
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
        result = result * 59 + this.getDwellTime();
        TriggerType $trigger = this.getTrigger();
        result = result * 59 + ($trigger == null ? 43 : ((Object)((Object)$trigger)).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getObjects());
        Zone $zone = this.getZone();
        result = result * 59 + ($zone == null ? 43 : $zone.hashCode());
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        return result;
    }

    public String toString() {
        return "StatsDTO(vaId=" + this.getVaId() + ", trigger=" + String.valueOf((Object)this.getTrigger()) + ", dwellTime=" + this.getDwellTime() + ", objects=" + Arrays.deepToString(this.getObjects()) + ", zone=" + String.valueOf(this.getZone()) + ", timestamp=" + String.valueOf(this.getTimestamp()) + ")";
    }
}

