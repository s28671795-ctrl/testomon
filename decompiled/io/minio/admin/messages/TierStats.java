/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TierStats {
    @JsonProperty(value="TotalSize")
    private long totalSize;
    @JsonProperty(value="NumVersions")
    private int numVersions;
    @JsonProperty(value="NumObjects")
    private int numObjects;

    public long totalSize() {
        return this.totalSize;
    }

    public int numVersions() {
        return this.numVersions;
    }

    public int numObjects() {
        return this.numObjects;
    }
}

