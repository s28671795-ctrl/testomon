/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.netty.shaded.io.grpc.netty.UnhelpfulSecurityProvider;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;

final class FixedKeyManagerFactory
extends KeyManagerFactory {
    public FixedKeyManagerFactory(List<KeyManager> keyManagers) {
        super(new FixedKeyManagerFactorySpi(keyManagers), new UnhelpfulSecurityProvider(), "FakeAlgorithm");
    }

    private static final class FixedKeyManagerFactorySpi
    extends KeyManagerFactorySpi {
        private final List<KeyManager> keyManagers;

        public FixedKeyManagerFactorySpi(List<KeyManager> keyManagers) {
            this.keyManagers = Collections.unmodifiableList(new ArrayList<KeyManager>(keyManagers));
        }

        @Override
        protected KeyManager[] engineGetKeyManagers() {
            return this.keyManagers.toArray(new KeyManager[0]);
        }

        @Override
        protected void engineInit(KeyStore ks, char[] password) {
        }

        @Override
        protected void engineInit(ManagerFactoryParameters spec) {
        }
    }
}

