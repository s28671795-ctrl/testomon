/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimedAction {
    @JsonProperty(value="count")
    private BigDecimal count;
    @JsonProperty(value="acc_time_ns")
    private BigDecimal accTime;
    @JsonProperty(value="bytes")
    private BigDecimal bytes;

    public BigDecimal count() {
        return this.count;
    }

    public BigDecimal accTime() {
        return this.accTime;
    }

    public BigDecimal bytes() {
        return this.bytes;
    }
}

