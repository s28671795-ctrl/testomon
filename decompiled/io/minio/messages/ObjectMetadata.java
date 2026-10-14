/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectMetadata {
    @JsonProperty
    private String key;
    @JsonProperty
    private long size;
    @JsonProperty
    private String eTag;
    @JsonProperty
    private String versionId;
    @JsonProperty
    private String sequencer;
    @JsonProperty
    private Map<String, String> userMetadata;

    public String key() {
        return this.key;
    }

    public long size() {
        return this.size;
    }

    public String etag() {
        return this.eTag;
    }

    public String versionId() {
        return this.versionId;
    }

    public String sequencer() {
        return this.sequencer;
    }

    public Map<String, String> userMetadata() {
        return Collections.unmodifiableMap(this.userMetadata == null ? new HashMap() : this.userMetadata);
    }
}

