/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;

public class AliasedX509ExtendedKeyManager
extends X509ExtendedKeyManager {
    private String keyAlias;
    private X509KeyManager keyManager;

    public AliasedX509ExtendedKeyManager(String keyAlias, X509KeyManager keyManager) throws Exception {
        this.keyAlias = keyAlias;
        this.keyManager = keyManager;
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.keyAlias == null ? this.keyManager.chooseClientAlias(keyType, issuers, socket) : this.keyAlias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.keyAlias == null ? this.keyManager.chooseServerAlias(keyType, issuers, socket) : this.keyAlias;
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
    public X509Certificate[] getCertificateChain(String alias) {
        return this.keyManager.getCertificateChain(alias);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return this.keyManager.getPrivateKey(alias);
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        return this.keyAlias == null ? super.chooseEngineServerAlias(keyType, issuers, engine) : this.keyAlias;
    }

    @Override
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        return this.keyAlias == null ? super.chooseEngineClientAlias(keyType, issuers, engine) : this.keyAlias;
    }
}

