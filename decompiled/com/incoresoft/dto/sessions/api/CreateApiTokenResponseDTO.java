/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.sessions.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.sessions.api.ApiTokenDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonDeserialize(builder=CreateApiTokenResponseDTOBuilder.class)
public final class CreateApiTokenResponseDTO {
    @JSONField(name="api_key")
    @JsonProperty(value="api_key")
    @Schema(name="api_key", description="API key")
    private final String apiKey;
    @JSONField(name="token")
    @JsonProperty(value="token")
    @Schema(name="token", description="API token", implementation=ApiTokenDTO.class)
    private final ApiTokenDTO token;

    public static CreateApiTokenResponseDTOBuilder builder() {
        return new CreateApiTokenResponseDTOBuilder();
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public ApiTokenDTO getToken() {
        return this.token;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateApiTokenResponseDTO)) {
            return false;
        }
        CreateApiTokenResponseDTO other = (CreateApiTokenResponseDTO)o;
        String this$apiKey = this.getApiKey();
        String other$apiKey = other.getApiKey();
        if (this$apiKey == null ? other$apiKey != null : !this$apiKey.equals(other$apiKey)) {
            return false;
        }
        ApiTokenDTO this$token = this.getToken();
        ApiTokenDTO other$token = other.getToken();
        return !(this$token == null ? other$token != null : !((Object)this$token).equals(other$token));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $apiKey = this.getApiKey();
        result = result * 59 + ($apiKey == null ? 43 : $apiKey.hashCode());
        ApiTokenDTO $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : ((Object)$token).hashCode());
        return result;
    }

    public String toString() {
        return "CreateApiTokenResponseDTO(apiKey=" + this.getApiKey() + ", token=" + String.valueOf(this.getToken()) + ")";
    }

    public CreateApiTokenResponseDTO(String apiKey, ApiTokenDTO token) {
        this.apiKey = apiKey;
        this.token = token;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class CreateApiTokenResponseDTOBuilder {
        private String apiKey;
        private ApiTokenDTO token;

        CreateApiTokenResponseDTOBuilder() {
        }

        @JsonProperty(value="api_key")
        public CreateApiTokenResponseDTOBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        @JsonProperty(value="token")
        public CreateApiTokenResponseDTOBuilder token(ApiTokenDTO token) {
            this.token = token;
            return this;
        }

        public CreateApiTokenResponseDTO build() {
            return new CreateApiTokenResponseDTO(this.apiKey, this.token);
        }

        public String toString() {
            return "CreateApiTokenResponseDTO.CreateApiTokenResponseDTOBuilder(apiKey=" + this.apiKey + ", token=" + String.valueOf(this.token) + ")";
        }
    }
}

