/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.sessions.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(name="CreateApiTokenDTO", description="API token creation data")
public record CreateApiTokenDTO(@JsonProperty(value="name") @JSONField(name="name") @Schema(name="name", description="API key name", example="Test API key", implementation=String.class) @NotBlank String name, @JsonProperty(value="expires_at") @JSONField(name="expires_at") @Schema(name="expires_at", description="Token expiration date(in unix milliseconds)", example="1640995200000", implementation=Long.class) @NotNull Instant expiresAt) {
}

