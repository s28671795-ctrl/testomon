/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import org.conscrypt.Platform;
import org.conscrypt.TrustManagerImpl;

public class TrustManagerFactoryImpl
extends TrustManagerFactorySpi {
    private KeyStore keyStore;

    @Override
    public void engineInit(KeyStore ks) throws KeyStoreException {
        this.keyStore = ks != null ? ks : Platform.getDefaultCertKeyStore();
    }

    @Override
    public void engineInit(ManagerFactoryParameters spec) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }

    @Override
    public TrustManager[] engineGetTrustManagers() {
        if (this.keyStore == null) {
            throw new IllegalStateException("TrustManagerFactory is not initialized");
        }
        return new TrustManager[]{new TrustManagerImpl(this.keyStore)};
    }
}

