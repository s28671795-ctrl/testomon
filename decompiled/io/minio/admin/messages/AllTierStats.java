/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.admin.messages.TierStats;
import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AllTierStats {
    @JsonProperty(value="Tiers")
    private Map<String, TierStats> tiers;

    public Map<String, TierStats> tiers() {
        return Collections.unmodifiableMap(this.tiers);
    }
}

