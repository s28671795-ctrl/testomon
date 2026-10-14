/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.x500.X500Principal;
import org.apache.wss4j.common.crypto.CryptoBase;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateStore
extends CryptoBase {
    private static final Logger LOG = LoggerFactory.getLogger(CertificateStore.class);
    private X509Certificate[] trustedCerts;

    public CertificateStore(X509Certificate[] trustedCerts) {
        this.trustedCerts = trustedCerts;
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
            case ALIAS: 
            case SUBJECT_DN: {
                certs = this.getX509CertificatesSubjectDN(cryptoType.getSubjectDN());
                break;
            }
        }
        return certs;
    }

    @Override
    public String getX509Identifier(X509Certificate cert) throws WSSecurityException {
        return cert.getSubjectDN().toString();
    }

    @Override
    public PrivateKey getPrivateKey(X509Certificate certificate, CallbackHandler callbackHandler) throws WSSecurityException {
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(PublicKey publicKey, CallbackHandler callbackHandler) throws WSSecurityException {
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(String identifier, String password) throws WSSecurityException {
        return null;
    }

    protected void verifyTrust(X509Certificate[] certs, boolean enableRevocation, Collection<Pattern> subjectCertConstraints) throws WSSecurityException {
        CryptoType cryptoType;
        String issuerString;
        if (certs.length == 1 && !enableRevocation) {
            issuerString = certs[0].getIssuerX500Principal().getName();
            BigInteger issuerSerial = certs[0].getSerialNumber();
            cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
            cryptoType.setIssuerSerial(issuerString, issuerSerial);
            X509Certificate[] x509CertificateArray = this.getX509Certificates(cryptoType);
            if (x509CertificateArray != null && x509CertificateArray[0] != null && x509CertificateArray[0].equals(certs[0])) {
                LOG.debug("Direct trust for certificate with {}", (Object)certs[0].getSubjectX500Principal().getName());
                return;
            }
        }
        issuerString = certs[0].getIssuerX500Principal().getName();
        X509Certificate[] foundCerts = new X509Certificate[]{};
        if (certs.length == 1) {
            cryptoType = new CryptoType(CryptoType.TYPE.SUBJECT_DN);
            cryptoType.setSubjectDN(issuerString);
            foundCerts = this.getX509Certificates(cryptoType);
            if (foundCerts == null || foundCerts.length < 1) {
                String string = certs[0].getSubjectX500Principal().getName();
                LOG.debug("No certs found in keystore for issuer {} of certificate for {}", (Object)issuerString, (Object)string);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "certpath", new Object[]{"No trusted certs found"});
            }
        }
        LOG.debug("Preparing to validate certificate path for issuer {}", (Object)issuerString);
        try {
            HashSet<TrustAnchor> set = new HashSet<TrustAnchor>();
            if (this.trustedCerts != null) {
                for (X509Certificate cert : this.trustedCerts) {
                    TrustAnchor anchor = new TrustAnchor(cert, null);
                    set.add(anchor);
                }
            }
            String string = this.getCryptoProvider();
            CertPathValidator validator = null;
            validator = string == null || string.length() == 0 ? CertPathValidator.getInstance("PKIX") : CertPathValidator.getInstance("PKIX", string);
            PKIXParameters param2 = new PKIXParameters(set);
            param2.setRevocationEnabled(enableRevocation);
            if (foundCerts.length > 0) {
                X509Certificate[] x509certs = new X509Certificate[foundCerts.length + 1];
                x509certs[0] = certs[0];
                System.arraycopy(foundCerts, 0, x509certs, 1, foundCerts.length);
                List<X509Certificate> certList = Arrays.asList(x509certs);
                CertPath path = this.getCertificateFactory().generateCertPath(certList);
                validator.validate(path, param2);
            } else {
                List<X509Certificate> certList = Arrays.asList(certs);
                CertPath path = this.getCertificateFactory().generateCertPath(certList);
                validator.validate(path, param2);
            }
        }
        catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | CertPathValidatorException | CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "certpath", new Object[]{e.getMessage()});
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

    @Override
    public void verifyTrust(PublicKey publicKey) throws WSSecurityException {
        if (publicKey == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        for (X509Certificate trustedCert : this.trustedCerts) {
            if (!publicKey.equals(trustedCert.getPublicKey())) continue;
            return;
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
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
        for (X509Certificate trustedCert : this.trustedCerts) {
            Object certName;
            if (trustedCert.getSerialNumber().compareTo(serialNumber) != 0 || !(certName = this.createBCX509Name(trustedCert.getIssuerX500Principal().getName())).equals(issuerName)) continue;
            return new X509Certificate[]{trustedCert};
        }
        return null;
    }

    private X509Certificate[] getX509Certificates(byte[] thumb) throws WSSecurityException {
        MessageDigest sha = null;
        if (this.trustedCerts == null) {
            return null;
        }
        try {
            sha = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "decoding.general");
        }
        for (X509Certificate trustedCert : this.trustedCerts) {
            try {
                sha.update(trustedCert.getEncoded());
            }
            catch (CertificateEncodingException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, ex, "encodeError");
            }
            byte[] data = sha.digest();
            if (!Arrays.equals(data, thumb)) continue;
            return new X509Certificate[]{trustedCert};
        }
        return null;
    }

    private X509Certificate[] getX509CertificatesSKI(byte[] skiBytes) throws WSSecurityException {
        if (this.trustedCerts == null) {
            return null;
        }
        for (X509Certificate trustedCert : this.trustedCerts) {
            byte[] data = this.getSKIBytesFromCert(trustedCert);
            if (data.length != skiBytes.length || !Arrays.equals(data, skiBytes)) continue;
            return new X509Certificate[]{trustedCert};
        }
        return null;
    }

    private X509Certificate[] getX509CertificatesSubjectDN(String subjectDN) throws WSSecurityException {
        Object subject;
        try {
            X500Principal subjectRDN = new X500Principal(subjectDN);
            subject = this.createBCX509Name(subjectRDN.getName());
        }
        catch (IllegalArgumentException ex) {
            subject = this.createBCX509Name(subjectDN);
        }
        if (this.trustedCerts != null) {
            for (X509Certificate trustedCert : this.trustedCerts) {
                X500Principal foundRDN = trustedCert.getSubjectX500Principal();
                Object certName = this.createBCX509Name(foundRDN.getName());
                if (!subject.equals(certName)) continue;
                return new X509Certificate[]{trustedCert};
            }
        }
        return null;
    }
}

