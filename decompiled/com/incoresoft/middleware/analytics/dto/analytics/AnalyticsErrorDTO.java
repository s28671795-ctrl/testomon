/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.AnalyticsError;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="AnalyticsErrorDTO", description="Analytics error class structure")
public final class AnalyticsErrorDTO {
    @JSONField(name="locale_key")
    @JsonProperty(value="locale_key")
    @Schema(name="locale_key", description="Analytics error locale key", example="LICENCE_ERROR")
    private final String localeKey;

    @JsonCreator
    public AnalyticsErrorDTO(@JsonProperty(value="locale_key") String localeKey) {
        this.localeKey = localeKey;
    }

    public AnalyticsErrorDTO(AnalyticsError analyticsError) {
        this.localeKey = analyticsError.getLocaleKey();
    }

    public String getLocaleKey() {
        return this.localeKey;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsErrorDTO)) {
            return false;
        }
        AnalyticsErrorDTO other = (AnalyticsErrorDTO)o;
        String this$localeKey = this.getLocaleKey();
        String other$localeKey = other.getLocaleKey();
        return !(this$localeKey == null ? other$localeKey != null : !this$localeKey.equals(other$localeKey));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $localeKey = this.getLocaleKey();
        result = result * 59 + ($localeKey == null ? 43 : $localeKey.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsErrorDTO(localeKey=" + this.getLocaleKey() + ")";
    }
}

