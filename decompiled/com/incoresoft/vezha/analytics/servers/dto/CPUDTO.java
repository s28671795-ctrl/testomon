/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="CPUDTO", description="CPU information class structure")
public final class CPUDTO {
    @JSONField(name="max")
    @Schema(name="max", description="Maximum CPU usage", implementation=Double.class, example="45.0")
    private final double max;
    @JSONField(name="avg")
    @Schema(name="avg", description="Average CPU usage", implementation=Double.class, example="35.0")
    private final double avg;

    public double getMax() {
        return this.max;
    }

    public double getAvg() {
        return this.avg;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CPUDTO)) {
            return false;
        }
        CPUDTO other = (CPUDTO)o;
        if (Double.compare(this.getMax(), other.getMax()) != 0) {
            return false;
        }
        return Double.compare(this.getAvg(), other.getAvg()) == 0;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $max = Double.doubleToLongBits(this.getMax());
        result = result * 59 + (int)($max >>> 32 ^ $max);
        long $avg = Double.doubleToLongBits(this.getAvg());
        result = result * 59 + (int)($avg >>> 32 ^ $avg);
        return result;
    }

    public String toString() {
        return "CPUDTO(max=" + this.getMax() + ", avg=" + this.getAvg() + ")";
    }

    public CPUDTO(double max, double avg) {
        this.max = max;
        this.avg = avg;
    }
}

