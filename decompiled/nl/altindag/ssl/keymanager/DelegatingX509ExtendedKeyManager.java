/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.security.Principal;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.DelegatingKeyManager;

class DelegatingX509ExtendedKeyManager
extends DelegatingKeyManager<X509ExtendedKeyManager> {
    public DelegatingX509ExtendedKeyManager(X509ExtendedKeyManager keyManager) {
        super(keyManager);
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
        return ((X509ExtendedKeyManager)this.keyManager).chooseEngineClientAlias(keyTypes, issuers, sslEngine);
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
        return ((X509ExtendedKeyManager)this.keyManager).chooseEngineServerAlias(keyType, issuers, sslEngine);
    }
}

