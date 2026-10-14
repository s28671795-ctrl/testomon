/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Backend {
    @JsonProperty(value="backendType")
    private String backendType;
    @JsonProperty(value="onlineDisks")
    private Integer onlineDisks;
    @JsonProperty(value="offlineDisks")
    private Integer offlineDisks;
    @JsonProperty(value="standardSCParity")
    private Integer standardSCParity;
    @JsonProperty(value="rrSCParity")
    private Integer rrSCParity;
    @JsonProperty(value="totalSets")
    private List<Integer> totalSets;
    @JsonProperty(value="totalDrivesPerSet")
    private List<Integer> totalDrivesPerSet;

    public String backendType() {
        return this.backendType;
    }

    public Integer onlineDisks() {
        return this.onlineDisks;
    }

    public Integer offlineDisks() {
        return this.offlineDisks;
    }

    public Integer standardSCParity() {
        return this.standardSCParity;
    }

    public Integer rrSCParity() {
        return this.rrSCParity;
    }

    public List<Integer> totalSets() {
        return Collections.unmodifiableList(this.totalSets == null ? new LinkedList() : this.totalSets);
    }

    public List<Integer> totalDrivesPerSet() {
        return Collections.unmodifiableList(this.totalDrivesPerSet == null ? new LinkedList() : this.totalDrivesPerSet);
    }
}

