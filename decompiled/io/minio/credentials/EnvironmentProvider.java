/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Provider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EnvironmentProvider
implements Provider {
    @Nullable
    protected String getProperty(@Nonnull String name) {
        String value = System.getProperty(name);
        return value != null ? value : System.getenv(name);
    }
}

