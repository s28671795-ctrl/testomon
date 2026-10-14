/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLContextImpl;

public class DefaultSSLContextImpl
extends OpenSSLContextImpl {
    private static KeyManager[] KEY_MANAGERS;
    private static TrustManager[] TRUST_MANAGERS;

    private DefaultSSLContextImpl(String[] protocols) throws GeneralSecurityException, IOException {
        super(protocols, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    KeyManager[] getKeyManagers() throws GeneralSecurityException, IOException {
        if (KEY_MANAGERS != null) {
            return KEY_MANAGERS;
        }
        String keystore = System.getProperty("javax.net.ssl.keyStore");
        if (keystore == null) {
            return null;
        }
        String keystorepwd = System.getProperty("javax.net.ssl.keyStorePassword");
        char[] pwd = keystorepwd == null ? null : keystorepwd.toCharArray();
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream is = null;){
            is = new BufferedInputStream(new FileInputStream(keystore));
            ks.load(is, pwd);
        }
        String kmfAlg = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlg);
        kmf.init(ks, pwd);
        KEY_MANAGERS = kmf.getKeyManagers();
        return KEY_MANAGERS;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    TrustManager[] getTrustManagers() throws GeneralSecurityException, IOException {
        if (TRUST_MANAGERS != null) {
            return TRUST_MANAGERS;
        }
        String keystore = System.getProperty("javax.net.ssl.trustStore");
        if (keystore == null) {
            return null;
        }
        String keystorepwd = System.getProperty("javax.net.ssl.trustStorePassword");
        char[] pwd = keystorepwd == null ? null : keystorepwd.toCharArray();
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream is = null;){
            is = new BufferedInputStream(new FileInputStream(keystore));
            ks.load(is, pwd);
        }
        String tmfAlg = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlg);
        tmf.init(ks);
        TRUST_MANAGERS = tmf.getTrustManagers();
        return TRUST_MANAGERS;
    }

    @Override
    public void engineInit(KeyManager[] kms, TrustManager[] tms, SecureRandom sr) throws KeyManagementException {
        throw new KeyManagementException("Do not init() the default SSLContext ");
    }

    public static final class TLSv12
    extends DefaultSSLContextImpl {
        public TLSv12() throws GeneralSecurityException, IOException {
            super(NativeCrypto.TLSV12_PROTOCOLS);
        }
    }

    public static final class TLSv13
    extends DefaultSSLContextImpl {
        public TLSv13() throws GeneralSecurityException, IOException {
            super(NativeCrypto.TLSV13_PROTOCOLS);
        }
    }
}

