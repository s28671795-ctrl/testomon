/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListServiceAccountResp {
    @JsonProperty(value="accounts")
    private List<ListServiceAccountInfo> accounts;

    public List<ListServiceAccountInfo> accounts() {
        return this.accounts;
    }

    public static class ListServiceAccountInfo {
        @JsonProperty(value="accessKey")
        private String accessKey;
        @JsonProperty(value="expiration")
        private String expiration;

        public String expiration() {
            return this.expiration;
        }

        public String accessKey() {
            return this.accessKey;
        }
    }
}

