/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

public final class KeyStoreUtils {
    private static final String DEFAULT_TYPE = KeyStore.getDefaultType();

    private KeyStoreUtils() {
    }

    public static KeyStore newInstance() throws GeneralSecurityException {
        return KeyStoreUtils.newInstance(DEFAULT_TYPE);
    }

    public static KeyStore newInstance(char[] password) throws GeneralSecurityException {
        return KeyStoreUtils.newInstance(DEFAULT_TYPE, password);
    }

    public static KeyStore newInstance(String type) throws GeneralSecurityException {
        return KeyStoreUtils.newInstance(type, null);
    }

    public static KeyStore newInstance(String type, char[] password) throws GeneralSecurityException {
        KeyStore.Builder builder = KeyStore.Builder.newInstance(type, null, new KeyStore.PasswordProtection(password));
        return builder.getKeyStore();
    }

    public static KeyStore.Entry getEntry(String alias, KeyStore keystore, char[] password) throws GeneralSecurityException {
        if (!keystore.containsAlias(alias)) {
            throw new IllegalArgumentException("KeyStore does not contain alias " + alias);
        }
        return keystore.getEntry(alias, password != null ? new KeyStore.PasswordProtection(password) : null);
    }

    public static void setEntry(String alias, KeyStore.Entry entry, KeyStore keystore, char[] password) throws GeneralSecurityException {
        keystore.setEntry(alias, entry, password != null ? new KeyStore.PasswordProtection(password) : null);
    }

    public static void setKeyEntry(String alias, KeyStore keystore, char[] password, Key key, Certificate ... certs) throws GeneralSecurityException {
        keystore.setKeyEntry(alias, key, password, certs);
    }

    public static void setCertificateEntry(String alias, KeyStore keystore, Certificate ... certs) throws GeneralSecurityException {
        if (certs != null && certs.length > 0) {
            if (certs.length == 1) {
                keystore.setCertificateEntry(alias, certs[0]);
            } else {
                for (int i = 0; i < certs.length; ++i) {
                    keystore.setCertificateEntry(String.format("%s%s", alias, i), certs[i]);
                }
            }
        }
    }
}

