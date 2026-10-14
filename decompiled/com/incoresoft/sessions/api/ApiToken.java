/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions.api;

import java.time.Duration;
import java.time.Instant;

public record ApiToken(int id, String name, String apiKey, String apiKeyHash, int userId, Instant issuedAt, Instant expiresAt) {
    public ApiToken(int userId, String name, String apiKey, String apiKeyHash, Duration validFor) {
        this(0, name, apiKey, apiKeyHash, userId, Instant.now(), Instant.now().plus(validFor));
    }

    public boolean valid() {
        return Instant.now().isBefore(this.expiresAt);
    }
}

