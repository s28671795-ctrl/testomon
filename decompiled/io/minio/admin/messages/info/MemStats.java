/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MemStats {
    @JsonProperty(value="Alloc")
    private BigDecimal alloc;
    @JsonProperty(value="TotalAlloc")
    private BigDecimal totalAlloc;
    @JsonProperty(value="Mallocs")
    private BigDecimal mallocs;
    @JsonProperty(value="Frees")
    private BigDecimal frees;
    @JsonProperty(value="HeapAlloc")
    private BigDecimal heapAlloc;

    public BigDecimal alloc() {
        return this.alloc;
    }

    public BigDecimal totalAlloc() {
        return this.totalAlloc;
    }

    public BigDecimal mallocs() {
        return this.mallocs;
    }

    public BigDecimal frees() {
        return this.frees;
    }

    public BigDecimal heapAlloc() {
        return this.heapAlloc;
    }
}

