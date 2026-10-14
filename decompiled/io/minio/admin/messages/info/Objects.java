/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Objects {
    @JsonProperty(value="count")
    private Integer count;
    @JsonProperty(value="error")
    private String error;

    public Integer count() {
        return this.count;
    }

    public String error() {
        return this.error;
    }
}

