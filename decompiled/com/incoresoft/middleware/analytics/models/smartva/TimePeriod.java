/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Schema(name="TimePeriod", description="TimePeriod base class")
public class TimePeriod {
    @JSONField(name="start_time")
    @Schema(name="start_time", description="Start time", implementation=String.class, example="00:00:00")
    @NotBlank
    private final String startTime;
    @JSONField(name="end_time")
    @Schema(name="end_time", description="End time", implementation=String.class, example="23:59:59")
    @NotBlank
    private final String endTime;
    @JSONField(name="selected_days")
    @ArraySchema(schema=@Schema(name="selected_days"), arraySchema=@Schema(name="selected_days", description="List of days of week during which analytics will be active", example="[\"monday\", \"tuesday\"]"))
    @NotEmpty
    DayOfWeek[] selectedDays;

    public TimePeriod(String startTime, String endTime, DayOfWeek[] selectedDays) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.selectedDays = selectedDays;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public DayOfWeek[] getSelectedDays() {
        return this.selectedDays;
    }

    public static class DayOfWeekSerializer
    implements ObjectSerializer,
    ObjectDeserializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
            serializer.write(((DayOfWeek)object).name().toLowerCase());
        }

        @Override
        public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
            return (T)DayOfWeek.valueOf(parser.parseObject(String.class).toUpperCase());
        }

        @Override
        public int getFastMatchToken() {
            return 4;
        }
    }
}

