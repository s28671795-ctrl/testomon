/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.info.TimedAction;
import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DiskMetrics {
    @JsonProperty(value="lastMinute")
    private Map<String, TimedAction> lastMinute;
    @JsonProperty(value="apiCalls")
    private Map<String, String> apiCalls;
    @JsonProperty(value="totalErrorsAvailability")
    private Integer totalErrorsAvailability;
    @JsonProperty(value="totalErrorsTimeout")
    private Integer totalErrorsTimeout;
    @JsonProperty(value="totalTokens")
    private Long totalTokens;
    @JsonProperty(value="totalWaiting")
    private Long totalWaiting;
    @JsonProperty(value="totalWrites")
    private Long totalWrites;
    @JsonProperty(value="totalDeletes")
    private Long totalDeletes;

    public Integer totalErrorsAvailability() {
        return this.totalErrorsAvailability;
    }

    public Integer totalErrorsTimeout() {
        return this.totalErrorsTimeout;
    }

    public Map<String, TimedAction> lastMinute() {
        return Collections.unmodifiableMap(this.lastMinute);
    }

    public Map<String, String> apiCalls() {
        return Collections.unmodifiableMap(this.apiCalls);
    }

    public Long totalTokens() {
        return this.totalTokens;
    }

    public Long totalWaiting() {
        return this.totalWaiting;
    }

    public Long totalWrites() {
        return this.totalWrites;
    }

    public Long totalDeletes() {
        return this.totalDeletes;
    }
}

