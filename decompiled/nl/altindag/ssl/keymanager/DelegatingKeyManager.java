/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import nl.altindag.ssl.util.internal.ValidationUtils;

abstract class DelegatingKeyManager<T extends X509KeyManager>
extends X509ExtendedKeyManager {
    private static final String NO_KEY_MANAGER_EXCEPTION_MESSAGE = "No valid KeyManager has been provided. KeyManager must be present, but was absent.";
    T keyManager;

    DelegatingKeyManager(T keyManager) {
        this.keyManager = (X509KeyManager)ValidationUtils.requireNotNull(keyManager, NO_KEY_MANAGER_EXCEPTION_MESSAGE);
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.keyManager.chooseClientAlias(keyType, issuers, socket);
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.keyManager.chooseServerAlias(keyType, issuers, socket);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return this.keyManager.getPrivateKey(alias);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return this.keyManager.getCertificateChain(alias);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return this.keyManager.getClientAliases(keyType, issuers);
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return this.keyManager.getServerAliases(keyType, issuers);
    }

    @Override
    public abstract String chooseEngineClientAlias(String[] var1, Principal[] var2, SSLEngine var3);

    @Override
    public abstract String chooseEngineServerAlias(String var1, Principal[] var2, SSLEngine var3);

    public T getInnerKeyManager() {
        return this.keyManager;
    }
}

