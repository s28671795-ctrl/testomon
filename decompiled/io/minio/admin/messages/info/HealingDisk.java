/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HealingDisk {
    @JsonProperty(value="id")
    private String id;
    @JsonProperty(value="heal_id")
    private String healID;
    @JsonProperty(value="pool_index")
    private Integer poolIndex;
    @JsonProperty(value="set_index")
    private Integer setIndex;
    @JsonProperty(value="disk_index")
    private Integer diskIndex;
    @JsonProperty(value="endpoint")
    private String endpoint;
    @JsonProperty(value="path")
    private String path;
    @JsonProperty(value="started")
    private String started;
    @JsonProperty(value="last_update")
    private String lastUpdate;
    @JsonProperty(value="objects_total_count")
    private BigDecimal objectsTotalCount;
    @JsonProperty(value="objects_total_size")
    private BigDecimal objectsTotalSize;
    @JsonProperty(value="items_healed")
    private BigDecimal itemsHealed;
    @JsonProperty(value="items_failed")
    private BigDecimal itemsFailed;
    @JsonProperty(value="bytes_done")
    private BigDecimal bytesDone;
    @JsonProperty(value="bytes_failed")
    private BigDecimal bytesFailed;
    @JsonProperty(value="objects_healed")
    private BigDecimal objectsHealed;
    @JsonProperty(value="objects_failed")
    private BigDecimal objectsFailed;
    @JsonProperty(value="current_bucket")
    private String bucket;
    @JsonProperty(value="current_object")
    private String object;
    @JsonProperty(value="queued_buckets")
    private List<String> queuedBuckets;
    @JsonProperty(value="healed_buckets")
    private List<String> healedBuckets;

    public String id() {
        return this.id;
    }

    public String healID() {
        return this.healID;
    }

    public Integer poolIndex() {
        return this.poolIndex;
    }

    public Integer setIndex() {
        return this.setIndex;
    }

    public Integer diskIndex() {
        return this.diskIndex;
    }

    public String endpoint() {
        return this.endpoint;
    }

    public String path() {
        return this.path;
    }

    public String started() {
        return this.started;
    }

    public String lastUpdate() {
        return this.lastUpdate;
    }

    public BigDecimal objectsTotalCount() {
        return this.objectsTotalCount;
    }

    public BigDecimal objectsTotalSize() {
        return this.objectsTotalSize;
    }

    public BigDecimal itemsHealed() {
        return this.itemsHealed;
    }

    public BigDecimal itemsFailed() {
        return this.itemsFailed;
    }

    public BigDecimal bytesDone() {
        return this.bytesDone;
    }

    public BigDecimal bytesFailed() {
        return this.bytesFailed;
    }

    public BigDecimal objectsHealed() {
        return this.objectsHealed;
    }

    public BigDecimal objectsFailed() {
        return this.objectsFailed;
    }

    public String bucket() {
        return this.bucket;
    }

    public String object() {
        return this.object;
    }

    public List<String> queuedBuckets() {
        return Collections.unmodifiableList(this.queuedBuckets == null ? new LinkedList() : this.queuedBuckets);
    }

    public List<String> healedBuckets() {
        return Collections.unmodifiableList(this.healedBuckets == null ? new LinkedList() : this.healedBuckets);
    }
}

