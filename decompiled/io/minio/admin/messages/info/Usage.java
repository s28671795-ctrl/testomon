/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Usage {
    @JsonProperty(value="size")
    private Long size;
    @JsonProperty(value="error")
    private String error;

    public Long size() {
        return this.size;
    }

    public String error() {
        return this.error;
    }
}

