/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class SslContext {
    protected String protocol = "TLS";
    protected String provider = null;
    protected List<KeyManager> keyManagers = new ArrayList<KeyManager>();
    protected List<TrustManager> trustManagers = new ArrayList<TrustManager>();
    protected SecureRandom secureRandom;
    private SSLContext sslContext;
    private static final ThreadLocal<SslContext> current = new ThreadLocal();

    public SslContext() {
    }

    public SslContext(KeyManager[] km4, TrustManager[] tm, SecureRandom random) {
        if (km4 != null) {
            this.setKeyManagers(Arrays.asList(km4));
        }
        if (tm != null) {
            this.setTrustManagers(Arrays.asList(tm));
        }
        this.setSecureRandom(random);
    }

    public static void setCurrentSslContext(SslContext bs) {
        current.set(bs);
    }

    public static SslContext getCurrentSslContext() {
        return current.get();
    }

    public KeyManager[] getKeyManagersAsArray() {
        KeyManager[] rc = new KeyManager[this.keyManagers.size()];
        return this.keyManagers.toArray(rc);
    }

    public TrustManager[] getTrustManagersAsArray() {
        TrustManager[] rc = new TrustManager[this.trustManagers.size()];
        return this.trustManagers.toArray(rc);
    }

    public void addKeyManager(KeyManager km4) {
        this.keyManagers.add(km4);
    }

    public boolean removeKeyManager(KeyManager km4) {
        return this.keyManagers.remove(km4);
    }

    public void addTrustManager(TrustManager tm) {
        this.trustManagers.add(tm);
    }

    public boolean removeTrustManager(TrustManager tm) {
        return this.trustManagers.remove(tm);
    }

    public List<KeyManager> getKeyManagers() {
        return this.keyManagers;
    }

    public void setKeyManagers(List<KeyManager> keyManagers) {
        this.keyManagers = keyManagers;
    }

    public List<TrustManager> getTrustManagers() {
        return this.trustManagers;
    }

    public void setTrustManagers(List<TrustManager> trustManagers) {
        this.trustManagers = trustManagers;
    }

    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public SSLContext getSSLContext() throws NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException {
        if (this.sslContext == null) {
            this.sslContext = this.provider == null ? SSLContext.getInstance(this.protocol) : SSLContext.getInstance(this.protocol, this.provider);
            this.sslContext.init(this.getKeyManagersAsArray(), this.getTrustManagersAsArray(), this.getSecureRandom());
        }
        return this.sslContext;
    }

    public void setSSLContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }
}

