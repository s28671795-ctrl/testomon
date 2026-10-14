/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.regex.Pattern;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureTrustValidator
implements Validator {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureTrustValidator.class);

    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (credential == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCredential");
        }
        X509Certificate[] certs = credential.getCertificates();
        PublicKey publicKey = credential.getPublicKey();
        Crypto crypto = this.getCrypto(data);
        if (crypto == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
        }
        if (certs != null && certs.length > 0) {
            this.validateCertificates(certs);
            this.verifyTrustInCerts(certs, crypto, data, data.isRevocationEnabled());
            return credential;
        }
        if (publicKey != null) {
            this.validatePublicKey(publicKey, crypto);
            return credential;
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
    }

    protected Crypto getCrypto(RequestData data) {
        return data.getSigVerCrypto();
    }

    protected void validateCertificates(X509Certificate[] certificates) throws WSSecurityException {
    }

    protected void verifyTrustInCerts(X509Certificate[] certificates, Crypto crypto, RequestData data, boolean enableRevocation) throws WSSecurityException {
        Collection<Pattern> subjectCertConstraints = data.getSubjectCertConstraints();
        Collection<Pattern> issuerCertConstraints = data.getIssuerDNPatterns();
        crypto.verifyTrust(certificates, enableRevocation, subjectCertConstraints, issuerCertConstraints);
        String subjectString = certificates[0].getSubjectX500Principal().getName();
        LOG.debug("Certificate path has been verified for certificate with subject {}", (Object)subjectString);
    }

    protected void validatePublicKey(PublicKey publicKey, Crypto crypto) throws WSSecurityException {
        crypto.verifyTrust(publicKey);
    }
}

