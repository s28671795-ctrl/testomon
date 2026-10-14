/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.ext;

import java.lang.reflect.Constructor;
import java.security.KeyStore;
import java.security.cert.CertStore;
import java.util.Properties;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.stax.ext.WSSConfigurationException;
import org.apache.xml.security.stax.config.ConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSSCrypto {
    protected static final transient Logger LOG = LoggerFactory.getLogger(WSSCrypto.class);
    private Class<? extends Merlin> cryptoClass = Merlin.class;
    private Properties cryptoProperties;
    private Crypto cachedCrypto;
    private KeyStore cachedKeyStore;
    private KeyStore keyStore;
    private CertStore crlCertStore;
    private PasswordEncryptor passwordEncryptor;

    WSSCrypto() {
    }

    public Crypto getCrypto() throws WSSConfigurationException {
        if (this.keyStore == this.cachedKeyStore && this.cachedCrypto != null) {
            return this.cachedCrypto;
        }
        Merlin crypto = null;
        if (this.cryptoProperties != null) {
            try {
                Constructor<? extends Merlin> ctor = this.cryptoClass.getConstructor(Properties.class, ClassLoader.class, PasswordEncryptor.class);
                crypto = ctor.newInstance(this.cryptoProperties, Loader.getClassLoader(CryptoFactory.class), this.passwordEncryptor);
                this.keyStore = crypto.getKeyStore();
            }
            catch (Exception e) {
                throw new WSSConfigurationException(WSSecurityException.ErrorCode.FAILURE, e, "signatureCryptoFailure");
            }
        }
        try {
            crypto = this.cryptoClass.newInstance();
            crypto.setDefaultX509Identifier(ConfigurationProperties.getProperty("DefaultX509Alias"));
            crypto.setCryptoProvider(ConfigurationProperties.getProperty("CertProvider"));
            crypto.setKeyStore(this.getKeyStore());
            crypto.setCRLCertStore(this.getCrlCertStore());
            crypto.setPasswordEncryptor(this.passwordEncryptor);
        }
        catch (Exception e) {
            throw new WSSConfigurationException(WSSecurityException.ErrorCode.FAILURE, e, "signatureCryptoFailure");
        }
        this.cachedCrypto = crypto;
        this.cachedKeyStore = crypto.getKeyStore();
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.cachedCrypto = crypto;
        if (crypto instanceof Merlin) {
            this.cachedKeyStore = this.keyStore = ((Merlin)crypto).getKeyStore();
        }
    }

    public Class<? extends Merlin> getCryptoClass() {
        return this.cryptoClass;
    }

    public void setCryptoClass(Class<? extends Merlin> cryptoClass) {
        this.cryptoClass = cryptoClass;
    }

    public Properties getCryptoProperties() {
        return this.cryptoProperties;
    }

    public void setCryptoProperties(Properties cryptoProperties) {
        this.cryptoProperties = cryptoProperties;
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public CertStore getCrlCertStore() {
        return this.crlCertStore;
    }

    public void setCrlCertStore(CertStore crlCertStore) {
        this.crlCertStore = crlCertStore;
    }

    public PasswordEncryptor getPasswordEncryptor() {
        return this.passwordEncryptor;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }
}

