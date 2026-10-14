/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.util.Collections;
import java.util.Properties;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerlinDevice
extends Merlin {
    private static final Logger LOG = LoggerFactory.getLogger(MerlinDevice.class);

    public MerlinDevice() {
    }

    public MerlinDevice(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor) throws WSSecurityException, IOException {
        super(properties, loader, passwordEncryptor);
    }

    @Override
    public void loadProperties(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor) throws WSSecurityException, IOException {
        String crlLocation;
        Throwable throwable;
        InputStream is;
        String trustStoreLocation;
        String loadCacerts;
        String trustStoreType;
        String trustStorePassword;
        String keyStoreType;
        String keyStorePassword;
        String keyStoreLocation;
        String alias;
        String certProvider;
        String provider;
        if (properties == null) {
            return;
        }
        this.properties = properties;
        this.passwordEncryptor = passwordEncryptor;
        String prefix = "org.apache.wss4j.crypto.merlin.";
        for (Object key : properties.keySet()) {
            if (!(key instanceof String)) continue;
            String propKey = (String)key;
            if (propKey.startsWith("org.apache.wss4j.crypto.merlin.")) break;
            if (!propKey.startsWith("org.apache.ws.security.crypto.merlin.")) continue;
            prefix = "org.apache.ws.security.crypto.merlin.";
            break;
        }
        if ((provider = properties.getProperty(prefix + "keystore.provider")) != null) {
            provider = provider.trim();
        }
        if ((certProvider = properties.getProperty(prefix + "cert.provider")) != null) {
            this.setCryptoProvider(certProvider);
        }
        if ((alias = properties.getProperty(prefix + "keystore.alias")) != null) {
            alias = alias.trim();
            this.setDefaultX509Identifier(alias);
        }
        if ((keyStoreLocation = properties.getProperty(prefix + "keystore.file")) == null) {
            keyStoreLocation = properties.getProperty(prefix + "file");
        }
        if ((keyStorePassword = properties.getProperty(prefix + "keystore.password", "security")) != null) {
            keyStorePassword = keyStorePassword.trim();
            keyStorePassword = this.decryptPassword(keyStorePassword, passwordEncryptor);
        }
        if ((keyStoreType = properties.getProperty(prefix + "keystore.type", KeyStore.getDefaultType())) != null) {
            keyStoreType = keyStoreType.trim();
        }
        if (keyStoreLocation != null) {
            keyStoreLocation = keyStoreLocation.trim();
            try (InputStream is2 = MerlinDevice.loadInputStream(loader, keyStoreLocation);){
                this.keystore = this.load(is2, keyStorePassword, provider, keyStoreType);
                LOG.debug("The KeyStore {} of type {} has been loaded", (Object)keyStoreLocation, (Object)keyStoreType);
            }
        } else {
            this.keystore = this.load(null, keyStorePassword, provider, keyStoreType);
        }
        if ((trustStorePassword = properties.getProperty(prefix + "truststore.password", "changeit")) != null) {
            trustStorePassword = trustStorePassword.trim();
            trustStorePassword = this.decryptPassword(trustStorePassword, passwordEncryptor);
        }
        if ((trustStoreType = properties.getProperty(prefix + "truststore.type", KeyStore.getDefaultType())) != null) {
            trustStoreType = trustStoreType.trim();
        }
        if ((loadCacerts = properties.getProperty(prefix + "load.cacerts", "false")) != null) {
            loadCacerts = loadCacerts.trim();
        }
        if ((trustStoreLocation = properties.getProperty(prefix + "truststore.file")) != null) {
            trustStoreLocation = trustStoreLocation.trim();
            try (InputStream is3 = MerlinDevice.loadInputStream(loader, trustStoreLocation);){
                this.truststore = this.load(is3, trustStorePassword, provider, trustStoreType);
                LOG.debug("The TrustStore {} of type {} has been loaded", (Object)trustStoreLocation, (Object)trustStoreType);
                this.loadCACerts = false;
            }
        } else if (Boolean.valueOf(loadCacerts).booleanValue()) {
            String cacertsPath = (System.getProperty("java.home") + "/lib/security/cacerts").trim();
            is = Files.newInputStream(Paths.get(cacertsPath, new String[0]), new OpenOption[0]);
            throwable = null;
            try {
                String cacertsPasswd = properties.getProperty(prefix + "truststore.password", "changeit");
                if (cacertsPasswd != null) {
                    cacertsPasswd = cacertsPasswd.trim();
                    cacertsPasswd = this.decryptPassword(cacertsPasswd, passwordEncryptor);
                }
                this.truststore = this.load(is, cacertsPasswd, null, KeyStore.getDefaultType());
                LOG.debug("CA certs have been loaded");
                this.loadCACerts = true;
            }
            catch (Throwable cacertsPasswd) {
                throwable = cacertsPasswd;
                throw cacertsPasswd;
            }
            finally {
                if (is != null) {
                    MerlinDevice.$closeResource(throwable, is);
                }
            }
        } else {
            this.truststore = this.load(null, trustStorePassword, provider, trustStoreType);
        }
        if ((crlLocation = properties.getProperty(prefix + "x509crl.file")) != null) {
            crlLocation = crlLocation.trim();
            try {
                is = MerlinDevice.loadInputStream(loader, crlLocation);
                throwable = null;
                try {
                    CertificateFactory cf = this.getCertificateFactory();
                    X509CRL crl = (X509CRL)cf.generateCRL(is);
                    this.crlCertStore = provider == null || provider.length() == 0 ? CertStore.getInstance("Collection", new CollectionCertStoreParameters(Collections.singletonList(crl))) : CertStore.getInstance("Collection", (CertStoreParameters)new CollectionCertStoreParameters(Collections.singletonList(crl)), provider);
                    LOG.debug("The CRL {} has been loaded", (Object)crlLocation);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is != null) {
                        MerlinDevice.$closeResource(throwable, is);
                    }
                }
            }
            catch (Exception e) {
                LOG.debug(e.getMessage(), e);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "failedCredentialLoad");
            }
        }
    }
}

