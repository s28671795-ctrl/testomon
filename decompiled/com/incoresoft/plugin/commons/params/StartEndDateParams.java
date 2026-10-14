/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public final class StartEndDateParams {
    @JSONField(name="start_date")
    @JsonProperty(value="start_date")
    private final Instant startDate;
    @JSONField(name="end_date")
    @JsonProperty(value="end_date")
    private final Instant endDate;

    @JSONCreator
    @JsonCreator(mode=JsonCreator.Mode.PROPERTIES)
    public StartEndDateParams(@JSONField(name="start_date", jsonDirect=true) @JsonProperty(value="start_date") Instant startDate, @JSONField(name="end_date") @JsonProperty(value="end_date") Instant endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.check(this.getStartDate(), this.getEndDate());
    }

    private void check(Instant start2, Instant end) {
        if (start2 != null && end != null && end.isBefore(start2)) {
            throw new IllegalArgumentException("end date is before start date");
        }
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StartEndDateParams)) {
            return false;
        }
        StartEndDateParams other = (StartEndDateParams)o;
        Instant this$startDate = this.getStartDate();
        Instant other$startDate = other.getStartDate();
        if (this$startDate == null ? other$startDate != null : !((Object)this$startDate).equals(other$startDate)) {
            return false;
        }
        Instant this$endDate = this.getEndDate();
        Instant other$endDate = other.getEndDate();
        return !(this$endDate == null ? other$endDate != null : !((Object)this$endDate).equals(other$endDate));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Instant $startDate = this.getStartDate();
        result = result * 59 + ($startDate == null ? 43 : ((Object)$startDate).hashCode());
        Instant $endDate = this.getEndDate();
        result = result * 59 + ($endDate == null ? 43 : ((Object)$endDate).hashCode());
        return result;
    }

    public String toString() {
        return "StartEndDateParams(startDate=" + String.valueOf(this.getStartDate()) + ", endDate=" + String.valueOf(this.getEndDate()) + ")";
    }
}

