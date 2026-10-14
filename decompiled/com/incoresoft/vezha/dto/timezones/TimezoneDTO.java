/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.timezones;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="TimezoneDTO", description="Timezone class structure")
public final class TimezoneDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Timezone id", example="Europe/Kiev", implementation=String.class)
    private final String id;
    @JSONField(name="name")
    @Schema(name="name", description="Timezone name", example="(+02:00) Kiev", implementation=String.class)
    private final String name;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimezoneDTO)) {
            return false;
        }
        TimezoneDTO other = (TimezoneDTO)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "TimezoneDTO(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public TimezoneDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

