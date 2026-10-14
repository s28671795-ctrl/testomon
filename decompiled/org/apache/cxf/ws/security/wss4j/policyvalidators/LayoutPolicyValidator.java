/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.Layout;
import org.w3c.dom.Element;

public class LayoutPolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.LAYOUT.equals(assertionInfo.getAssertion().getName()) || SP11Constants.LAYOUT.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            Layout layout = (Layout)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(layout, parameters.getAssertionInfoMap());
            if (this.validatePolicy(layout, parameters.getResults().getResults(), parameters.getSignedResults())) continue;
            String error = "Layout does not match the requirements";
            ai.setNotAsserted(error);
        }
    }

    private void assertToken(Layout token, AssertionInfoMap aim) {
        String namespace = token.getName().getNamespaceURI();
        Layout.LayoutType layoutType = token.getLayoutType();
        if (layoutType != null) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, layoutType.name()));
        }
    }

    private boolean validatePolicy(Layout layout, List<WSSecurityEngineResult> results, List<WSSecurityEngineResult> signedResults) {
        boolean strict;
        boolean timestampFirst = layout.getLayoutType() == Layout.LayoutType.LaxTsFirst;
        boolean timestampLast = layout.getLayoutType() == Layout.LayoutType.LaxTsLast;
        boolean bl = strict = layout.getLayoutType() == Layout.LayoutType.Strict;
        if (timestampFirst) {
            if (results.isEmpty()) {
                return false;
            }
            Integer firstAction = (Integer)results.get(results.size() - 1).get("action");
            if (firstAction != 32) {
                return false;
            }
        } else if (timestampLast) {
            if (results.isEmpty()) {
                return false;
            }
            Integer lastAction = (Integer)results.get(0).get("action");
            if (lastAction != 32) {
                return false;
            }
        } else if (!(!strict || this.validateStrictSignaturePlacement(results, signedResults) && this.validateStrictSignatureTokenPlacement(results) && this.checkSignatureIsSignedPlacement(results, signedResults))) {
            return false;
        }
        return true;
    }

    private boolean validateStrictSignaturePlacement(List<WSSecurityEngineResult> results, List<WSSecurityEngineResult> signedResults) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> sl = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            Integer actInt = (Integer)signedResult.get("action");
            if (sl == null || 16 == actInt) continue;
            block1: for (WSDataRef r : sl) {
                String[] nodes;
                String xpath = r.getXpath();
                if (xpath == null || (nodes = xpath.split("/")).length != 5) continue;
                Element protectedElement = r.getProtectedElement();
                boolean tokenFound = false;
                for (WSSecurityEngineResult result : results) {
                    Element resultElement = (Element)result.get("token-element");
                    if (resultElement == protectedElement) {
                        tokenFound = true;
                    }
                    if (tokenFound && result == signedResult) {
                        return false;
                    }
                    if (resultElement == null || result != signedResult) continue;
                    continue block1;
                }
            }
        }
        return true;
    }

    private boolean validateStrictSignatureTokenPlacement(List<WSSecurityEngineResult> results) {
        for (int i = 0; i < results.size(); ++i) {
            int correspondingIndex;
            WSSecurityEngineResult result = results.get(i);
            Integer actInt = (Integer)result.get("action");
            if (actInt != 2 || (correspondingIndex = this.findCorrespondingTokenIndex(result, results)) <= 0 || correspondingIndex >= i) continue;
            return false;
        }
        return true;
    }

    private boolean checkSignatureIsSignedPlacement(List<WSSecurityEngineResult> results, List<WSSecurityEngineResult> signedResults) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> sl = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            if (sl == null || sl.isEmpty()) continue;
            for (WSDataRef dataRef : sl) {
                Element protectedElement;
                QName signedQName = dataRef.getName();
                if (!WSConstants.SIGNATURE.equals(signedQName) || this.isEndorsingSignatureInCorrectPlace(results, signedResult, protectedElement = dataRef.getProtectedElement())) continue;
                return false;
            }
        }
        return true;
    }

    private boolean isEndorsingSignatureInCorrectPlace(List<WSSecurityEngineResult> results, WSSecurityEngineResult signedResult, Element protectedElement) {
        boolean endorsingSigFound = false;
        for (WSSecurityEngineResult result : results) {
            Integer action = (Integer)result.get("action");
            if (2 != action && 16 != action && 64 != action) continue;
            if (result == signedResult) {
                endorsingSigFound = true;
            }
            Element resultElement = (Element)result.get("token-element");
            if (endorsingSigFound && resultElement == protectedElement) {
                return true;
            }
            if (resultElement != protectedElement) continue;
            return false;
        }
        return true;
    }

    private int findCorrespondingTokenIndex(WSSecurityEngineResult signatureResult, List<WSSecurityEngineResult> results) {
        X509Certificate cert = (X509Certificate)signatureResult.get("x509-certificate");
        PublicKey publicKey = (PublicKey)signatureResult.get("public-key");
        for (int i = 0; i < results.size(); ++i) {
            WSSecurityEngineResult token = results.get(i);
            Integer actInt = (Integer)token.get("action");
            if (actInt == 2) continue;
            BinarySecurity binarySecurity = (BinarySecurity)token.get("binary-security-token");
            PublicKey foundPublicKey = (PublicKey)token.get("public-key");
            if (binarySecurity instanceof X509Security || binarySecurity instanceof PKIPathSecurity) {
                X509Certificate foundCert = (X509Certificate)token.get("x509-certificate");
                if (!foundCert.equals(cert)) continue;
                return i;
            }
            if (actInt == 16 || actInt == 8) {
                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)token.get("saml-assertion");
                SAMLKeyInfo samlKeyInfo = assertionWrapper.getSubjectKeyInfo();
                if (samlKeyInfo == null) continue;
                X509Certificate[] subjectCerts = samlKeyInfo.getCerts();
                PublicKey subjectPublicKey = samlKeyInfo.getPublicKey();
                if ((cert == null || subjectCerts == null || !cert.equals(subjectCerts[0])) && (subjectPublicKey == null || !subjectPublicKey.equals(publicKey))) continue;
                return i;
            }
            if (publicKey == null || !publicKey.equals(foundPublicKey)) continue;
            return i;
        }
        return -1;
    }
}

