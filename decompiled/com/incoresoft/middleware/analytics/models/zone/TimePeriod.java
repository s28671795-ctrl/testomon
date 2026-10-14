/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.zone;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ArraySchema(arraySchema=@Schema(name="selected_days", description="List of days of week during which analytics will be active. The values are numbers from 0 (Monday) to 6 (Sunday)", example="[0, 1, 2, 3, 4, 5, 6]"), schema=@Schema(name="selected_days", implementation=Integer.class, example="1"))
    @NotEmpty
    int[] selectedDays;

    public TimePeriod(String startTime, String endTime, int[] selectedDays) {
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

    public int[] getSelectedDays() {
        return this.selectedDays;
    }
}

