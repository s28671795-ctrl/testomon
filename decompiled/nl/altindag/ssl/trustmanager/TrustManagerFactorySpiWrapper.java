/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.security.KeyStore;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TrustManagerFactorySpiWrapper
extends TrustManagerFactorySpi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrustManagerFactorySpiWrapper.class);
    private final TrustManager[] trustManagers;

    TrustManagerFactorySpiWrapper(TrustManager trustManager) {
        ValidationUtils.requireNotNull(trustManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("TrustManager"));
        this.trustManagers = new TrustManager[]{trustManager};
    }

    @Override
    protected void engineInit(KeyStore keyStore) {
        LOGGER.info("Ignoring provided KeyStore");
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        LOGGER.info("Ignoring provided ManagerFactoryParameters");
    }

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return this.trustManagers;
    }
}

