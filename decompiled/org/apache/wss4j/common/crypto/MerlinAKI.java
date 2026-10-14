/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.wss4j.common.crypto.BouncyCastleUtils;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerlinAKI
extends Merlin {
    private static final Logger LOG = LoggerFactory.getLogger(MerlinAKI.class);

    public MerlinAKI() {
    }

    public MerlinAKI(boolean loadCACerts, String cacertsPasswd) {
        super(loadCACerts, cacertsPasswd);
    }

    public MerlinAKI(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor) throws WSSecurityException, IOException {
        super(properties, loader, passwordEncryptor);
    }

    @Override
    protected void verifyTrust(X509Certificate[] certs, boolean enableRevocation, Collection<Pattern> subjectCertConstraints) throws WSSecurityException {
        X509Certificate[] foundCerts;
        if (certs.length == 1 && !enableRevocation) {
            String issuerString = certs[0].getIssuerX500Principal().getName();
            BigInteger issuerSerial = certs[0].getSerialNumber();
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
            cryptoType.setIssuerSerial(issuerString, issuerSerial);
            foundCerts = this.getX509Certificates(cryptoType);
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
        X509Certificate[] x509certs = certs;
        String issuerString = certs[0].getIssuerX500Principal().getName();
        try {
            if (certs.length == 1) {
                byte[] keyIdentifierBytes = BouncyCastleUtils.getAuthorityKeyIdentifierBytes(certs[0]);
                foundCerts = this.getX509CertificatesFromKeyIdentifier(keyIdentifierBytes);
                if (foundCerts == null || foundCerts.length < 1) {
                    String subjectString = certs[0].getSubjectX500Principal().getName();
                    LOG.debug("No certs found in keystore for issuer {} of certificate for {}", (Object)issuerString, (Object)subjectString);
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "certpath", new Object[]{"No trusted certs found"});
                }
                x509certs = new X509Certificate[foundCerts.length + 1];
                x509certs[0] = certs[0];
                System.arraycopy(foundCerts, 0, x509certs, 1, foundCerts.length);
            }
        }
        catch (NoSuchAlgorithmException | CertificateException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "certpath");
        }
        LOG.debug("Preparing to validate certificate path for issuer {}", (Object)issuerString);
        try {
            List<X509Certificate> certList = Arrays.asList(x509certs);
            CertPath path = this.getCertificateFactory().generateCertPath(certList);
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
            validator.validate(path, param2);
        }
        catch (InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertPathValidatorException | CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "certpath");
        }
        if (!this.matchesSubjectDnPattern(certs[0], subjectCertConstraints)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
    }

    private X509Certificate[] getX509CertificatesFromKeyIdentifier(byte[] keyIdentifierBytes) throws WSSecurityException, NoSuchAlgorithmException, CertificateEncodingException {
        if (keyIdentifierBytes == null) {
            return null;
        }
        Certificate[] certs = null;
        if (this.keystore != null) {
            certs = this.getCertificates(keyIdentifierBytes, this.keystore);
        }
        if ((certs == null || certs.length == 0) && this.truststore != null) {
            certs = this.getCertificates(keyIdentifierBytes, this.truststore);
        }
        if (certs == null || certs.length == 0) {
            return null;
        }
        return (X509Certificate[])Arrays.copyOf(certs, certs.length, X509Certificate[].class);
    }

    private Certificate[] getCertificates(byte[] keyIdentifier, KeyStore store) throws WSSecurityException, NoSuchAlgorithmException, CertificateEncodingException {
        try {
            Enumeration<String> e = store.aliases();
            while (e.hasMoreElements()) {
                byte[] subjectKeyIdentifier;
                Certificate cert;
                String alias = e.nextElement();
                Certificate[] certs = store.getCertificateChain(alias);
                if ((certs == null || certs.length == 0) && (cert = store.getCertificate(alias)) != null) {
                    certs = new Certificate[]{cert};
                }
                if (certs == null || certs.length <= 0 || !(certs[0] instanceof X509Certificate) || (subjectKeyIdentifier = BouncyCastleUtils.getSubjectKeyIdentifierBytes((X509Certificate)certs[0])) == null || !Arrays.equals(subjectKeyIdentifier, keyIdentifier)) continue;
                return certs;
            }
        }
        catch (KeyStoreException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "keystore");
        }
        return new Certificate[0];
    }
}

