/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.security.Provider;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import nl.altindag.ssl.keymanager.KeyManagerFactorySpiWrapper;

public final class KeyManagerFactoryWrapper
extends KeyManagerFactory {
    private static final String KEY_MANAGER_FACTORY_ALGORITHM = "no-algorithm";
    private static final Provider PROVIDER = new Provider("", 1.0, ""){};

    public KeyManagerFactoryWrapper(KeyManager keyManager) {
        super(new KeyManagerFactorySpiWrapper(keyManager), PROVIDER, KEY_MANAGER_FACTORY_ALGORITHM);
    }
}

