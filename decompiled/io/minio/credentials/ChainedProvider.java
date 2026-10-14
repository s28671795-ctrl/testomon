/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import java.security.ProviderException;
import java.util.Arrays;
import java.util.List;

public class ChainedProvider
implements Provider {
    private final List<Provider> providers;
    private Provider currentProvider;
    private Credentials credentials;

    public ChainedProvider(Provider ... providers) {
        this.providers = Arrays.asList(providers);
    }

    @Override
    public synchronized Credentials fetch() {
        if (this.credentials != null && !this.credentials.isExpired()) {
            return this.credentials;
        }
        if (this.currentProvider != null) {
            try {
                this.credentials = this.currentProvider.fetch();
                return this.credentials;
            }
            catch (ProviderException providerException) {
                // empty catch block
            }
        }
        for (Provider provider : this.providers) {
            try {
                this.credentials = provider.fetch();
                this.currentProvider = provider;
                return this.credentials;
            }
            catch (ProviderException providerException) {
            }
        }
        throw new ProviderException("All providers fail to fetch credentials");
    }
}

