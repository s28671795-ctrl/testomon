/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.jsse.MultiKeyPasswordKeyManager;
import org.apache.cxf.configuration.jsse.SSLUtils;
import org.apache.cxf.configuration.security.CertStoreType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.KeyStoreType;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;
import org.apache.cxf.resource.ResourceManager;

public final class TLSParameterJaxBUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(TLSParameterJaxBUtils.class);

    private TLSParameterJaxBUtils() {
    }

    public static SecureRandom getSecureRandom(SecureRandomParameters secureRandomParams) throws GeneralSecurityException {
        SecureRandom secureRandom = null;
        if (secureRandomParams != null) {
            String secureRandomAlg = secureRandomParams.getAlgorithm();
            String randomProvider = secureRandomParams.getProvider();
            secureRandom = randomProvider != null ? (secureRandomAlg != null ? SecureRandom.getInstance(secureRandomAlg, randomProvider) : null) : (secureRandomAlg != null ? SecureRandom.getInstance(secureRandomAlg) : null);
        }
        return secureRandom;
    }

    public static KeyStore getKeyStore(KeyStoreType kst) throws GeneralSecurityException, IOException {
        return TLSParameterJaxBUtils.getKeyStore(kst, false);
    }

    public static KeyStore getKeyStore(KeyStoreType kst, boolean trustStore) throws GeneralSecurityException, IOException {
        KeyStore keyStore;
        char[] password;
        if (kst == null) {
            return null;
        }
        String type = null;
        type = trustStore ? SSLUtils.getTrustStoreType(kst.isSetType() ? kst.getType() : null, LOG, KeyStore.getDefaultType()) : SSLUtils.getKeystoreType(kst.isSetType() ? kst.getType() : null, LOG, KeyStore.getDefaultType());
        char[] cArray = password = kst.isSetPassword() ? TLSParameterJaxBUtils.deobfuscate(kst.getPassword()) : null;
        if (password == null) {
            String tmp = null;
            tmp = trustStore ? SSLUtils.getTruststorePassword(null, LOG) : SSLUtils.getKeystorePassword(null, LOG);
            if (tmp != null) {
                password = tmp.toCharArray();
            }
        }
        String provider = null;
        provider = trustStore ? SSLUtils.getTruststoreProvider(kst.isSetProvider() ? kst.getProvider() : null, LOG) : SSLUtils.getKeystoreProvider(kst.isSetProvider() ? kst.getProvider() : null, LOG);
        KeyStore keyStore2 = keyStore = provider == null ? KeyStore.getInstance(type) : KeyStore.getInstance(type, provider);
        if (kst.isSetFile()) {
            try (InputStream kstInputStream = Files.newInputStream(Paths.get(kst.getFile(), new String[0]), new OpenOption[0]);){
                keyStore.load(kstInputStream, password);
            }
        }
        if (kst.isSetResource()) {
            InputStream is = TLSParameterJaxBUtils.getResourceAsStream(kst.getResource());
            if (is == null) {
                String msg = "Could not load keystore resource " + kst.getResource();
                LOG.severe(msg);
                throw new IOException(msg);
            }
            keyStore.load(is, password);
        } else if (kst.isSetUrl()) {
            keyStore.load(new URL(kst.getUrl()).openStream(), password);
        } else {
            String loc = null;
            loc = trustStore ? SSLUtils.getTruststore(null, LOG) : SSLUtils.getKeystore(null, LOG);
            if (loc != null) {
                try (InputStream ins = Files.newInputStream(Paths.get(loc, new String[0]), new OpenOption[0]);){
                    keyStore.load(ins, password);
                }
                catch (NoSuchFileException ex) {
                    try (InputStream ins2 = TLSParameterJaxBUtils.getResourceAsStream(loc);){
                        keyStore.load(ins2, password);
                    }
                }
            }
        }
        return keyStore;
    }

    public static KeyStore getKeyStore(CertStoreType pst) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        if (pst == null) {
            return null;
        }
        String type = pst.isSetType() ? pst.getType() : KeyStore.getDefaultType();
        if (pst.isSetFile()) {
            InputStream is = Files.newInputStream(Paths.get(pst.getFile(), new String[0]), new OpenOption[0]);
            return TLSParameterJaxBUtils.createTrustStore(is, type);
        }
        if (pst.isSetResource()) {
            InputStream is = TLSParameterJaxBUtils.getResourceAsStream(pst.getResource());
            if (is == null) {
                String msg = "Could not load truststore resource " + pst.getResource();
                LOG.severe(msg);
                throw new IOException(msg);
            }
            return TLSParameterJaxBUtils.createTrustStore(is, type);
        }
        if (pst.isSetUrl()) {
            return TLSParameterJaxBUtils.createTrustStore(new URL(pst.getUrl()).openStream(), type);
        }
        throw new IllegalArgumentException("Could not create KeyStore based on information in CertStoreType");
    }

    private static InputStream getResourceAsStream(String resource) {
        Bus bus;
        ResourceManager rm;
        InputStream is = ClassLoaderUtils.getResourceAsStream(resource, TLSParameterJaxBUtils.class);
        if (is == null && (rm = (bus = BusFactory.getThreadDefaultBus(true)).getExtension(ResourceManager.class)) != null) {
            is = rm.getResourceAsStream(resource);
        }
        return is;
    }

    private static KeyStore createTrustStore(InputStream is, String type) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        Collection<? extends Certificate> certs = TLSParameterJaxBUtils.loadCertificates(is);
        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(null, null);
        for (Certificate certificate : certs) {
            X509Certificate xcert = (X509Certificate)certificate;
            keyStore.setCertificateEntry(xcert.getSubjectX500Principal().getName(), certificate);
        }
        return keyStore;
    }

    private static Collection<? extends Certificate> loadCertificates(InputStream is) throws IOException, CertificateException {
        CertificateFactory factory2 = CertificateFactory.getInstance("X.509");
        return factory2.generateCertificates(is);
    }

    private static char[] deobfuscate(String s2) {
        if (!s2.startsWith("OBF:")) {
            return s2.toCharArray();
        }
        s2 = s2.substring(4);
        char[] b = new char[s2.length() / 2];
        int l = 0;
        for (int i = 0; i < s2.length(); i += 4) {
            String x = s2.substring(i, i + 4);
            int i0 = Integer.parseInt(x, 36);
            int i1 = i0 / 256;
            int i2 = i0 % 256;
            b[l++] = (char)((i1 + i2 - 254) / 2);
        }
        return new String(b, 0, l).toCharArray();
    }

    public static KeyManager[] getKeyManagers(KeyManagersType kmc) throws GeneralSecurityException, IOException {
        KeyStore keyStore = TLSParameterJaxBUtils.getKeyStore(kmc.getKeyStore(), false);
        String alg = kmc.isSetFactoryAlgorithm() ? kmc.getFactoryAlgorithm() : KeyManagerFactory.getDefaultAlgorithm();
        char[] keyPass = TLSParameterJaxBUtils.getKeyPassword(kmc);
        KeyManagerFactory fac = kmc.isSetProvider() ? KeyManagerFactory.getInstance(alg, kmc.getProvider()) : KeyManagerFactory.getInstance(alg);
        fac.init(keyStore, keyPass);
        return fac.getKeyManagers();
    }

    public static KeyManager[] getKeyManagers(KeyManagersType kmc, String alias) throws GeneralSecurityException, IOException {
        KeyStore keyStore = TLSParameterJaxBUtils.getKeyStore(kmc.getKeyStore(), false);
        String alg = kmc.isSetFactoryAlgorithm() ? kmc.getFactoryAlgorithm() : KeyManagerFactory.getDefaultAlgorithm();
        char[] keyPass = TLSParameterJaxBUtils.getKeyPassword(kmc);
        KeyManagerFactory fac = kmc.isSetProvider() ? KeyManagerFactory.getInstance(alg, kmc.getProvider()) : KeyManagerFactory.getInstance(alg);
        try {
            fac.init(keyStore, keyPass);
            return fac.getKeyManagers();
        }
        catch (UnrecoverableKeyException uke) {
            MultiKeyPasswordKeyManager manager = new MultiKeyPasswordKeyManager(keyStore, alias, new String(keyPass));
            return new KeyManager[]{manager};
        }
    }

    private static char[] getKeyPassword(KeyManagersType kmc) {
        char[] keyPass;
        char[] cArray = keyPass = kmc.isSetKeyPassword() ? TLSParameterJaxBUtils.deobfuscate(kmc.getKeyPassword()) : null;
        if (keyPass != null) {
            return keyPass;
        }
        String callbackHandlerClass = kmc.getKeyPasswordCallbackHandler();
        if (callbackHandlerClass == null) {
            return null;
        }
        CallbackHandler ch = null;
        try {
            ch = (CallbackHandler)ClassLoaderUtils.loadClass(callbackHandlerClass, TLSParameterJaxBUtils.class).newInstance();
            String prompt = kmc.getKeyStore().getFile();
            if (prompt == null) {
                prompt = kmc.getKeyStore().getResource();
            }
            PasswordCallback pwCb = new PasswordCallback(prompt, false);
            Callback[] callbacks = new PasswordCallback[]{pwCb};
            ch.handle(callbacks);
            keyPass = ((PasswordCallback)callbacks[0]).getPassword();
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, "Cannot load key password from callback handler: " + e.getMessage(), e);
        }
        return keyPass;
    }

    @Deprecated
    public static TrustManager[] getTrustManagers(TrustManagersType tmc) throws GeneralSecurityException, IOException {
        return TLSParameterJaxBUtils.getTrustManagers(tmc, false);
    }

    public static TrustManager[] getTrustManagers(TrustManagersType tmc, boolean enableRevocation) throws GeneralSecurityException, IOException {
        TrustManagerFactory fac;
        KeyStore keyStore = tmc.isSetKeyStore() ? TLSParameterJaxBUtils.getKeyStore(tmc.getKeyStore(), true) : (tmc.isSetCertStore() ? TLSParameterJaxBUtils.getKeyStore(tmc.getCertStore()) : null);
        String alg = tmc.isSetFactoryAlgorithm() ? tmc.getFactoryAlgorithm() : TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = fac = tmc.isSetProvider() ? TrustManagerFactory.getInstance(alg, tmc.getProvider()) : TrustManagerFactory.getInstance(alg);
        if (enableRevocation) {
            PKIXBuilderParameters param2 = new PKIXBuilderParameters(keyStore, (CertSelector)new X509CertSelector());
            param2.setRevocationEnabled(true);
            fac.init(new CertPathTrustManagerParameters(param2));
        } else {
            fac.init(keyStore);
        }
        return fac.getTrustManagers();
    }
}

