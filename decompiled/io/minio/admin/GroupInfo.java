/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.Status;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GroupInfo {
    @JsonProperty(value="name")
    private String name;
    @JsonProperty(value="status")
    private Status status;
    @JsonProperty(value="members")
    private List<String> members;
    @JsonProperty(value="policy")
    private String policy;

    public String name() {
        return this.name;
    }

    public Status status() {
        return this.status;
    }

    public List<String> members() {
        return Collections.unmodifiableList(this.members == null ? new LinkedList() : this.members);
    }

    public String policy() {
        return this.policy;
    }
}

