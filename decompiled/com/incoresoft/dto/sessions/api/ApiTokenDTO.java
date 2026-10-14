/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.sessions.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.sessions.api.ApiToken;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name="ApiTokenDTO", description="API token data")
public record ApiTokenDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Token ID", example="1") int id, @JsonProperty(value="name") @JSONField(name="name") @Schema(name="name", description="Token name", example="Test token", implementation=String.class) String name, @JsonProperty(value="created_at") @JSONField(name="created_at") @Schema(name="created_at", description="Token creation date(in unix milliseconds)", example="1640995200000", implementation=Long.class) Instant createdAt, @JsonProperty(value="expires_at") @JSONField(name="expires_at") @Schema(name="expires_at", description="Token expiration date(in unix milliseconds)", example="1640995200000", implementation=Long.class) Instant expiresAt) {
    public ApiTokenDTO(ApiToken session) {
        this(session.id(), session.name(), session.issuedAt(), session.expiresAt());
    }
}

