/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserInfo {
    @JsonProperty(value="secretKey")
    private String secretKey;
    @JsonProperty(value="policyName")
    private String policyName;
    @JsonProperty(value="memberOf")
    private List<String> memberOf;
    @JsonProperty(value="status")
    private Status status;

    public UserInfo(@Nullable @JsonProperty(value="status") Status status, @Nullable @JsonProperty(value="secretKey") String secretKey, @Nullable @JsonProperty(value="policyName") String policyName, @Nullable @JsonProperty(value="memberOf") List<String> memberOf) {
        this.status = status;
        this.secretKey = secretKey;
        this.policyName = policyName;
        this.memberOf = memberOf != null ? Collections.unmodifiableList(memberOf) : null;
    }

    public String secretKey() {
        return this.secretKey;
    }

    public String policyName() {
        return this.policyName;
    }

    public List<String> memberOf() {
        return Collections.unmodifiableList(this.memberOf == null ? new LinkedList() : this.memberOf);
    }

    public Status status() {
        return this.status;
    }

    public static enum Status {
        ENABLED("enabled"),
        DISABLED("disabled");

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        @JsonValue
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Status fromString(String statusString) {
            if ("enabled".equals(statusString)) {
                return ENABLED;
            }
            if ("disabled".equals(statusString)) {
                return DISABLED;
            }
            if (statusString.isEmpty()) {
                return null;
            }
            throw new IllegalArgumentException("Unknown status " + statusString);
        }
    }
}

