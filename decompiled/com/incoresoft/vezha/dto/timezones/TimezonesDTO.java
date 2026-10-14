/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.timezones;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.timezones.TimezoneDTO;
import com.incoresoft.vezha.models.timezones.TimezoneInfo;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;

@Schema(name="TimezonesDTO", description="Timezones class structure")
public final class TimezonesDTO {
    @JSONField(name="region")
    @Schema(name="region", description="Region name", example="Europe", implementation=String.class)
    private final String region;
    @JSONField(name="timezones")
    @ArraySchema(schema=@Schema(implementation=TimezoneDTO.class))
    private final List<TimezoneDTO> timezones;

    public TimezonesDTO(String region, Collection<TimezoneInfo> timezonesInfo) {
        this.region = region;
        this.timezones = timezonesInfo.stream().map(it -> new TimezoneDTO(it.getId(), it.getName())).toList();
    }

    public String getRegion() {
        return this.region;
    }

    public List<TimezoneDTO> getTimezones() {
        return this.timezones;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimezonesDTO)) {
            return false;
        }
        TimezonesDTO other = (TimezonesDTO)o;
        String this$region = this.getRegion();
        String other$region = other.getRegion();
        if (this$region == null ? other$region != null : !this$region.equals(other$region)) {
            return false;
        }
        List<TimezoneDTO> this$timezones = this.getTimezones();
        List<TimezoneDTO> other$timezones = other.getTimezones();
        return !(this$timezones == null ? other$timezones != null : !((Object)this$timezones).equals(other$timezones));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $region = this.getRegion();
        result = result * 59 + ($region == null ? 43 : $region.hashCode());
        List<TimezoneDTO> $timezones = this.getTimezones();
        result = result * 59 + ($timezones == null ? 43 : ((Object)$timezones).hashCode());
        return result;
    }

    public String toString() {
        return "TimezonesDTO(region=" + this.getRegion() + ", timezones=" + String.valueOf(this.getTimezones()) + ")";
    }

    public TimezonesDTO(String region, List<TimezoneDTO> timezones) {
        this.region = region;
        this.timezones = timezones;
    }
}

