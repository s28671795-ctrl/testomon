/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZoneId;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public record TimezoneParams(@NotNull @JsonProperty(value="timezone") @JSONField(name="timezone") ZoneId timezone) {
    public Optional<ZoneId> getTimezone() {
        return Optional.ofNullable(this.timezone);
    }
}

