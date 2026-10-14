/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.timezones;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.ZoneOffset;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class TimezoneInfo {
    private final String id;
    private final String name;
    @JSONField(serialize=false)
    private final ZoneOffset zoneOffset;

    public TimezoneInfo(@NotNull String id, @NotNull String city, @NotNull ZoneOffset offset) {
        this.id = Objects.requireNonNull(id);
        this.zoneOffset = Objects.requireNonNull(offset);
        this.name = String.format("(%s) %s", TimezoneInfo.offsetString(offset), city.replace("_", " "));
    }

    private static String offsetString(@NotNull ZoneOffset offset) {
        return offset.getId().replace("Z", "+00:00");
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimezoneInfo)) {
            return false;
        }
        TimezoneInfo other = (TimezoneInfo)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        ZoneOffset this$zoneOffset = this.getZoneOffset();
        ZoneOffset other$zoneOffset = other.getZoneOffset();
        return !(this$zoneOffset == null ? other$zoneOffset != null : !((Object)this$zoneOffset).equals(other$zoneOffset));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        ZoneOffset $zoneOffset = this.getZoneOffset();
        result = result * 59 + ($zoneOffset == null ? 43 : ((Object)$zoneOffset).hashCode());
        return result;
    }

    public String toString() {
        return "TimezoneInfo(id=" + this.getId() + ", name=" + this.getName() + ", zoneOffset=" + String.valueOf(this.getZoneOffset()) + ")";
    }
}

