/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.ldaptive.ssl.AbstractSSLContextInitializer;
import org.ldaptive.ssl.KeyStoreUtils;

public class KeyStoreSSLContextInitializer
extends AbstractSSLContextInitializer {
    private KeyStore trustKeystore;
    private String[] trustAliases;
    private KeyStore authenticationKeystore;
    private String[] authenticationAliases;
    private char[] authenticationPassword;

    public KeyStore getTrustKeystore() {
        return this.trustKeystore;
    }

    public void setTrustKeystore(KeyStore keystore) {
        this.trustKeystore = keystore;
    }

    public String[] getTrustAliases() {
        return this.trustAliases;
    }

    public void setTrustAliases(String ... aliases) {
        this.trustAliases = aliases;
    }

    public KeyStore getAuthenticationKeystore() {
        return this.authenticationKeystore;
    }

    public void setAuthenticationKeystore(KeyStore keystore) {
        this.authenticationKeystore = keystore;
    }

    public String[] getAuthenticationAliases() {
        return this.authenticationAliases;
    }

    public void setAuthenticationAliases(String ... aliases) {
        this.authenticationAliases = aliases;
    }

    public char[] getAuthenticationPassword() {
        return this.authenticationPassword;
    }

    public void setAuthenticationPassword(char[] password) {
        this.authenticationPassword = password;
    }

    @Override
    protected TrustManager[] createTrustManagers() throws GeneralSecurityException {
        TrustManager[] tm = null;
        if (this.trustKeystore != null) {
            TrustManagerFactory tmf = this.getTrustManagerFactory(this.trustKeystore, this.trustAliases);
            tm = tmf.getTrustManagers();
        }
        return tm;
    }

    protected TrustManagerFactory getTrustManagerFactory(KeyStore keystore, String ... aliases) throws GeneralSecurityException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        if (aliases != null && aliases.length > 0) {
            KeyStore ks = KeyStoreUtils.newInstance();
            for (String alias : aliases) {
                KeyStore.Entry entry = KeyStoreUtils.getEntry(alias, keystore, null);
                KeyStoreUtils.setEntry(alias, entry, ks, null);
            }
            tmf.init(ks);
        } else {
            tmf.init(keystore);
        }
        return tmf;
    }

    @Override
    public KeyManager[] getKeyManagers() throws GeneralSecurityException {
        KeyManager[] km4 = null;
        if (this.authenticationKeystore != null && this.authenticationPassword != null) {
            KeyManagerFactory kmf = this.getKeyManagerFactory(this.authenticationKeystore, this.authenticationPassword, this.authenticationAliases);
            km4 = kmf.getKeyManagers();
        }
        return km4;
    }

    protected KeyManagerFactory getKeyManagerFactory(KeyStore keystore, char[] password, String ... aliases) throws GeneralSecurityException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        if (aliases != null && aliases.length > 0) {
            KeyStore ks = KeyStoreUtils.newInstance(password);
            for (String alias : aliases) {
                KeyStore.Entry entry = KeyStoreUtils.getEntry(alias, keystore, password);
                KeyStoreUtils.setEntry(alias, entry, ks, password);
            }
            kmf.init(ks, password);
        } else {
            kmf.init(keystore, password);
        }
        return kmf;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustManagers=" + Arrays.toString(this.trustManagers) + ", " + "trustKeystore=" + this.trustKeystore + ", " + "trustAliases=" + Arrays.toString(this.trustAliases) + ", " + "authenticationKeystore=" + this.authenticationKeystore + ", " + "authenticationAliases=" + Arrays.toString(this.authenticationAliases) + "]";
    }
}

