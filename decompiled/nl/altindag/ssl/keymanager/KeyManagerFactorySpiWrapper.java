/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class KeyManagerFactorySpiWrapper
extends KeyManagerFactorySpi {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyManagerFactorySpiWrapper.class);
    private static final String NO_KEY_MANAGER_EXCEPTION_MESSAGE = "No valid KeyManager has been provided. KeyManager must be present, but was absent.";
    private final KeyManager[] keyManagers;

    KeyManagerFactorySpiWrapper(KeyManager keyManager) {
        ValidationUtils.requireNotNull(keyManager, NO_KEY_MANAGER_EXCEPTION_MESSAGE);
        this.keyManagers = new KeyManager[]{keyManager};
    }

    @Override
    protected void engineInit(KeyStore keyStore, char[] keyStorePassword) {
        LOGGER.info("Ignoring provided KeyStore");
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        LOGGER.info("Ignoring provided ManagerFactoryParameters");
    }

    @Override
    protected KeyManager[] engineGetKeyManagers() {
        return this.keyManagers;
    }
}

