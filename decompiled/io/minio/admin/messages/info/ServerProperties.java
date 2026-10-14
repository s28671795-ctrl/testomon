/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.info.Disk;
import io.minio.admin.messages.info.GCStats;
import io.minio.admin.messages.info.MemStats;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerProperties {
    @JsonProperty(value="state")
    private String state;
    @JsonProperty(value="endpoint")
    private String endpoint;
    @JsonProperty(value="scheme")
    private String scheme;
    @JsonProperty(value="uptime")
    private Integer uptime;
    @JsonProperty(value="version")
    private String version;
    @JsonProperty(value="commitID")
    private String commitID;
    @JsonProperty(value="network")
    private Map<String, String> network;
    @JsonProperty(value="drives")
    private List<Disk> disks;
    @JsonProperty(value="poolNumber")
    private Integer poolNumber;
    @JsonProperty(value="mem_stats")
    private MemStats memStats;
    @JsonProperty(value="go_max_procs")
    private Integer goMaxProcs;
    @JsonProperty(value="num_cpu")
    private Integer numCPU;
    @JsonProperty(value="runtime_version")
    private String runtimeVersion;
    @JsonProperty(value="gc_stats")
    private GCStats gCStats;
    @JsonProperty(value="minio_env_vars")
    private Map<String, String> minioEnvVars;

    public String state() {
        return this.state;
    }

    public String endpoint() {
        return this.endpoint;
    }

    public String scheme() {
        return this.scheme;
    }

    public Integer uptime() {
        return this.uptime;
    }

    public String version() {
        return this.version;
    }

    public String commitID() {
        return this.commitID;
    }

    public Map<String, String> network() {
        return Collections.unmodifiableMap(this.network);
    }

    public List<Disk> disks() {
        return Collections.unmodifiableList(this.disks == null ? new LinkedList() : this.disks);
    }

    public Integer poolNumber() {
        return this.poolNumber;
    }

    public MemStats memStats() {
        return this.memStats;
    }

    public Integer goMaxProcs() {
        return this.goMaxProcs;
    }

    public Integer numCPU() {
        return this.numCPU;
    }

    public String runtimeVersion() {
        return this.runtimeVersion;
    }

    public GCStats gCStats() {
        return this.gCStats;
    }

    public Map<String, String> minioEnvVars() {
        return Collections.unmodifiableMap(this.minioEnvVars);
    }
}

