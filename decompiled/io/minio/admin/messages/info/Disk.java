/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.info.DiskMetrics;
import io.minio.admin.messages.info.HealingDisk;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Disk {
    @JsonProperty(value="endpoint")
    private String endpoint;
    @JsonProperty(value="rootDisk")
    private boolean rootDisk;
    @JsonProperty(value="path")
    private String path;
    @JsonProperty(value="healing")
    private boolean healing;
    @JsonProperty(value="scanning")
    private boolean scanning;
    @JsonProperty(value="state")
    private String state;
    @JsonProperty(value="uuid")
    private String uuid;
    @JsonProperty(value="major")
    private BigDecimal major;
    @JsonProperty(value="minor")
    private BigDecimal minor;
    @JsonProperty(value="model")
    private String model;
    @JsonProperty(value="totalspace")
    private BigDecimal totalspace;
    @JsonProperty(value="usedspace")
    private BigDecimal usedspace;
    @JsonProperty(value="availspace")
    private BigDecimal availspace;
    @JsonProperty(value="readthroughput")
    private BigDecimal readthroughput;
    @JsonProperty(value="writethroughput")
    private BigDecimal writethroughput;
    @JsonProperty(value="readlatency")
    private BigDecimal readlatency;
    @JsonProperty(value="writelatency")
    private BigDecimal writelatency;
    @JsonProperty(value="utilization")
    private BigDecimal utilization;
    @JsonProperty(value="metrics")
    private DiskMetrics metrics;
    @JsonProperty(value="heal_info")
    private HealingDisk healInfo;
    @JsonProperty(value="used_inodes")
    private BigDecimal usedInodes;
    @JsonProperty(value="free_inodes")
    private BigDecimal freeInodes;
    @JsonProperty(value="pool_index")
    private Integer poolIndex;
    @JsonProperty(value="set_index")
    private Integer setIndex;
    @JsonProperty(value="disk_index")
    private Integer diskIndex;

    public String endpoint() {
        return this.endpoint;
    }

    public boolean isRootDisk() {
        return this.rootDisk;
    }

    public String path() {
        return this.path;
    }

    public boolean isHealing() {
        return this.healing;
    }

    public boolean isScanning() {
        return this.scanning;
    }

    public String state() {
        return this.state;
    }

    public String uuid() {
        return this.uuid;
    }

    public BigDecimal major() {
        return this.major;
    }

    public BigDecimal minor() {
        return this.minor;
    }

    public String model() {
        return this.model;
    }

    public BigDecimal totalspace() {
        return this.totalspace;
    }

    public BigDecimal usedspace() {
        return this.usedspace;
    }

    public BigDecimal availspace() {
        return this.availspace;
    }

    public BigDecimal readthroughput() {
        return this.readthroughput;
    }

    public BigDecimal writethroughput() {
        return this.writethroughput;
    }

    public BigDecimal readlatency() {
        return this.readlatency;
    }

    public BigDecimal writelatency() {
        return this.writelatency;
    }

    public BigDecimal utilization() {
        return this.utilization;
    }

    public DiskMetrics metrics() {
        return this.metrics;
    }

    public HealingDisk healInfo() {
        return this.healInfo;
    }

    public BigDecimal usedInodes() {
        return this.usedInodes;
    }

    public BigDecimal freeInodes() {
        return this.freeInodes;
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
}

