/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.security.Provider;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import nl.altindag.ssl.trustmanager.TrustManagerFactorySpiWrapper;

public final class TrustManagerFactoryWrapper
extends TrustManagerFactory {
    private static final String TRUST_MANAGER_FACTORY_ALGORITHM = "no-algorithm";
    private static final Provider PROVIDER = new Provider("", 1.0, ""){};

    public TrustManagerFactoryWrapper(TrustManager trustManager) {
        super(new TrustManagerFactorySpiWrapper(trustManager), PROVIDER, TRUST_MANAGER_FACTORY_ALGORITHM);
    }
}

