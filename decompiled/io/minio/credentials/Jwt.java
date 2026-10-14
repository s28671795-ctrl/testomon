/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.beans.ConstructorProperties;
import java.util.Objects;
import javax.annotation.Nonnull;

public class Jwt {
    @JsonProperty(value="access_token")
    private final String token;
    @JsonProperty(value="expires_in")
    private final int expiry;

    @ConstructorProperties(value={"access_token", "expires_in"})
    public Jwt(@Nonnull String token, int expiry) {
        this.token = Objects.requireNonNull(token);
        this.expiry = expiry;
    }

    public String token() {
        return this.token;
    }

    public int expiry() {
        return this.expiry;
    }
}

