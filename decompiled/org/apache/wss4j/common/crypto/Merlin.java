/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.x500.X500Principal;
import org.apache.wss4j.common.crypto.CryptoBase;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Merlin
extends CryptoBase {
    public static final String ENCRYPTED_PASSWORD_PREFIX = "ENC(";
    public static final String ENCRYPTED_PASSWORD_SUFFIX = ")";
    public static final String PREFIX = "org.apache.wss4j.crypto.merlin.";
    public static final String OLD_PREFIX = "org.apache.ws.security.crypto.merlin.";
    public static final String OLD_KEYSTORE_FILE = "file";
    public static final String CRYPTO_KEYSTORE_PROVIDER = "keystore.provider";
    public static final String CRYPTO_CERT_PROVIDER = "cert.provider";
    public static final String CRYPTO_CERT_PROVIDER_HANDLES_NAME_CONSTRAINTS = "cert.provider.nameconstraints";
    public static final String KEYSTORE_FILE = "keystore.file";
    public static final String KEYSTORE_PASSWORD = "keystore.password";
    public static final String KEYSTORE_TYPE = "keystore.type";
    public static final String KEYSTORE_ALIAS = "keystore.alias";
    public static final String KEYSTORE_PRIVATE_PASSWORD = "keystore.private.password";
    public static final String LOAD_CA_CERTS = "load.cacerts";
    public static final String TRUSTSTORE_FILE = "truststore.file";
    public static final String TRUSTSTORE_PASSWORD = "truststore.password";
    public static final String TRUSTSTORE_TYPE = "truststore.type";
    public static final String TRUSTSTORE_PROVIDER = "truststore.provider";
    public static final String X509_CRL_FILE = "x509crl.file";
    private static final Logger LOG = LoggerFactory.getLogger(Merlin.class);
    private static final String COMMA_SEPARATOR = ",";
    protected Properties properties;
    protected KeyStore keystore;
    protected KeyStore truststore;
    protected CertStore crlCertStore;
    protected boolean loadCACerts;
    protected boolean privatePasswordSet;
    protected PasswordEncryptor passwordEncryptor;
    private boolean certProviderHandlesNameConstraints = false;

    public Merlin() {
    }

    public Merlin(boolean loadCACerts, String cacertsPasswd) {
        if (this.truststore == null && loadCACerts) {
            String cacertsPath = System.getProperty("java.home") + "/lib/security/cacerts";
            try (InputStream cacertsIs = Files.newInputStream(Paths.get(cacertsPath, new String[0]), new OpenOption[0]);){
                this.truststore = KeyStore.getInstance(KeyStore.getDefaultType());
                this.truststore.load(cacertsIs, cacertsPasswd.toCharArray());
                this.loadCACerts = true;
            }
            catch (Exception e) {
                LOG.warn("CA certs could not be loaded: " + e.getMessage());
            }
        }
    }

    public Merlin(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor) throws WSSecurityException, IOException {
        this.loadProperties(properties, loader, passwordEncryptor);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public void loadProperties(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor) throws WSSecurityException, IOException {
        if (properties == null) {
            return;
        }
        this.properties = properties;
        this.passwordEncryptor = passwordEncryptor;
        prefix = "org.apache.wss4j.crypto.merlin.";
        for (Object key : properties.keySet()) {
            if (!(key instanceof String)) continue;
            propKey = (String)key;
            if (propKey.startsWith("org.apache.wss4j.crypto.merlin.")) break;
            if (!propKey.startsWith("org.apache.ws.security.crypto.merlin.")) continue;
            prefix = "org.apache.ws.security.crypto.merlin.";
            break;
        }
        if ((keystoreProvider = this.properties.getProperty(prefix + "keystore.provider")) != null && (keystoreProvider = keystoreProvider.trim()).length() == 0) {
            keystoreProvider = null;
        }
        if ((certProvider = properties.getProperty(prefix + "cert.provider")) != null) {
            this.setCryptoProvider(certProvider);
        }
        if ((cpNameConstraintsProp = properties.getProperty(prefix + "cert.provider.nameconstraints")) != null) {
            this.certProviderHandlesNameConstraints = Boolean.parseBoolean(cpNameConstraintsProp);
        }
        if ((alias = properties.getProperty(prefix + "keystore.alias")) != null) {
            alias = alias.trim();
            this.setDefaultX509Identifier(alias);
        }
        if ((keyStoreLocation = properties.getProperty(prefix + "keystore.file")) == null) {
            keyStoreLocation = properties.getProperty(prefix + "file");
        }
        if (keyStoreLocation != null) {
            keyStoreLocation = keyStoreLocation.trim();
            is = Merlin.loadInputStream(loader, keyStoreLocation);
            var11_11 = null;
            try {
                passwd = properties.getProperty(prefix + "keystore.password", "security");
                if (passwd != null) {
                    passwd = passwd.trim();
                    passwd = this.decryptPassword(passwd, passwordEncryptor);
                }
                if ((type = properties.getProperty(prefix + "keystore.type", KeyStore.getDefaultType())) != null) {
                    type = type.trim();
                }
                this.keystore = this.load(is, passwd, keystoreProvider, (String)type);
                Merlin.LOG.debug("The KeyStore {} of type {} has been loaded", (Object)keyStoreLocation, type);
                privatePasswd = properties.getProperty(prefix + "keystore.private.password");
                if (privatePasswd == null) ** GOTO lbl49
                this.privatePasswordSet = true;
            }
            catch (Throwable passwd) {
                var11_11 = passwd;
                throw passwd;
            }
            finally {
                if (is != null) {
                    Merlin.$closeResource(var11_11, is);
                }
            }
        } else {
            Merlin.LOG.debug("The KeyStore is not loaded as KEYSTORE_FILE is null");
        }
lbl49:
        // 3 sources

        trustProvider = this.properties.getProperty(prefix + "truststore.provider");
        if (trustProvider != null) {
            if ((trustProvider = trustProvider.trim()).length() == 0) {
                trustProvider = null;
            } else {
                this.setTrustProvider(trustProvider);
            }
        } else if (keystoreProvider != null) {
            trustProvider = keystoreProvider;
            this.setTrustProvider(trustProvider);
        }
        trustStoreLocation = properties.getProperty(prefix + "truststore.file");
        if (trustStoreLocation != null) {
            trustStoreLocation = trustStoreLocation.trim();
            is = Merlin.loadInputStream(loader, trustStoreLocation);
            type = null;
            try {
                passwd = properties.getProperty(prefix + "truststore.password", "changeit");
                if (passwd != null) {
                    passwd = passwd.trim();
                    passwd = this.decryptPassword(passwd, passwordEncryptor);
                }
                if ((type /* !! */  = properties.getProperty(prefix + "truststore.type", KeyStore.getDefaultType())) != null) {
                    type /* !! */  = type /* !! */ .trim();
                }
                this.truststore = this.load(is, passwd, trustProvider, (String)type /* !! */ );
                Merlin.LOG.debug("The TrustStore {} of type {} has been loaded", (Object)trustStoreLocation, (Object)type /* !! */ );
                this.loadCACerts = false;
            }
            catch (Throwable passwd) {
                type = passwd;
                throw passwd;
            }
            finally {
                if (is != null) {
                    Merlin.$closeResource((Throwable)type, is);
                }
            }
        }
        loadCacerts = properties.getProperty(prefix + "load.cacerts", "false");
        if (loadCacerts != null) {
            loadCacerts = loadCacerts.trim();
        }
        if (Boolean.valueOf(loadCacerts).booleanValue()) {
            cacertsPath = (System.getProperty("java.home") + "/lib/security/cacerts").trim();
            is = Files.newInputStream(Paths.get(cacertsPath, new String[0]), new OpenOption[0]);
            type /* !! */  = null;
            try {
                cacertsPasswd = properties.getProperty(prefix + "truststore.password", "changeit");
                if (cacertsPasswd != null) {
                    cacertsPasswd = cacertsPasswd.trim();
                    cacertsPasswd = this.decryptPassword(cacertsPasswd, passwordEncryptor);
                }
                this.truststore = this.load(is, cacertsPasswd, null, KeyStore.getDefaultType());
                Merlin.LOG.debug("CA certs have been loaded");
                this.loadCACerts = true;
            }
            catch (Throwable var16_22) {
                type /* !! */  = var16_22;
                throw var16_22;
            }
            finally {
                if (is != null) {
                    Merlin.$closeResource((Throwable)type /* !! */ , is);
                }
            }
        }
        if ((crlLocations = properties.getProperty(prefix + "x509crl.file")) != null) {
            splittedCrlsLocations = crlLocations.split(",");
            crls = new ArrayList<X509CRL>(splittedCrlsLocations.length);
            for (String crlLocation : splittedCrlsLocations) {
                try {
                    is = Merlin.loadInputStream(loader, crlLocation.trim());
                    var20_29 = null;
                    try {
                        cf = this.getCertificateFactory();
                        crl = (X509CRL)cf.generateCRL(is);
                        crls.add(crl);
                    }
                    catch (Throwable var21_31) {
                        var20_29 = var21_31;
                        throw var21_31;
                    }
                    finally {
                        if (is != null) {
                            Merlin.$closeResource(var20_29, is);
                        }
                    }
                }
                catch (Exception e) {
                    Merlin.LOG.debug(e.getMessage(), e);
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "ioError00");
                }
            }
            try {
                this.crlCertStore = keystoreProvider == null || keystoreProvider.length() == 0 ? CertStore.getInstance("Collection", new CollectionCertStoreParameters(crls)) : CertStore.getInstance("Collection", (CertStoreParameters)new CollectionCertStoreParameters(crls), keystoreProvider);
            }
            catch (Exception e) {
                Merlin.LOG.debug(e.getMessage(), e);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "ioError00");
            }
            Merlin.LOG.debug("The CRL files {} have been loaded", (Object)crlLocations);
        }
    }

    public static InputStream loadInputStream(ClassLoader loader, String location) throws WSSecurityException, IOException {
        return Loader.loadInputStream(loader, location);
    }

    protected KeyStore load(InputStream input, String storepass, String provider, String type) throws WSSecurityException {
        KeyStore ks = null;
        try {
            ks = provider == null || provider.length() == 0 ? KeyStore.getInstance(type) : KeyStore.getInstance(type, provider);
            ks.load(input, storepass == null || storepass.length() == 0 ? new char[]{} : storepass.toCharArray());
        }
        catch (IOException | GeneralSecurityException e) {
            LOG.debug(e.getMessage(), e);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "failedCredentialLoad");
        }
        return ks;
    }

    public KeyStore getKeyStore() {
        return this.keystore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keystore = keyStore;
    }

    public KeyStore getTrustStore() {
        return this.truststore;
    }

    public void setTrustStore(KeyStore trustStore) {
        this.truststore = trustStore;
    }

    public void setCRLCertStore(CertStore crlCertStore) {
        this.crlCertStore = crlCertStore;
    }

    public CertStore getCRLCertStore() {
        return this.crlCertStore;
    }

    @Override
    public CertificateFactory getCertificateFactory() throws WSSecurityException {
        block10: {
            if (this.certificateFactory != null) {
                return this.certificateFactory;
            }
            String provider = this.getCryptoProvider();
            String keyStoreProvider = null;
            if (this.keystore != null) {
                keyStoreProvider = this.keystore.getProvider().getName();
            }
            try {
                if (provider == null || provider.length() == 0) {
                    if (keyStoreProvider != null && keyStoreProvider.length() != 0) {
                        try {
                            this.certificateFactory = CertificateFactory.getInstance("X.509", this.mapKeystoreProviderToCertProvider(keyStoreProvider));
                        }
                        catch (Exception ex) {
                            LOG.debug("The keystore provider '" + keyStoreProvider + "' does not support X.509 because \"" + ex.getMessage() + "\". The JVM default provider will be tried out next", ex);
                        }
                    }
                    if (this.certificateFactory == null) {
                        this.certificateFactory = CertificateFactory.getInstance("X.509");
                    }
                    break block10;
                }
                this.certificateFactory = CertificateFactory.getInstance("X.509", provider);
            }
            catch (CertificateException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "unsupportedCertType");
            }
            catch (NoSuchProviderException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "noSecProvider");
            }
        }
        return this.certificateFactory;
    }

    private String mapKeystoreProviderToCertProvider(String s2) {
        if ("SunJSSE".equals(s2)) {
            return "SUN";
        }
        return s2;
    }

    @Override
    public String getDefaultX509Identifier() throws WSSecurityException {
        if (super.getDefaultX509Identifier() != null) {
            return super.getDefaultX509Identifier();
        }
        if (this.keystore != null) {
            try {
                Enumeration<String> as = this.keystore.aliases();
                if (as.hasMoreElements()) {
                    String alias = as.nextElement();
                    if (!as.hasMoreElements()) {
                        this.setDefaultX509Identifier(alias);
                        return alias;
                    }
                }
            }
            catch (KeyStoreException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "keystore");
            }
        }
        return null;
    }

    @Override
    public X509Certificate[] getX509Certificates(CryptoType cryptoType) throws WSSecurityException {
        if (cryptoType == null) {
            return null;
        }
        CryptoType.TYPE type = cryptoType.getType();
        X509Certificate[] certs = null;
        switch (type) {
            case ISSUER_SERIAL: {
                certs = this.getX509Certificates(cryptoType.getIssuer(), cryptoType.getSerial());
                break;
            }
            case THUMBPRINT_SHA1: {
                certs = this.getX509Certificates(cryptoType.getBytes());
                break;
            }
            case SKI_BYTES: {
                certs = this.getX509CertificatesSKI(cryptoType.getBytes());
                break;
            }
            case SUBJECT_DN: {
                certs = this.getX509CertificatesSubjectDN(cryptoType.getSubjectDN());
                break;
            }
            case ALIAS: {
                certs = this.getX509Certificates(cryptoType.getAlias());
                break;
            }
        }
        return certs;
    }

    @Override
    public String getX509Identifier(X509Certificate cert) throws WSSecurityException {
        String identifier = null;
        if (this.keystore != null) {
            identifier = this.getIdentifier(cert, this.keystore);
        }
        if (identifier == null && this.truststore != null) {
            identifier = this.getIdentifier(cert, this.truststore);
        }
        return identifier;
    }

    @Override
    public PrivateKey getPrivateKey(X509Certificate certificate, CallbackHandler callbackHandler) throws WSSecurityException {
        if (this.keystore == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The keystore is null"});
        }
        if (callbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The CallbackHandler is null"});
        }
        String identifier = this.getIdentifier(certificate, this.keystore);
        if (identifier == null) {
            try {
                String msg = "Cannot find key for certificate";
                String logMsg = Merlin.createKeyStoreErrorMessage(this.keystore);
                LOG.error(msg + logMsg);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{msg});
            }
            catch (KeyStoreException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "noPrivateKey", new Object[]{ex.getMessage()});
            }
        }
        String password = this.getPassword(identifier, callbackHandler);
        return this.getPrivateKey(identifier, password);
    }

    @Override
    public PrivateKey getPrivateKey(PublicKey publicKey, CallbackHandler callbackHandler) throws WSSecurityException {
        if (this.keystore == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The keystore is null"});
        }
        if (callbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The CallbackHandler is null"});
        }
        String identifier = this.getIdentifier(publicKey, this.keystore);
        if (identifier == null) {
            try {
                String msg = "Cannot find key for corresponding public key";
                String logMsg = Merlin.createKeyStoreErrorMessage(this.keystore);
                LOG.error(msg + logMsg);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{msg});
            }
            catch (KeyStoreException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "noPrivateKey", new Object[]{ex.getMessage()});
            }
        }
        String password = this.getPassword(identifier, callbackHandler);
        return this.getPrivateKey(identifier, password);
    }

    @Override
    public PrivateKey getPrivateKey(String identifier, String password) throws WSSecurityException {
        if (this.keystore == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The keystore is null"});
        }
        try {
            Key keyTmp;
            if (identifier == null || !this.keystore.isKeyEntry(identifier)) {
                String msg = "Cannot find key for alias: [" + identifier + "]";
                String logMsg = Merlin.createKeyStoreErrorMessage(this.keystore);
                LOG.error(msg + logMsg);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{msg});
            }
            String pwd = password;
            if (pwd == null && this.privatePasswordSet) {
                pwd = this.properties.getProperty("org.apache.wss4j.crypto.merlin.keystore.private.password");
                if (pwd == null) {
                    pwd = this.properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.private.password");
                }
                if (pwd != null) {
                    pwd = pwd.trim();
                    pwd = this.decryptPassword(pwd, this.passwordEncryptor);
                }
            }
            if (!((keyTmp = this.keystore.getKey(identifier, pwd == null ? new char[]{} : pwd.toCharArray())) instanceof PrivateKey)) {
                String msg = "Key is not a private key, alias: [" + identifier + "]";
                String logMsg = Merlin.createKeyStoreErrorMessage(this.keystore);
                LOG.error(msg + logMsg);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{msg});
            }
            return (PrivateKey)keyTmp;
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "noPrivateKey", new Object[]{ex.getMessage()});
        }
    }

    protected void verifyTrust(X509Certificate[] certs, boolean enableRevocation, Collection<Pattern> subjectCertConstraints) throws WSSecurityException {
        if (certs.length == 1 && !enableRevocation) {
            String issuerString = certs[0].getIssuerX500Principal().getName();
            BigInteger issuerSerial = certs[0].getSerialNumber();
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
            cryptoType.setIssuerSerial(issuerString, issuerSerial);
            X509Certificate[] foundCerts = this.getX509Certificates(cryptoType);
            if (foundCerts != null && foundCerts[0] != null && foundCerts[0].equals(certs[0])) {
                try {
                    certs[0].checkValidity();
                }
                catch (CertificateExpiredException | CertificateNotYetValidException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, e, "invalidCert");
                }
                LOG.debug("Direct trust for certificate with {}", (Object)certs[0].getSubjectX500Principal().getName());
                return;
            }
        }
        List<Certificate[]> foundIssuingCertChains = null;
        String issuerString = certs[0].getIssuerX500Principal().getName();
        if (certs.length == 1) {
            Object subject = this.convertSubjectToPrincipal(issuerString);
            if (this.keystore != null) {
                foundIssuingCertChains = this.getCertificates(subject, this.keystore, false);
            }
            if ((foundIssuingCertChains == null || foundIssuingCertChains.isEmpty()) && this.truststore != null) {
                foundIssuingCertChains = this.getCertificates(subject, this.truststore, true);
            }
            if (foundIssuingCertChains == null || foundIssuingCertChains.isEmpty() || foundIssuingCertChains.get(0).length < 1) {
                String subjectString = certs[0].getSubjectX500Principal().getName();
                LOG.debug("No certs found in keystore for issuer {} of certificate for {}", (Object)issuerString, (Object)subjectString);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "certpath", new Object[]{"No trusted certs found"});
            }
        }
        LOG.debug("Preparing to validate certificate path for issuer {}", (Object)issuerString);
        try {
            HashSet<TrustAnchor> set = new HashSet<TrustAnchor>();
            if (this.truststore != null) {
                this.addTrustAnchors(set, this.truststore);
            }
            if (this.keystore != null && (this.truststore == null || this.loadCACerts)) {
                this.addTrustAnchors(set, this.keystore);
            }
            String provider = this.getCryptoProvider();
            CertPathValidator validator = null;
            validator = provider == null || provider.length() == 0 ? CertPathValidator.getInstance("PKIX") : CertPathValidator.getInstance("PKIX", provider);
            PKIXParameters param2 = this.createPKIXParameters(set, enableRevocation);
            if (foundIssuingCertChains != null && !foundIssuingCertChains.isEmpty()) {
                CertPathValidatorException validatorException = null;
                for (Certificate[] foundCertChain : foundIssuingCertChains) {
                    X509Certificate[] x509certs = new X509Certificate[foundCertChain.length + 1];
                    x509certs[0] = certs[0];
                    System.arraycopy(foundCertChain, 0, x509certs, 1, foundCertChain.length);
                    List<X509Certificate> certList = Arrays.asList(x509certs);
                    CertPath path = this.getCertificateFactory().generateCertPath(certList);
                    try {
                        validator.validate(path, param2);
                        validatorException = null;
                        break;
                    }
                    catch (CertPathValidatorException e) {
                        validatorException = e;
                    }
                }
                if (validatorException != null) {
                    throw validatorException;
                }
            } else {
                List<X509Certificate> certList = Arrays.asList(certs);
                CertPath path = this.getCertificateFactory().generateCertPath(certList);
                validator.validate(path, param2);
            }
        }
        catch (InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertPathValidatorException | CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "certpath");
        }
        if (!this.matchesSubjectDnPattern(certs[0], subjectCertConstraints)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }

    @Override
    public void verifyTrust(X509Certificate[] certs, boolean enableRevocation, Collection<Pattern> subjectCertConstraints, Collection<Pattern> issuerCertConstraints) throws WSSecurityException {
        this.verifyTrust(certs, enableRevocation, subjectCertConstraints);
        if (!this.matchesIssuerDnPattern(certs[0], issuerCertConstraints)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }

    protected PKIXParameters createPKIXParameters(Set<TrustAnchor> trustAnchors, boolean enableRevocation) throws InvalidAlgorithmParameterException {
        PKIXParameters param2 = new PKIXParameters(trustAnchors);
        param2.setRevocationEnabled(enableRevocation);
        if (enableRevocation && this.crlCertStore != null) {
            param2.addCertStore(this.crlCertStore);
        }
        return param2;
    }

    @Override
    public void verifyTrust(PublicKey publicKey) throws WSSecurityException {
        if (publicKey == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        if (!this.findPublicKeyInKeyStore(publicKey, this.keystore, false) && !this.findPublicKeyInKeyStore(publicKey, this.truststore, true)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }

    private X509Certificate[] getX509Certificates(String issuer, BigInteger serialNumber) throws WSSecurityException {
        Object issuerName = null;
        try {
            X500Principal issuerRDN = new X500Principal(issuer);
            issuerName = this.createBCX509Name(issuerRDN.getName());
        }
        catch (IllegalArgumentException ex) {
            issuerName = this.createBCX509Name(issuer);
        }
        Certificate[] certs = null;
        if (this.keystore != null) {
            certs = this.getCertificates(issuerName, serialNumber, this.keystore, false);
        }
        if ((certs == null || certs.length == 0) && this.truststore != null) {
            certs = this.getCertificates(issuerName, serialNumber, this.truststore, true);
        }
        if (certs == null || certs.length == 0) {
            return null;
        }
        return (X509Certificate[])Arrays.copyOf(certs, certs.length, X509Certificate[].class);
    }

    private Certificate[] getCertificates(Object issuerRDN, BigInteger serialNumber, KeyStore store, boolean truststore) throws WSSecurityException {
        String keystore = "keystore";
        if (truststore) {
            keystore = "truststore";
        }
        LOG.debug("Searching {} for cert with issuer {} and serial {}", keystore, issuerRDN, serialNumber);
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                Object certName;
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate)) continue;
                X509Certificate x509cert = (X509Certificate)certs[0];
                LOG.debug("Keystore alias {} has issuer {} and serial {}", alias, x509cert.getIssuerX500Principal().getName(), x509cert.getSerialNumber());
                if (x509cert.getSerialNumber().compareTo(serialNumber) != 0 || !(certName = this.createBCX509Name(x509cert.getIssuerX500Principal().getName())).equals(issuerRDN)) continue;
                LOG.debug("Issuer Serial match found using keystore alias {}", (Object)alias);
                return certs;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        LOG.debug("No issuer serial match found in {}", (Object)keystore);
        return new Certificate[0];
    }

    private X509Certificate[] getX509Certificates(byte[] thumbprint) throws WSSecurityException {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "decoding.general");
        }
        Certificate[] certs = null;
        if (this.keystore != null) {
            certs = this.getCertificates(thumbprint, this.keystore, sha, false);
        }
        if ((certs == null || certs.length == 0) && this.truststore != null) {
            certs = this.getCertificates(thumbprint, this.truststore, sha, true);
        }
        if (certs == null || certs.length == 0) {
            return null;
        }
        return (X509Certificate[])Arrays.copyOf(certs, certs.length, X509Certificate[].class);
    }

    private Certificate[] getCertificates(byte[] thumbprint, KeyStore store, MessageDigest sha, boolean truststore) throws WSSecurityException {
        String keystore = "keystore";
        if (truststore) {
            keystore = "truststore";
        }
        LOG.debug("Searching {} for cert using a SHA-1 thumbprint", (Object)keystore);
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate)) continue;
                X509Certificate x509cert = (X509Certificate)certs[0];
                try {
                    sha.update(x509cert.getEncoded());
                }
                catch (CertificateEncodingException ex) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, ex, "encodeError");
                }
                byte[] data = sha.digest();
                if (!Arrays.equals(data, thumbprint)) continue;
                LOG.debug("Thumbprint match found using keystore alias {}", (Object)alias);
                return certs;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        LOG.debug("No thumbprint match found in {}", (Object)keystore);
        return new Certificate[0];
    }

    private X509Certificate[] getX509CertificatesSKI(byte[] skiBytes) throws WSSecurityException {
        Certificate[] certs = null;
        if (this.keystore != null) {
            certs = this.getCertificates(skiBytes, this.keystore, false);
        }
        if ((certs == null || certs.length == 0) && this.truststore != null) {
            certs = this.getCertificates(skiBytes, this.truststore, true);
        }
        if (certs == null || certs.length == 0) {
            return null;
        }
        return (X509Certificate[])Arrays.copyOf(certs, certs.length, X509Certificate[].class);
    }

    private Certificate[] getCertificates(byte[] skiBytes, KeyStore store, boolean truststore) throws WSSecurityException {
        String keystore = "keystore";
        if (truststore) {
            keystore = "truststore";
        }
        LOG.debug("Searching {} for cert using Subject Key Identifier bytes", (Object)keystore);
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                X509Certificate x509cert;
                byte[] data;
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate) || (data = this.getSKIBytesFromCert(x509cert = (X509Certificate)certs[0])).length != skiBytes.length || !Arrays.equals(data, skiBytes)) continue;
                LOG.debug("SKI match found using keystore alias {}", (Object)alias);
                return certs;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        LOG.debug("No SKI match found in {}", (Object)keystore);
        return new Certificate[0];
    }

    private X509Certificate[] getX509CertificatesSubjectDN(String subjectDN) throws WSSecurityException {
        Object subject = this.convertSubjectToPrincipal(subjectDN);
        List<Certificate[]> certs = null;
        if (this.keystore != null) {
            certs = this.getCertificates(subject, this.keystore, false);
        }
        if ((certs == null || certs.isEmpty()) && this.truststore != null) {
            certs = this.getCertificates(subject, this.truststore, true);
        }
        if (certs == null || certs.isEmpty()) {
            return null;
        }
        return (X509Certificate[])Arrays.copyOf(certs.get(0), certs.get(0).length, X509Certificate[].class);
    }

    private Object convertSubjectToPrincipal(String subjectDN) {
        Object subject;
        try {
            X500Principal subjectRDN = new X500Principal(subjectDN);
            subject = this.createBCX509Name(subjectRDN.getName());
        }
        catch (IllegalArgumentException ex) {
            subject = this.createBCX509Name(subjectDN);
        }
        return subject;
    }

    private X509Certificate[] getX509Certificates(String identifier) throws WSSecurityException {
        if (identifier == null) {
            return null;
        }
        Certificate[] certs = null;
        try {
            Certificate cert;
            if (this.keystore != null && ((certs = this.keystore.getCertificateChain(identifier)) == null || certs.length == 0) && (cert = this.keystore.getCertificate(identifier)) != null) {
                certs = new Certificate[]{cert};
            }
            if (certs == null && this.truststore != null && (certs = this.truststore.getCertificateChain(identifier)) == null && (cert = this.truststore.getCertificate(identifier)) != null) {
                certs = new Certificate[]{cert};
            }
            if (certs == null) {
                return null;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        return (X509Certificate[])Arrays.copyOf(certs, certs.length, X509Certificate[].class);
    }

    private boolean findPublicKeyInKeyStore(PublicKey publicKey, KeyStore keyStoreToSearch, boolean truststore) {
        if (keyStoreToSearch == null) {
            return false;
        }
        String keystore = "keystore";
        if (truststore) {
            keystore = "truststore";
        }
        LOG.debug("Searching {} for public key {}", (Object)keystore, (Object)publicKey);
        try {
            Enumeration<String> e = keyStoreToSearch.aliases();
            while (e.hasMoreElements()) {
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = keyStoreToSearch.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = keyStoreToSearch.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate) || !publicKey.equals(((X509Certificate)certs[0]).getPublicKey())) continue;
                LOG.debug("PublicKey match found using keystore alias {}", (Object)alias);
                return true;
            }
        }
        catch (KeyStoreException e) {
            return false;
        }
        LOG.debug("No PublicKey match found in {}", (Object)keystore);
        return false;
    }

    private List<Certificate[]> getCertificates(Object subjectRDN, KeyStore store, boolean truststore) throws WSSecurityException {
        String keystore = "keystore";
        if (truststore) {
            keystore = "truststore";
        }
        LOG.debug("Searching {} for cert with Subject {}", (Object)keystore, subjectRDN);
        ArrayList<Certificate[]> foundCerts = new ArrayList<Certificate[]>();
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                X500Principal foundRDN;
                Object certName;
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate) || !subjectRDN.equals(certName = this.createBCX509Name((foundRDN = ((X509Certificate)certs[0]).getSubjectX500Principal()).getName()))) continue;
                LOG.debug("Subject certificate match found using keystore alias {}", (Object)alias);
                foundCerts.add(certs);
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        if (foundCerts.isEmpty()) {
            LOG.debug("No Subject match found in {}", (Object)keystore);
        }
        return foundCerts;
    }

    private static String createKeyStoreErrorMessage(KeyStore keystore) throws KeyStoreException {
        Enumeration<String> aliases = keystore.aliases();
        StringBuilder sb = new StringBuilder(keystore.size() * 7);
        boolean firstAlias = true;
        while (aliases.hasMoreElements()) {
            if (!firstAlias) {
                sb.append(", ");
            }
            sb.append(aliases.nextElement());
            firstAlias = false;
        }
        String msg = " in keystore of type [" + keystore.getType() + "] from provider [" + keystore.getProvider() + "] with size [" + keystore.size() + "] and aliases: {" + sb.toString() + "}";
        return msg;
    }

    protected void addTrustAnchors(Set<TrustAnchor> set, KeyStore keyStore) throws KeyStoreException, WSSecurityException {
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            TrustAnchor anchor;
            String alias = aliases.nextElement();
            X509Certificate cert = (X509Certificate)keyStore.getCertificate(alias);
            if (cert == null) continue;
            if (this.certProviderHandlesNameConstraints) {
                anchor = new TrustAnchor(cert, this.getNameConstraints(cert));
                set.add(anchor);
                continue;
            }
            anchor = new TrustAnchor(cert, null);
            set.add(anchor);
        }
    }

    private String getIdentifier(X509Certificate cert, KeyStore store) throws WSSecurityException {
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                Certificate retrievedCert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (retrievedCert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{retrievedCert};
                }
                if (certs == null || certs.length <= 0 || !certs[0].equals(cert)) continue;
                return alias;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        return null;
    }

    private String getIdentifier(PublicKey publicKey, KeyStore store) throws WSSecurityException {
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                Certificate retrievedCert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (retrievedCert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{retrievedCert};
                }
                if (certs == null || certs.length <= 0 || !certs[0].getPublicKey().equals(publicKey)) continue;
                return alias;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        return null;
    }

    private String getPassword(String identifier, CallbackHandler cb) throws WSSecurityException {
        WSPasswordCallback pwCb = new WSPasswordCallback(identifier, 1);
        try {
            Callback[] callbacks = new Callback[]{pwCb};
            cb.handle(callbacks);
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noPassword", new Object[]{identifier});
        }
        return pwCb.getPassword();
    }

    protected String decryptPassword(String password, PasswordEncryptor passwordEncryptor) {
        if (password.startsWith(ENCRYPTED_PASSWORD_PREFIX) && password.endsWith(ENCRYPTED_PASSWORD_SUFFIX)) {
            if (passwordEncryptor == null) {
                String error = "The Crypto properties has an encrypted password, but no PasswordEncryptor is configured!";
                LOG.debug(error);
                return password;
            }
            String substring = password.substring(ENCRYPTED_PASSWORD_PREFIX.length(), password.length() - 1);
            return passwordEncryptor.decrypt(substring);
        }
        return password;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }
}

