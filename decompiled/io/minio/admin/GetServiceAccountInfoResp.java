/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GetServiceAccountInfoResp {
    @JsonProperty(value="parentUser")
    private String parentUser;
    @JsonProperty(value="accountStatus")
    private String accountStatus;
    @JsonProperty(value="impliedPolicy")
    private boolean impliedPolicy;
    @JsonProperty(value="policy")
    private String policy;
    @JsonProperty(value="name")
    private String name;
    @JsonProperty(value="description")
    private String description;
    @JsonProperty(value="expiration")
    private String expiration;

    public String parentUser() {
        return this.parentUser;
    }

    public String accountStatus() {
        return this.accountStatus;
    }

    public boolean impliedPolicy() {
        return this.impliedPolicy;
    }

    public String description() {
        return this.description;
    }

    public String name() {
        return this.name;
    }

    public String expiration() {
        return this.expiration;
    }
}

