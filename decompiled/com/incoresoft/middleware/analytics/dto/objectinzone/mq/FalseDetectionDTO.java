/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.objectinzone.mq;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsToken;

@AnalyticsToken(value="get_failed_data")
public final class FalseDetectionDTO
implements AnalyticsMetadata {
    @JSONField(name="va_id")
    private final int vaId;

    @Override
    public int getVideoAnalyticsId() {
        return this.vaId;
    }

    public FalseDetectionDTO(int vaId) {
        this.vaId = vaId;
    }

    public int getVaId() {
        return this.vaId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FalseDetectionDTO)) {
            return false;
        }
        FalseDetectionDTO other = (FalseDetectionDTO)o;
        return this.getVaId() == other.getVaId();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getVaId();
        return result;
    }

    public String toString() {
        return "FalseDetectionDTO(vaId=" + this.getVaId() + ")";
    }
}

