/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AlgorithmSuite;

public class AlgorithmSuitePolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.ALGORITHM_SUITE.equals(assertionInfo.getAssertion().getName()) || SP11Constants.ALGORITHM_SUITE.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            AlgorithmSuite algorithmSuite = (AlgorithmSuite)ai.getAssertion();
            ai.setAsserted(true);
            boolean valid = this.validatePolicy(ai, algorithmSuite, parameters.getResults().getResults());
            if (valid) {
                String namespace = algorithmSuite.getAlgorithmSuiteType().getNamespace();
                String name = algorithmSuite.getAlgorithmSuiteType().getName();
                Collection algSuiteAis = (Collection)parameters.getAssertionInfoMap().get(new QName(namespace, name));
                if (algSuiteAis != null) {
                    for (AssertionInfo algSuiteAi : algSuiteAis) {
                        algSuiteAi.setAsserted(true);
                    }
                }
                PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), new QName(algorithmSuite.getName().getNamespaceURI(), algorithmSuite.getC14n().name()));
                continue;
            }
            if (!ai.isAsserted()) continue;
            ai.setNotAsserted("Error in validating AlgorithmSuite policy");
        }
    }

    private boolean validatePolicy(AssertionInfo ai, AlgorithmSuite algorithmPolicy, List<WSSecurityEngineResult> results) {
        for (WSSecurityEngineResult result : results) {
            Integer action = (Integer)result.get("action");
            if (2 == action && !this.checkSignatureAlgorithms(result, algorithmPolicy, ai)) {
                return false;
            }
            if (4 != action || this.checkEncryptionAlgorithms(result, algorithmPolicy, ai)) continue;
            return false;
        }
        return true;
    }

    private boolean checkSignatureAlgorithms(WSSecurityEngineResult result, AlgorithmSuite algorithmPolicy, AssertionInfo ai) {
        String signatureMethod = (String)result.get("signature-method");
        if (!algorithmPolicy.getAsymmetricSignature().equals(signatureMethod) && !algorithmPolicy.getSymmetricSignature().equals(signatureMethod)) {
            ai.setNotAsserted("The signature method does not match the requirement");
            return false;
        }
        String c14nMethod = (String)result.get("canonicalization-method");
        if (!algorithmPolicy.getC14n().getValue().equals(c14nMethod)) {
            ai.setNotAsserted("The c14n method does not match the requirement");
            return false;
        }
        List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
        if (!this.checkDataRefs(dataRefs, algorithmPolicy, ai)) {
            return false;
        }
        return this.checkKeyLengths(result, algorithmPolicy, ai, true);
    }

    private boolean checkDataRefs(List<WSDataRef> dataRefs, AlgorithmSuite algorithmPolicy, AssertionInfo ai) {
        AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType = algorithmPolicy.getAlgorithmSuiteType();
        for (WSDataRef dataRef : dataRefs) {
            String digestMethod = dataRef.getDigestAlgorithm();
            if (!algorithmSuiteType.getDigest().equals(digestMethod)) {
                ai.setNotAsserted("The digest method does not match the requirement");
                return false;
            }
            List<String> transformAlgorithms = dataRef.getTransformAlgorithms();
            if (transformAlgorithms == null || transformAlgorithms.size() > 2) {
                ai.setNotAsserted("The transform algorithms do not match the requirement");
                return false;
            }
            for (String transformAlgorithm : transformAlgorithms) {
                if (algorithmPolicy.getC14n().getValue().equals(transformAlgorithm) || "http://www.w3.org/2001/10/xml-exc-c14n#".equals(transformAlgorithm) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(transformAlgorithm) || "http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(transformAlgorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(transformAlgorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(transformAlgorithm)) continue;
                ai.setNotAsserted("The transform algorithms do not match the requirement");
                return false;
            }
        }
        return true;
    }

    private boolean checkEncryptionAlgorithms(WSSecurityEngineResult result, AlgorithmSuite algorithmPolicy, AssertionInfo ai) {
        AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType = algorithmPolicy.getAlgorithmSuiteType();
        String transportMethod = (String)result.get("encrypted-key-transport-method");
        if (transportMethod != null && !algorithmSuiteType.getSymmetricKeyWrap().equals(transportMethod) && !algorithmSuiteType.getAsymmetricKeyWrap().equals(transportMethod)) {
            ai.setNotAsserted("The Key transport method does not match the requirement");
            return false;
        }
        List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
        if (dataRefs != null) {
            for (WSDataRef dataRef : dataRefs) {
                String encryptionAlgorithm = dataRef.getAlgorithm();
                if (algorithmSuiteType.getEncryption().equals(encryptionAlgorithm)) continue;
                ai.setNotAsserted("The encryption algorithm does not match the requirement");
                return false;
            }
        }
        return this.checkKeyLengths(result, algorithmPolicy, ai, false);
    }

    private boolean checkKeyLengths(WSSecurityEngineResult result, AlgorithmSuite algorithmPolicy, AssertionInfo ai, boolean signature) {
        PublicKey publicKey = (PublicKey)result.get("public-key");
        if (publicKey != null && !this.checkPublicKeyLength(publicKey, algorithmPolicy, ai)) {
            return false;
        }
        X509Certificate x509Cert = (X509Certificate)result.get("x509-certificate");
        if (x509Cert != null && !this.checkPublicKeyLength(x509Cert.getPublicKey(), algorithmPolicy, ai)) {
            return false;
        }
        AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType = algorithmPolicy.getAlgorithmSuiteType();
        byte[] secret = (byte[])result.get("secret");
        if (signature) {
            Principal principal = (Principal)result.get("principal");
            if (principal instanceof WSDerivedKeyTokenPrincipal) {
                int requiredLength = algorithmSuiteType.getSignatureDerivedKeyLength();
                if (secret == null || secret.length != requiredLength / 8) {
                    ai.setNotAsserted("The signature derived key length does not match the requirement");
                    return false;
                }
            } else if (secret != null && (secret.length < algorithmSuiteType.getMinimumSymmetricKeyLength() / 8 || secret.length > algorithmSuiteType.getMaximumSymmetricKeyLength() / 8)) {
                ai.setNotAsserted("The symmetric key length does not match the requirement");
                return false;
            }
        } else if (secret != null && (secret.length < algorithmSuiteType.getMinimumSymmetricKeyLength() / 8 || secret.length > algorithmSuiteType.getMaximumSymmetricKeyLength() / 8)) {
            ai.setNotAsserted("The symmetric key length does not match the requirement");
            return false;
        }
        return true;
    }

    private boolean checkPublicKeyLength(PublicKey publicKey, AlgorithmSuite algorithmPolicy, AssertionInfo ai) {
        AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType = algorithmPolicy.getAlgorithmSuiteType();
        if (publicKey instanceof RSAPublicKey) {
            int modulus = ((RSAPublicKey)publicKey).getModulus().bitLength();
            if (modulus < algorithmSuiteType.getMinimumAsymmetricKeyLength() || modulus > algorithmSuiteType.getMaximumAsymmetricKeyLength()) {
                ai.setNotAsserted("The asymmetric key length does not match the requirement");
                return false;
            }
        } else if (publicKey instanceof DSAPublicKey) {
            int length = ((DSAPublicKey)publicKey).getParams().getP().bitLength();
            if (length < algorithmSuiteType.getMinimumAsymmetricKeyLength() || length > algorithmSuiteType.getMaximumAsymmetricKeyLength()) {
                ai.setNotAsserted("The asymmetric key length does not match the requirement");
                return false;
            }
        } else {
            ai.setNotAsserted("An unknown public key was provided");
            return false;
        }
        return true;
    }
}

