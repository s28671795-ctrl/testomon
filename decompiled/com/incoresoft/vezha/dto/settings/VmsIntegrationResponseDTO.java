/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.settings;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

public final class VmsIntegrationResponseDTO {
    @JSONField(name="api_key")
    @Schema(name="api_key", description="API access key to be used by VMS for actions that are performed by the system, but require authorization in Vezha")
    private final String apiKey;

    public VmsIntegrationResponseDTO(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VmsIntegrationResponseDTO)) {
            return false;
        }
        VmsIntegrationResponseDTO other = (VmsIntegrationResponseDTO)o;
        String this$apiKey = this.getApiKey();
        String other$apiKey = other.getApiKey();
        return !(this$apiKey == null ? other$apiKey != null : !this$apiKey.equals(other$apiKey));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $apiKey = this.getApiKey();
        result = result * 59 + ($apiKey == null ? 43 : $apiKey.hashCode());
        return result;
    }

    public String toString() {
        return "VmsIntegrationResponseDTO(apiKey=" + this.getApiKey() + ")";
    }
}

