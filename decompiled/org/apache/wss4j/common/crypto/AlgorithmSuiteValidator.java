/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECParameterSpec;
import java.util.Iterator;
import java.util.Set;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorithmSuiteValidator {
    private static final Logger LOG = LoggerFactory.getLogger(AlgorithmSuiteValidator.class);
    private final AlgorithmSuite algorithmSuite;

    public AlgorithmSuiteValidator(AlgorithmSuite algorithmSuite) {
        this.algorithmSuite = algorithmSuite;
    }

    public void checkSignatureMethod(String signatureMethod) throws WSSecurityException {
        Set<String> allowedSignatureMethods = this.algorithmSuite.getSignatureMethods();
        if (!allowedSignatureMethods.isEmpty() && !allowedSignatureMethods.contains(signatureMethod)) {
            LOG.debug("SignatureMethod " + signatureMethod + " does not match required values");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkC14nAlgorithm(String c14nAlgorithm) throws WSSecurityException {
        Set<String> allowedC14nAlgorithms = this.algorithmSuite.getC14nAlgorithms();
        if (!allowedC14nAlgorithms.isEmpty() && !allowedC14nAlgorithms.contains(c14nAlgorithm)) {
            LOG.debug("C14nMethod " + c14nAlgorithm + " does not match required value");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkSignatureAlgorithms(XMLSignature xmlSignature) throws WSSecurityException {
        String signatureMethod = xmlSignature.getSignedInfo().getSignatureMethod().getAlgorithm();
        this.checkSignatureMethod(signatureMethod);
        String c14nMethod = xmlSignature.getSignedInfo().getCanonicalizationMethod().getAlgorithm();
        this.checkC14nAlgorithm(c14nMethod);
        Iterator<Reference> iterator2 = xmlSignature.getSignedInfo().getReferences().iterator();
        while (iterator2.hasNext()) {
            Reference refObject;
            Reference reference = refObject = iterator2.next();
            String digestMethod = reference.getDigestMethod().getAlgorithm();
            Set<String> allowedDigestAlgorithms = this.algorithmSuite.getDigestAlgorithms();
            if (!allowedDigestAlgorithms.isEmpty() && !allowedDigestAlgorithms.contains(digestMethod)) {
                LOG.debug("DigestMethod " + digestMethod + " does not match required value");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
            for (int i = 0; i < reference.getTransforms().size(); ++i) {
                Transform transform = reference.getTransforms().get(i);
                String algorithm = transform.getAlgorithm();
                Set<String> allowedTransformAlgorithms = this.algorithmSuite.getTransformAlgorithms();
                if (allowedTransformAlgorithms.isEmpty() || allowedTransformAlgorithms.contains(algorithm)) continue;
                LOG.debug("Transform method " + algorithm + " does not match required value");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        }
    }

    public void checkEncryptionKeyWrapAlgorithm(String keyWrapAlgorithm) throws WSSecurityException {
        Set<String> keyWrapAlgorithms = this.algorithmSuite.getKeyWrapAlgorithms();
        if (!keyWrapAlgorithms.isEmpty() && !keyWrapAlgorithms.contains(keyWrapAlgorithm)) {
            LOG.debug("The Key transport method does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkSymmetricEncryptionAlgorithm(String symmetricAlgorithm) throws WSSecurityException {
        Set<String> encryptionMethods = this.algorithmSuite.getEncryptionMethods();
        if (!encryptionMethods.isEmpty() && !encryptionMethods.contains(symmetricAlgorithm)) {
            LOG.debug("The encryption algorithm does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkAsymmetricKeyLength(X509Certificate[] x509Certificates) throws WSSecurityException {
        if (x509Certificates == null) {
            return;
        }
        for (X509Certificate cert : x509Certificates) {
            this.checkAsymmetricKeyLength(cert.getPublicKey());
        }
    }

    public void checkAsymmetricKeyLength(X509Certificate x509Certificate) throws WSSecurityException {
        if (x509Certificate == null) {
            return;
        }
        this.checkAsymmetricKeyLength(x509Certificate.getPublicKey());
    }

    public void checkAsymmetricKeyLength(PublicKey publicKey) throws WSSecurityException {
        if (publicKey == null) {
            return;
        }
        if (publicKey instanceof RSAPublicKey) {
            int modulus = ((RSAPublicKey)publicKey).getModulus().bitLength();
            if (modulus < this.algorithmSuite.getMinimumAsymmetricKeyLength() || modulus > this.algorithmSuite.getMaximumAsymmetricKeyLength()) {
                LOG.debug("The asymmetric key length does not match the requirement");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        } else if (publicKey instanceof DSAPublicKey) {
            int length = ((DSAPublicKey)publicKey).getParams().getP().bitLength();
            if (length < this.algorithmSuite.getMinimumAsymmetricKeyLength() || length > this.algorithmSuite.getMaximumAsymmetricKeyLength()) {
                LOG.debug("The asymmetric key length does not match the requirement");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        } else if (publicKey instanceof ECPublicKey) {
            ECPublicKey ecpriv = (ECPublicKey)publicKey;
            ECParameterSpec spec = ecpriv.getParams();
            int length = spec.getOrder().bitLength();
            if (length < this.algorithmSuite.getMinimumEllipticCurveKeyLength() || length > this.algorithmSuite.getMaximumEllipticCurveKeyLength()) {
                LOG.warn("The elliptic curve key length does not match the requirement");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        } else {
            LOG.debug("An unknown public key was provided");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkSymmetricKeyLength(int secretKeyLength) throws WSSecurityException {
        if (secretKeyLength < this.algorithmSuite.getMinimumSymmetricKeyLength() / 8 || secretKeyLength > this.algorithmSuite.getMaximumSymmetricKeyLength() / 8) {
            LOG.debug("The symmetric key length does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public void checkSignatureDerivedKeyLength(int derivedKeyLength) throws WSSecurityException {
        int requiredKeyLength = this.algorithmSuite.getSignatureDerivedKeyLength();
        if (requiredKeyLength > 0 && derivedKeyLength / 8 != requiredKeyLength) {
            LOG.debug("The signature derived key length of " + derivedKeyLength + " does not matchthe requirement of " + requiredKeyLength);
        }
    }

    public void checkEncryptionDerivedKeyLength(int derivedKeyLength) throws WSSecurityException {
        int requiredKeyLength = this.algorithmSuite.getEncryptionDerivedKeyLength();
        if (requiredKeyLength > 0 && derivedKeyLength / 8 != requiredKeyLength) {
            LOG.debug("The encryption derived key length of " + derivedKeyLength + " does not matchthe requirement of " + requiredKeyLength);
        }
    }

    public void checkDerivedKeyAlgorithm(String algorithm) throws WSSecurityException {
        Set<String> derivedKeyAlgorithms = this.algorithmSuite.getDerivedKeyAlgorithms();
        if (!derivedKeyAlgorithms.isEmpty() && !derivedKeyAlgorithms.contains(algorithm)) {
            LOG.debug("The Derived Key Algorithm does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }
}

