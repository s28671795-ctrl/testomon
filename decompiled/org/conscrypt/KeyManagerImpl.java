/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

class KeyManagerImpl
extends X509ExtendedKeyManager {
    private final HashMap<String, KeyStore.PrivateKeyEntry> hash = new HashMap();

    KeyManagerImpl(KeyStore keyStore, char[] pwd) {
        Enumeration<String> aliases;
        try {
            aliases = keyStore.aliases();
        }
        catch (KeyStoreException e) {
            return;
        }
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            try {
                KeyStore.PrivateKeyEntry entry;
                if (!keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) continue;
                try {
                    entry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, new KeyStore.PasswordProtection(pwd));
                }
                catch (UnsupportedOperationException e) {
                    PrivateKey key = (PrivateKey)keyStore.getKey(alias, pwd);
                    Certificate[] certs = keyStore.getCertificateChain(alias);
                    entry = new KeyStore.PrivateKeyEntry(key, certs);
                }
                this.hash.put(alias, entry);
            }
            catch (KeyStoreException keyStoreException) {
            }
            catch (UnrecoverableEntryException unrecoverableEntryException) {
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
        }
    }

    @Override
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers, Socket socket) {
        String[] al = this.chooseAlias(keyTypes, issuers);
        return al == null ? null : al[0];
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        String[] al = this.chooseAlias(new String[]{keyType}, issuers);
        return al == null ? null : al[0];
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        Certificate[] certs;
        if (alias == null) {
            return null;
        }
        if (this.hash.containsKey(alias) && (certs = this.hash.get(alias).getCertificateChain())[0] instanceof X509Certificate) {
            X509Certificate[] xcerts = new X509Certificate[certs.length];
            for (int i = 0; i < certs.length; ++i) {
                xcerts[i] = (X509Certificate)certs[i];
            }
            return xcerts;
        }
        return null;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return this.chooseAlias(new String[]{keyType}, issuers);
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return this.chooseAlias(new String[]{keyType}, issuers);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        if (alias == null) {
            return null;
        }
        if (this.hash.containsKey(alias)) {
            return this.hash.get(alias).getPrivateKey();
        }
        return null;
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine engine) {
        String[] al = this.chooseAlias(keyTypes, issuers);
        return al == null ? null : al[0];
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        String[] al = this.chooseAlias(new String[]{keyType}, issuers);
        return al == null ? null : al[0];
    }

    private String[] chooseAlias(String[] keyTypes, Principal[] issuers) {
        if (keyTypes == null || keyTypes.length == 0) {
            return null;
        }
        List<Principal> issuersList = issuers == null ? null : Arrays.asList(issuers);
        ArrayList<String> found = new ArrayList<String>();
        for (Map.Entry<String, KeyStore.PrivateKeyEntry> entry : this.hash.entrySet()) {
            String alias = entry.getKey();
            Certificate[] chain = entry.getValue().getCertificateChain();
            Certificate cert = chain[0];
            String certKeyAlg = cert.getPublicKey().getAlgorithm();
            String certSigAlg = cert instanceof X509Certificate ? ((X509Certificate)cert).getSigAlgName().toUpperCase(Locale.US) : null;
            for (String keyAlgorithm : keyTypes) {
                String sigAlgorithm;
                if (keyAlgorithm == null) continue;
                int index = keyAlgorithm.indexOf(95);
                if (index == -1) {
                    sigAlgorithm = null;
                } else {
                    sigAlgorithm = keyAlgorithm.substring(index + 1);
                    keyAlgorithm = keyAlgorithm.substring(0, index);
                }
                if (!certKeyAlg.equals(keyAlgorithm) || sigAlgorithm != null && certSigAlg != null && !certSigAlg.contains(sigAlgorithm)) continue;
                if (issuers == null || issuers.length == 0) {
                    found.add(alias);
                    continue;
                }
                for (Certificate certFromChain : chain) {
                    X509Certificate xcertFromChain;
                    X500Principal issuerFromChain;
                    if (!(certFromChain instanceof X509Certificate) || !issuersList.contains(issuerFromChain = (xcertFromChain = (X509Certificate)certFromChain).getIssuerX500Principal())) continue;
                    found.add(alias);
                }
            }
        }
        if (!found.isEmpty()) {
            return found.toArray(new String[found.size()]);
        }
        return null;
    }
}

