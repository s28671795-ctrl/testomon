/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.AllTierStats;
import io.minio.admin.messages.BucketTargetUsageInfo;
import io.minio.admin.messages.BucketUsageInfo;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataUsageInfo {
    @JsonProperty(value="lastUpdate")
    private ZonedDateTime lastUpdate;
    @JsonProperty(value="objectsCount")
    private long objectsCount;
    @JsonProperty(value="versionsCount")
    private long versionsCount;
    @JsonProperty(value="objectsTotalSize")
    private long objectsTotalSize;
    @JsonProperty(value="objectsReplicationInfo")
    private Map<String, BucketTargetUsageInfo> objectsReplicationInfo;
    @JsonProperty(value="bucketsCount")
    private long bucketsCount;
    @JsonProperty(value="bucketsUsageInfo")
    private Map<String, BucketUsageInfo> bucketsUsageInfo;
    @JsonProperty(value="bucketsSizes")
    private Map<String, Long> bucketsSizes;
    @JsonProperty(value="tierStats")
    private AllTierStats tierStats;

    public ZonedDateTime lastUpdate() {
        return this.lastUpdate;
    }

    public long objectsCount() {
        return this.objectsCount;
    }

    public long versionsCount() {
        return this.versionsCount;
    }

    public long objectsTotalSize() {
        return this.objectsTotalSize;
    }

    public Map<String, BucketTargetUsageInfo> objectsReplicationInfo() {
        return Collections.unmodifiableMap(this.objectsReplicationInfo);
    }

    public long bucketsCount() {
        return this.bucketsCount;
    }

    public Map<String, BucketUsageInfo> bucketsUsageInfo() {
        return Collections.unmodifiableMap(this.bucketsUsageInfo);
    }

    public Map<String, Long> bucketsSizes() {
        return Collections.unmodifiableMap(this.bucketsSizes);
    }

    public AllTierStats tierStats() {
        return this.tierStats;
    }
}

