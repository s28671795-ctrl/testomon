/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.ldaptive.ssl.AbstractSSLContextInitializer;
import org.ldaptive.ssl.KeyStoreUtils;

public class X509SSLContextInitializer
extends AbstractSSLContextInitializer {
    private X509Certificate[] trustCerts;
    private X509Certificate authenticationCert;
    private PrivateKey authenticationKey;

    public X509Certificate[] getTrustCertificates() {
        return this.trustCerts;
    }

    public void setTrustCertificates(X509Certificate ... certs) {
        this.trustCerts = certs;
    }

    public X509Certificate getAuthenticationCertificate() {
        return this.authenticationCert;
    }

    public void setAuthenticationCertificate(X509Certificate cert) {
        this.authenticationCert = cert;
    }

    public PrivateKey getAuthenticationKey() {
        return this.authenticationKey;
    }

    public void setAuthenticationKey(PrivateKey key) {
        this.authenticationKey = key;
    }

    @Override
    protected TrustManager[] createTrustManagers() throws GeneralSecurityException {
        TrustManager[] tm = null;
        if (this.trustCerts != null && this.trustCerts.length > 0) {
            TrustManagerFactory tmf = this.getTrustManagerFactory(this.trustCerts);
            tm = tmf.getTrustManagers();
        }
        return tm;
    }

    protected TrustManagerFactory getTrustManagerFactory(X509Certificate[] certs) throws GeneralSecurityException {
        KeyStore ks = KeyStoreUtils.newInstance();
        KeyStoreUtils.setCertificateEntry("ldap_trust_", ks, certs);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        return tmf;
    }

    @Override
    public KeyManager[] getKeyManagers() throws GeneralSecurityException {
        KeyManager[] km4 = null;
        if (this.authenticationCert != null && this.authenticationKey != null) {
            KeyManagerFactory kmf = this.getKeyManagerFactory(this.authenticationCert, this.authenticationKey);
            km4 = kmf.getKeyManagers();
        }
        return km4;
    }

    protected KeyManagerFactory getKeyManagerFactory(X509Certificate cert, PrivateKey key) throws GeneralSecurityException {
        KeyStore ks = KeyStoreUtils.newInstance();
        KeyStoreUtils.setKeyEntry("ldap_client_auth", ks, "changeit".toCharArray(), key, cert);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, "changeit".toCharArray());
        return kmf;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustManagers=" + Arrays.toString(this.trustManagers) + ", " + "trustCerts=" + Arrays.toString(this.trustCerts) + ", " + "authenticationCert=" + this.authenticationCert + "]";
    }
}

