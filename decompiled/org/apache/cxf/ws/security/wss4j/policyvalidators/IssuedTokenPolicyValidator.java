/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSamlPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ClaimsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.DefaultClaimsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.IssuedToken;
import org.opensaml.saml.common.SAMLVersion;
import org.w3c.dom.Element;

public class IssuedTokenPolicyValidator
extends AbstractSamlPolicyValidator {
    private ClaimsPolicyValidator claimsValidator = new DefaultClaimsPolicyValidator();

    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.ISSUED_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.ISSUED_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        List<WSSecurityEngineResult> bstResults;
        List<WSSecurityEngineResult> samlResults = parameters.getSamlResults();
        if (samlResults != null) {
            for (WSSecurityEngineResult samlResult : samlResults) {
                SamlAssertionWrapper samlAssertion = (SamlAssertionWrapper)samlResult.get("saml-assertion");
                if (!this.validateSAMLToken(parameters, samlAssertion, ais)) continue;
                SecurityToken token = this.createSecurityToken(samlAssertion);
                parameters.getMessage().getExchange().put("ws-security.token", token);
                return;
            }
        }
        if ((bstResults = parameters.getResults().getActionResults().get(4096)) != null) {
            for (WSSecurityEngineResult bstResult : bstResults) {
                BinarySecurity binarySecurity = (BinarySecurity)bstResult.get("binary-security-token");
                if (!Boolean.TRUE.equals(bstResult.get("validated-token")) || !this.validateBinarySecurityToken(parameters, binarySecurity, ais)) continue;
                SecurityToken token = this.createSecurityToken(binarySecurity);
                parameters.getMessage().getExchange().put("ws-security.token", token);
                return;
            }
        }
    }

    private boolean validateSAMLToken(PolicyValidatorParameters parameters, SamlAssertionWrapper samlAssertion, Collection<AssertionInfo> ais) {
        boolean asserted = true;
        for (AssertionInfo ai : ais) {
            IssuedToken issuedToken = (IssuedToken)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(issuedToken, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(issuedToken, parameters.getMessage())) continue;
            if (samlAssertion == null) {
                asserted = false;
                ai.setNotAsserted("The received token does not match the token inclusion requirement");
                continue;
            }
            Element template = issuedToken.getRequestSecurityTokenTemplate();
            if (template != null && !this.checkIssuedTokenTemplate(template, samlAssertion)) {
                asserted = false;
                ai.setNotAsserted("Error in validating the IssuedToken policy");
                continue;
            }
            Element claims = issuedToken.getClaims();
            if (claims != null) {
                String dialect = claims.getAttributeNS(null, "Dialect");
                if (this.claimsValidator.getDialect().equals(dialect) && !this.claimsValidator.validatePolicy(claims, samlAssertion)) {
                    asserted = false;
                    ai.setNotAsserted("Error in validating the Claims policy");
                    continue;
                }
            }
            TLSSessionInfo tlsInfo = parameters.getMessage().get(TLSSessionInfo.class);
            Certificate[] tlsCerts = null;
            if (tlsInfo != null) {
                tlsCerts = tlsInfo.getPeerCertificates();
            }
            if (this.checkHolderOfKey(samlAssertion, parameters.getSignedResults(), tlsCerts)) continue;
            asserted = false;
            ai.setNotAsserted("Assertion fails holder-of-key requirements");
        }
        return asserted;
    }

    private boolean validateBinarySecurityToken(PolicyValidatorParameters parameters, BinarySecurity binarySecurity, Collection<AssertionInfo> ais) {
        boolean asserted = true;
        for (AssertionInfo ai : ais) {
            IssuedToken issuedToken = (IssuedToken)ai.getAssertion();
            ai.setAsserted(true);
            asserted = true;
            this.assertToken(issuedToken, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(issuedToken, parameters.getMessage())) continue;
            if (binarySecurity == null) {
                asserted = false;
                ai.setNotAsserted("The received token does not match the token inclusion requirement");
                continue;
            }
            Element template = issuedToken.getRequestSecurityTokenTemplate();
            if (template == null || this.checkIssuedTokenTemplate(template, binarySecurity)) continue;
            asserted = false;
            ai.setNotAsserted("Error in validating the IssuedToken policy");
        }
        return asserted;
    }

    private void assertToken(IssuedToken token, AssertionInfoMap aim) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireExternalReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireExternalReference"));
        }
        if (token.isRequireInternalReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireInternalReference"));
        }
    }

    private boolean checkIssuedTokenTemplate(Element template, SamlAssertionWrapper assertionWrapper) {
        Element child = DOMUtils.getFirstElement(template);
        while (child != null) {
            String content;
            if ("TokenType".equals(child.getLocalName())) {
                content = child.getTextContent();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(content) && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_11) {
                    return false;
                }
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(content) && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_20) {
                    return false;
                }
            } else if ("KeyType".equals(child.getLocalName())) {
                SAMLKeyInfo subjectKeyInfo;
                content = child.getTextContent();
                if (content.endsWith("SymmetricKey") ? (subjectKeyInfo = assertionWrapper.getSubjectKeyInfo()) == null || subjectKeyInfo.getSecret() == null : content.endsWith("PublicKey") && ((subjectKeyInfo = assertionWrapper.getSubjectKeyInfo()) == null || subjectKeyInfo.getPublicKey() == null && subjectKeyInfo.getCerts() == null)) {
                    return false;
                }
            } else if ("Claims".equals(child.getLocalName())) {
                String dialect = child.getAttributeNS(null, "Dialect");
                if (this.claimsValidator.getDialect().equals(dialect) && !this.claimsValidator.validatePolicy(child, assertionWrapper)) {
                    return false;
                }
            }
            child = DOMUtils.getNextElement(child);
        }
        return true;
    }

    private boolean checkIssuedTokenTemplate(Element template, BinarySecurity binarySecurityToken) {
        Element child = DOMUtils.getFirstElement(template);
        while (child != null) {
            String valueType;
            String content;
            if ("TokenType".equals(child.getLocalName()) && !(content = child.getTextContent()).equals(valueType = binarySecurityToken.getValueType())) {
                return false;
            }
            child = DOMUtils.getNextElement(child);
        }
        return true;
    }

    private SecurityToken createSecurityToken(SamlAssertionWrapper assertionWrapper) {
        SecurityToken token = new SecurityToken(assertionWrapper.getId());
        SAMLKeyInfo subjectKeyInfo = assertionWrapper.getSubjectKeyInfo();
        if (subjectKeyInfo != null) {
            token.setSecret(subjectKeyInfo.getSecret());
            X509Certificate[] certs = subjectKeyInfo.getCerts();
            if (certs != null && certs.length > 0) {
                token.setX509Certificate(certs[0], null);
            }
            if (subjectKeyInfo.getPublicKey() != null) {
                token.setKey(subjectKeyInfo.getPublicKey());
            }
        }
        if (assertionWrapper.getSaml1() != null) {
            token.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
        } else if (assertionWrapper.getSaml2() != null) {
            token.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        }
        token.setToken(assertionWrapper.getElement());
        return token;
    }

    private SecurityToken createSecurityToken(BinarySecurity binarySecurityToken) {
        SecurityToken token = new SecurityToken(binarySecurityToken.getID());
        token.setToken(binarySecurityToken.getElement());
        token.setSecret(binarySecurityToken.getToken());
        token.setTokenType(binarySecurityToken.getValueType());
        return token;
    }
}

