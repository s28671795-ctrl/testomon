/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.smartva.objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.objectinzone.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;

public final class ObjectStatsDTO {
    @JSONField(name="id")
    @Schema(name="id", implementation=String.class, example="1f76dfda-12d1-45ec-ab39-669e018daa2f")
    private final String id;
    @JSONField(name="dwell_time")
    @Schema(name="dwell_time", description="Dwell time", implementation=Integer.class, example="2")
    private final int dwellTime;
    @JSONField(name="type")
    @Schema(name="type", implementation=ObjectType.class, example="0")
    private final ObjectType type;
    @JSONField(name="zone_id")
    @Schema(name="zone_id", description="Zone (rule) ID", implementation=String.class, example="0bef6b9da545")
    private final String zoneId;

    public ObjectStatsDTO(String id, int dwellTime, ObjectType type, String zoneId) {
        this.id = id;
        this.dwellTime = dwellTime;
        this.type = type;
        this.zoneId = zoneId;
    }

    public String getId() {
        return this.id;
    }

    public int getDwellTime() {
        return this.dwellTime;
    }

    public ObjectType getType() {
        return this.type;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ObjectStatsDTO)) {
            return false;
        }
        ObjectStatsDTO other = (ObjectStatsDTO)o;
        if (this.getDwellTime() != other.getDwellTime()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        ObjectType this$type = this.getType();
        ObjectType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        String this$zoneId = this.getZoneId();
        String other$zoneId = other.getZoneId();
        return !(this$zoneId == null ? other$zoneId != null : !this$zoneId.equals(other$zoneId));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getDwellTime();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        ObjectType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        String $zoneId = this.getZoneId();
        result = result * 59 + ($zoneId == null ? 43 : $zoneId.hashCode());
        return result;
    }

    public String toString() {
        return "ObjectStatsDTO(id=" + this.getId() + ", dwellTime=" + this.getDwellTime() + ", type=" + String.valueOf((Object)this.getType()) + ", zoneId=" + this.getZoneId() + ")";
    }
}

