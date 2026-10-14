/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.credentials.Credentials;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AddServiceAccountResp {
    @JsonProperty(value="credentials")
    private Credentials credentials;

    public Credentials credentials() {
        return this.credentials;
    }
}

