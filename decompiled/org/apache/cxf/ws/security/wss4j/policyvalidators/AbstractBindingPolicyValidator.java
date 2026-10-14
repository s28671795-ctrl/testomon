/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.EncryptionToken;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.SignatureToken;
import org.apache.wss4j.policy.model.X509Token;
import org.w3c.dom.Element;

public abstract class AbstractBindingPolicyValidator
implements SecurityPolicyValidator {
    private static final QName SIG_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");

    protected boolean validateTimestamp(boolean includeTimestamp, boolean transportBinding, WSHandlerResult results, List<WSSecurityEngineResult> signedResults, Message message) {
        List<WSSecurityEngineResult> timestampResults = results.getActionResults().get(32);
        if (includeTimestamp && (timestampResults == null || timestampResults.size() != 1)) {
            return false;
        }
        if (!includeTimestamp) {
            return timestampResults == null || timestampResults.isEmpty();
        }
        if (transportBinding) {
            return true;
        }
        if (!signedResults.isEmpty()) {
            Timestamp timestamp = (Timestamp)timestampResults.get(0).get("timestamp");
            for (WSSecurityEngineResult signedResult : signedResults) {
                List<WSDataRef> dataRefs = CastUtils.cast((List)signedResult.get("data-ref-uris"));
                for (WSDataRef dataRef : dataRefs) {
                    if (timestamp.getElement() != dataRef.getProtectedElement()) continue;
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean validateEntireHeaderAndBodySignatures(List<WSSecurityEngineResult> signedResults) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> dataRefs = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            if (dataRefs == null) continue;
            for (WSDataRef dataRef : dataRefs) {
                String xpath = dataRef.getXpath();
                if (xpath == null) continue;
                String[] nodes = xpath.split("/");
                if (nodes.length < 3 || nodes.length > 5) {
                    return false;
                }
                if (!nodes[2].contains("Header") && !nodes[2].contains("Body")) {
                    return false;
                }
                if (nodes.length == 5 && !nodes[3].contains("Security")) {
                    return false;
                }
                if (nodes.length != 4 || !nodes[2].contains("Body")) continue;
                return false;
            }
        }
        return true;
    }

    protected boolean checkProperties(AbstractSymmetricAsymmetricBinding binding, AssertionInfo ai, AssertionInfoMap aim, WSHandlerResult results, List<WSSecurityEngineResult> signedResults, Message message) {
        if (!this.validateTimestamp(binding.isIncludeTimestamp(), false, results, signedResults, message)) {
            String error = "Received Timestamp does not match the requirements";
            ai.setNotAsserted(error);
            return false;
        }
        String namespace = binding.getName().getNamespaceURI();
        PolicyUtils.assertPolicy(aim, new QName(namespace, "IncludeTimestamp"));
        if (binding.isOnlySignEntireHeadersAndBody() && !this.validateEntireHeaderAndBodySignatures(signedResults)) {
            String error = "OnlySignEntireHeadersAndBody does not match the requirements";
            ai.setNotAsserted(error);
            return false;
        }
        PolicyUtils.assertPolicy(aim, new QName(namespace, "OnlySignEntireHeadersAndBody"));
        if (binding.isEncryptSignature() && !this.isSignatureEncrypted(results.getResults())) {
            ai.setNotAsserted("The signature is not protected");
            return false;
        }
        PolicyUtils.assertPolicy(aim, new QName(namespace, "EncryptSignature"));
        PolicyUtils.assertPolicy(aim, new QName(namespace, "ProtectTokens"));
        return true;
    }

    protected boolean checkProtectionOrder(AbstractSymmetricAsymmetricBinding binding, AssertionInfoMap aim, AssertionInfo ai, List<WSSecurityEngineResult> results) {
        AbstractSymmetricAsymmetricBinding.ProtectionOrder protectionOrder = binding.getProtectionOrder();
        String namespace = binding.getName().getNamespaceURI();
        if (protectionOrder == AbstractSymmetricAsymmetricBinding.ProtectionOrder.EncryptBeforeSigning) {
            if (!binding.isProtectTokens() && this.isSignedBeforeEncrypted(results)) {
                ai.setNotAsserted("Not encrypted before signed");
                return false;
            }
            PolicyUtils.assertPolicy(aim, new QName(namespace, "EncryptBeforeSigning"));
        } else if (protectionOrder == AbstractSymmetricAsymmetricBinding.ProtectionOrder.SignBeforeEncrypting) {
            if (this.isEncryptedBeforeSigned(results)) {
                ai.setNotAsserted("Not signed before encrypted");
                return false;
            }
            PolicyUtils.assertPolicy(aim, new QName(namespace, "SignBeforeEncrypting"));
        }
        return true;
    }

    private boolean isSignedBeforeEncrypted(List<WSSecurityEngineResult> results) {
        boolean signed = false;
        for (WSSecurityEngineResult result : results) {
            Integer actInt = (Integer)result.get("action");
            List el = CastUtils.cast((List)result.get("data-ref-uris"));
            if (!(actInt != 2 || el == null || el.size() == 1 && ((WSDataRef)el.get(0)).getName().equals(SIG_QNAME))) {
                signed = true;
            }
            if (actInt != 4 || el == null) continue;
            return signed;
        }
        return false;
    }

    private boolean isEncryptedBeforeSigned(List<WSSecurityEngineResult> results) {
        boolean encrypted = false;
        for (WSSecurityEngineResult result : results) {
            Integer actInt = (Integer)result.get("action");
            List el = CastUtils.cast((List)result.get("data-ref-uris"));
            if (actInt == 4 && el != null) {
                encrypted = true;
            }
            if (actInt != 2 || el == null || el.size() == 1 && ((WSDataRef)el.get(0)).getName().equals(SIG_QNAME)) continue;
            return encrypted;
        }
        return false;
    }

    protected boolean checkDerivedKeys(AbstractTokenWrapper tokenWrapper, boolean hasDerivedKeys, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        boolean isDerivedKeys;
        AbstractToken token = tokenWrapper.getToken();
        boolean bl = isDerivedKeys = token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys;
        if (!(token instanceof X509Token) || !isDerivedKeys) {
            return true;
        }
        if (tokenWrapper instanceof EncryptionToken && !hasDerivedKeys && !encryptedResults.isEmpty()) {
            return false;
        }
        if (tokenWrapper instanceof SignatureToken && !hasDerivedKeys && !signedResults.isEmpty()) {
            return false;
        }
        return !(tokenWrapper instanceof ProtectionToken) || hasDerivedKeys || signedResults.isEmpty() || encryptedResults.isEmpty();
    }

    protected boolean isTokenProtected(List<WSSecurityEngineResult> results, List<WSSecurityEngineResult> signedResults) {
        for (WSSecurityEngineResult result : signedResults) {
            WSSecurityEngineResult tokenResult = this.findCorrespondingToken(result, results);
            if (tokenResult == null) {
                return false;
            }
            List<WSDataRef> sl = CastUtils.cast((List)result.get("data-ref-uris"));
            boolean found = false;
            if (sl != null) {
                for (WSDataRef dataRef : sl) {
                    Element referenceElement = dataRef.getProtectedElement();
                    if (referenceElement == null || !referenceElement.equals(tokenResult.get("token-element"))) continue;
                    found = true;
                }
            }
            if (found) continue;
            return false;
        }
        return true;
    }

    private WSSecurityEngineResult findCorrespondingToken(WSSecurityEngineResult signatureResult, List<WSSecurityEngineResult> results) {
        X509Certificate cert = (X509Certificate)signatureResult.get("x509-certificate");
        PublicKey publicKey = (PublicKey)signatureResult.get("public-key");
        for (WSSecurityEngineResult token : results) {
            Integer actInt = (Integer)token.get("action");
            if (actInt == 2) continue;
            BinarySecurity binarySecurity = (BinarySecurity)token.get("binary-security-token");
            PublicKey foundPublicKey = (PublicKey)token.get("public-key");
            if (binarySecurity instanceof X509Security || binarySecurity instanceof PKIPathSecurity) {
                X509Certificate foundCert = (X509Certificate)token.get("x509-certificate");
                if (!foundCert.equals(cert)) continue;
                return token;
            }
            if (actInt == 16 || actInt == 8) {
                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)token.get("saml-assertion");
                SAMLKeyInfo samlKeyInfo = assertionWrapper.getSubjectKeyInfo();
                if (samlKeyInfo == null) continue;
                X509Certificate[] subjectCerts = samlKeyInfo.getCerts();
                PublicKey subjectPublicKey = samlKeyInfo.getPublicKey();
                if ((cert == null || subjectCerts == null || !cert.equals(subjectCerts[0])) && (subjectPublicKey == null || !subjectPublicKey.equals(publicKey))) continue;
                return token;
            }
            if (publicKey == null || !publicKey.equals(foundPublicKey)) continue;
            return token;
        }
        return null;
    }

    protected boolean isSignatureEncrypted(List<WSSecurityEngineResult> results) {
        boolean foundPrimarySignature = false;
        for (int i = results.size() - 1; i >= 0; --i) {
            Element sigElement;
            WSSecurityEngineResult result = results.get(i);
            Integer actInt = (Integer)result.get("action");
            if (actInt == 2 && !foundPrimarySignature) {
                foundPrimarySignature = true;
                sigElement = (Element)result.get("token-element");
                if (sigElement != null && this.isElementEncrypted(sigElement, results)) continue;
                return false;
            }
            if (actInt != 128 || (sigElement = (Element)result.get("token-element")) != null && this.isElementEncrypted(sigElement, results)) continue;
            return false;
        }
        return true;
    }

    private boolean isElementEncrypted(Element element, List<WSSecurityEngineResult> results) {
        for (WSSecurityEngineResult wser : results) {
            List<WSDataRef> el;
            Integer actInt = (Integer)wser.get("action");
            if (actInt != 4 || (el = CastUtils.cast((List)wser.get("data-ref-uris"))) == null) continue;
            for (WSDataRef r : el) {
                Element protectedElement = r.getProtectedElement();
                if (!element.equals(protectedElement)) continue;
                return true;
            }
        }
        return false;
    }

    protected void assertDerivedKeys(AbstractToken token, AssertionInfoMap aim) {
        AbstractToken.DerivedKeys derivedKeys = token.getDerivedKeys();
        if (derivedKeys != null) {
            PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), derivedKeys.name()));
        }
    }
}

