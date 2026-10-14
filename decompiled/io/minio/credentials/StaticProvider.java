/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StaticProvider
implements Provider {
    private final Credentials credentials;

    public StaticProvider(@Nonnull String accessKey, @Nonnull String secretKey, @Nullable String sessionToken) {
        this.credentials = new Credentials(accessKey, secretKey, sessionToken, null);
    }

    @Override
    public Credentials fetch() {
        return this.credentials;
    }
}

