/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.BucketTargetUsageInfo;
import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BucketUsageInfo {
    @JsonProperty(value="size")
    private long size;
    @JsonProperty(value="objectsPendingReplicationTotalSize")
    private long objectsPendingReplicationTotalSize;
    @JsonProperty(value="objectsFailedReplicationTotalSize")
    private long objectsFailedReplicationTotalSize;
    @JsonProperty(value="objectsReplicatedTotalSize")
    private long objectsReplicatedTotalSize;
    @JsonProperty(value="objectsPendingReplicationCount")
    private long objectsPendingReplicationCount;
    @JsonProperty(value="objectsFailedReplicationCount")
    private long objectsFailedReplicationCount;
    @JsonProperty(value="objectsCount")
    private long objectsCount;
    @JsonProperty(value="objectsSizesHistogram")
    private Map<String, Long> objectsSizesHistogram;
    @JsonProperty(value="versionsCount")
    private long versionsCount;
    @JsonProperty(value="objectReplicaTotalSize")
    private long objectReplicaTotalSize;
    @JsonProperty(value="objectsReplicationInfo")
    private Map<String, BucketTargetUsageInfo> objectsReplicationInfo;

    public long size() {
        return this.size;
    }

    public long objectsPendingReplicationTotalSize() {
        return this.objectsPendingReplicationTotalSize;
    }

    public long objectsFailedReplicationTotalSize() {
        return this.objectsFailedReplicationTotalSize;
    }

    public long objectsReplicatedTotalSize() {
        return this.objectsReplicatedTotalSize;
    }

    public long objectsPendingReplicationCount() {
        return this.objectsPendingReplicationCount;
    }

    public long objectsFailedReplicationCount() {
        return this.objectsFailedReplicationCount;
    }

    public long objectsCount() {
        return this.objectsCount;
    }

    public Map<String, Long> objectsSizesHistogram() {
        return Collections.unmodifiableMap(this.objectsSizesHistogram);
    }

    public long versionsCount() {
        return this.versionsCount;
    }

    public long objectReplicaTotalSize() {
        return this.objectReplicaTotalSize;
    }

    public Map<String, BucketTargetUsageInfo> objectsReplicationInfo() {
        return Collections.unmodifiableMap(this.objectsReplicationInfo);
    }
}

