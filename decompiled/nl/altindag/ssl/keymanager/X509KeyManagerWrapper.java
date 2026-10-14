/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.security.Principal;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509KeyManager;
import nl.altindag.ssl.keymanager.DelegatingKeyManager;

public final class X509KeyManagerWrapper
extends DelegatingKeyManager<X509KeyManager> {
    public X509KeyManagerWrapper(X509KeyManager keyManager) {
        super(keyManager);
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
        return this.keyManager.chooseClientAlias(keyTypes, issuers, null);
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
        return this.keyManager.chooseServerAlias(keyType, issuers, null);
    }
}

