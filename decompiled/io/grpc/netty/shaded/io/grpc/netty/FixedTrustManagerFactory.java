/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.netty.shaded.io.grpc.netty.UnhelpfulSecurityProvider;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;

final class FixedTrustManagerFactory
extends TrustManagerFactory {
    public FixedTrustManagerFactory(List<TrustManager> trustManagers) {
        super(new FixedTrustManagerFactorySpi(trustManagers), new UnhelpfulSecurityProvider(), "FakeAlgorithm");
    }

    private static final class FixedTrustManagerFactorySpi
    extends TrustManagerFactorySpi {
        private final List<TrustManager> trustManagers;

        public FixedTrustManagerFactorySpi(List<TrustManager> trustManagers) {
            this.trustManagers = Collections.unmodifiableList(new ArrayList<TrustManager>(trustManagers));
        }

        @Override
        protected TrustManager[] engineGetTrustManagers() {
            return this.trustManagers.toArray(new TrustManager[0]);
        }

        @Override
        protected void engineInit(KeyStore ks) {
        }

        @Override
        protected void engineInit(ManagerFactoryParameters spec) {
        }
    }
}

