/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509KeyManager;

public class MultiKeyPasswordKeyManager
implements X509KeyManager {
    private final KeyStore mKeyStore;
    private final String mKeyAlias;
    private final String mKeyPassword;

    public MultiKeyPasswordKeyManager(KeyStore keystore, String keyAlias, String keyPassword) {
        this.mKeyStore = keystore;
        this.mKeyAlias = keyAlias;
        this.mKeyPassword = keyPassword;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return new String[]{this.mKeyAlias};
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return new String[]{this.mKeyAlias};
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        Certificate[] chain = null;
        try {
            chain = this.mKeyStore.getCertificateChain(alias);
        }
        catch (KeyStoreException kse) {
            throw new RuntimeException(kse);
        }
        X509Certificate[] certChain = new X509Certificate[chain.length];
        for (int i = 0; i < chain.length; ++i) {
            certChain[i] = (X509Certificate)chain[i];
        }
        return certChain;
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        try {
            return (PrivateKey)this.mKeyStore.getKey(alias, this.mKeyPassword.toCharArray());
        }
        catch (GeneralSecurityException gse) {
            throw new RuntimeException(gse);
        }
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.mKeyAlias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.mKeyAlias;
    }
}

