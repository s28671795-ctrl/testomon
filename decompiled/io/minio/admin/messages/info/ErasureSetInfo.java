/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ErasureSetInfo {
    @JsonProperty(value="id")
    private Integer id;
    @JsonProperty(value="rawUsage")
    private BigDecimal rawUsage;
    @JsonProperty(value="rawCapacity")
    private BigDecimal rawCapacity;
    @JsonProperty(value="usage")
    private BigDecimal usage;
    @JsonProperty(value="objectsCount")
    private BigDecimal objectsCount;
    @JsonProperty(value="versionsCount")
    private BigDecimal versionsCount;
    @JsonProperty(value="healDisks")
    private Integer healDisks;

    public Integer id() {
        return this.id;
    }

    public BigDecimal rawUsage() {
        return this.rawUsage;
    }

    public BigDecimal rawCapacity() {
        return this.rawCapacity;
    }

    public BigDecimal usage() {
        return this.usage;
    }

    public BigDecimal objectsCount() {
        return this.objectsCount;
    }

    public BigDecimal versionsCount() {
        return this.versionsCount;
    }

    public Integer healDisks() {
        return this.healDisks;
    }
}

