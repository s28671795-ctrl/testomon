/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BucketTargetUsageInfo {
    @JsonProperty(value="objectsPendingReplicationTotalSize")
    private long objectsPendingReplicationTotalSize;
    @JsonProperty(value="objectsFailedReplicationTotalSize")
    private long objectsFailedReplicationTotalSize;
    @JsonProperty(value="objectsReplicatedTotalSize")
    private long objectsReplicatedTotalSize;
    @JsonProperty(value="objectReplicaTotalSize")
    private long objectReplicaTotalSize;
    @JsonProperty(value="objectsPendingReplicationCount")
    private long objectsPendingReplicationCount;
    @JsonProperty(value="objectsFailedReplicationCount")
    private long objectsFailedReplicationCount;

    public long objectsPendingReplicationTotalSize() {
        return this.objectsPendingReplicationTotalSize;
    }

    public long objectsFailedReplicationTotalSize() {
        return this.objectsFailedReplicationTotalSize;
    }

    public long objectsReplicatedTotalSize() {
        return this.objectsReplicatedTotalSize;
    }

    public long objectReplicaTotalSize() {
        return this.objectReplicaTotalSize;
    }

    public long objectsPendingReplicationCount() {
        return this.objectsPendingReplicationCount;
    }

    public long objectsFailedReplicationCount() {
        return this.objectsFailedReplicationCount;
    }
}

