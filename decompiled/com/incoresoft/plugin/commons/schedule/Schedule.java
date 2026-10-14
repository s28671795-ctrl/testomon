/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.schedule;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.constraints.NotNull;

@Schema(name="Schedule", description="Schedule class structure")
public record Schedule(@ArraySchema(schema=@Schema(name="monday", description="Working hours on Monday", implementation=Integer.class)) @JSONField(name="monday") @JsonProperty(value="monday") @NotNull Set<Integer> monday, @ArraySchema(schema=@Schema(name="tuesday", description="Working hours on Tuesday", implementation=Integer.class)) @JSONField(name="tuesday") @JsonProperty(value="tuesday") @NotNull Set<Integer> tuesday, @ArraySchema(schema=@Schema(name="wednesday", description="Working hours on Wednesday", implementation=Integer.class)) @JSONField(name="wednesday") @JsonProperty(value="wednesday") @NotNull Set<Integer> wednesday, @ArraySchema(schema=@Schema(name="thursday", description="Working hours on Thursday", implementation=Integer.class)) @JSONField(name="thursday") @JsonProperty(value="thursday") @NotNull Set<Integer> thursday, @ArraySchema(schema=@Schema(name="friday", description="Working hours on Friday", implementation=Integer.class)) @JSONField(name="friday") @JsonProperty(value="friday") @NotNull Set<Integer> friday, @ArraySchema(schema=@Schema(name="saturday", description="Working hours on Saturday", implementation=Integer.class)) @JSONField(name="saturday") @JsonProperty(value="saturday") @NotNull Set<Integer> saturday, @ArraySchema(schema=@Schema(name="sunday", description="Working hours on Sunday", implementation=Integer.class)) @JSONField(name="sunday") @JsonProperty(value="sunday") @NotNull Set<Integer> sunday) {
    @JsonIgnore
    @JSONField(serialize=false, deserialize=false)
    public boolean isInSchedule() {
        LocalDateTime now = LocalDateTime.now();
        return this.isInSchedule(DayOfWeek.from(now), now.getHour());
    }

    @JsonIgnore
    @JSONField(serialize=false, deserialize=false)
    public boolean isInSchedule(DayOfWeek dayOfWeek, int hour) {
        Set<Integer> hours;
        switch (dayOfWeek) {
            default: {
                throw new MatchException(null, null);
            }
            case MONDAY: {
                Set<Integer> set = this.monday;
                break;
            }
            case TUESDAY: {
                Set<Integer> set = this.tuesday;
                break;
            }
            case WEDNESDAY: {
                Set<Integer> set = this.wednesday;
                break;
            }
            case THURSDAY: {
                Set<Integer> set = this.thursday;
                break;
            }
            case FRIDAY: {
                Set<Integer> set = this.friday;
                break;
            }
            case SATURDAY: {
                Set<Integer> set = this.saturday;
                break;
            }
            case SUNDAY: {
                Set<Integer> set = hours = this.sunday;
            }
        }
        if (hours == null) {
            return false;
        }
        return hours.contains(hour);
    }

    public static Schedule defaultSchedule() {
        Set<Integer> defaultSet = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toSet());
        return new Schedule(defaultSet, defaultSet, defaultSet, defaultSet, defaultSet, defaultSet, defaultSet);
    }
}

