/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.info.Backend;
import io.minio.admin.messages.info.Buckets;
import io.minio.admin.messages.info.ErasureSetInfo;
import io.minio.admin.messages.info.Objects;
import io.minio.admin.messages.info.ServerProperties;
import io.minio.admin.messages.info.Usage;
import io.minio.admin.messages.info.Versions;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Message {
    @JsonProperty(value="mode")
    private String mode;
    @JsonProperty(value="deploymentID")
    private String deploymentID;
    @JsonProperty(value="buckets")
    private Buckets buckets;
    @JsonProperty(value="objects")
    private Objects objects;
    @JsonProperty(value="versions")
    private Versions versions;
    @JsonProperty(value="usage")
    private Usage usage;
    @JsonProperty(value="backend")
    private Backend backend;
    @JsonProperty(value="servers")
    private List<ServerProperties> servers;
    @JsonProperty(value="pools")
    private Map<Integer, Map<Integer, ErasureSetInfo>> pools;

    public String mode() {
        return this.mode;
    }

    public String deploymentID() {
        return this.deploymentID;
    }

    public Buckets buckets() {
        return this.buckets;
    }

    public Objects objects() {
        return this.objects;
    }

    public Versions versions() {
        return this.versions;
    }

    public Usage usage() {
        return this.usage;
    }

    public Backend backend() {
        return this.backend;
    }

    public List<ServerProperties> servers() {
        return Collections.unmodifiableList(this.servers == null ? new LinkedList() : this.servers);
    }

    public Map<Integer, Map<Integer, ErasureSetInfo>> pools() {
        return this.pools;
    }
}

