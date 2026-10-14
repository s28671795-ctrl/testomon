/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.ldaptive.ssl.AbstractSSLContextInitializer;

public class DefaultSSLContextInitializer
extends AbstractSSLContextInitializer {
    private KeyManager[] keyManagers;
    private final boolean createDefaultTrustManagers;

    public DefaultSSLContextInitializer() {
        this(true);
    }

    public DefaultSSLContextInitializer(boolean defaultTrustManagers) {
        this.createDefaultTrustManagers = defaultTrustManagers;
    }

    @Override
    protected TrustManager[] createTrustManagers() throws GeneralSecurityException {
        if (this.createDefaultTrustManagers) {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore)null);
            return tmf.getTrustManagers();
        }
        return null;
    }

    @Override
    public KeyManager[] getKeyManagers() throws GeneralSecurityException {
        return this.keyManagers;
    }

    public void setKeyManagers(KeyManager ... managers) {
        this.keyManagers = managers;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustManagers=" + Arrays.toString(this.trustManagers) + ", " + "keyManagers=" + Arrays.toString(this.keyManagers) + ", " + "createDefaultTrustManagers=" + this.createDefaultTrustManagers + "]";
    }
}

