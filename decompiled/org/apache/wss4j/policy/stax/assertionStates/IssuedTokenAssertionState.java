/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.net.URI;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.IssuedTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.KerberosTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.SamlTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class IssuedTokenAssertionState
extends TokenAssertionState {
    private static final String DEFAULT_CLAIMS_NAMESPACE = "http://schemas.xmlsoap.org/ws/2005/05/identity";

    public IssuedTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.KERBEROS_TOKEN, WSSecurityEventConstants.REL_TOKEN, WSSecurityEventConstants.SAML_TOKEN, WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof IssuedTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a IssuedTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        IssuedToken issuedToken = (IssuedToken)abstractToken;
        IssuedTokenSecurityEvent issuedTokenSecurityEvent = (IssuedTokenSecurityEvent)tokenSecurityEvent;
        try {
            Element claims;
            String errorMsg;
            if (issuedToken.getIssuerName() != null && !issuedToken.getIssuerName().equals(issuedTokenSecurityEvent.getIssuerName())) {
                this.setErrorMessage("IssuerName in Policy (" + issuedToken.getIssuerName() + ") didn't match with the one in the IssuedToken (" + issuedTokenSecurityEvent.getIssuerName() + ")");
                this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                return false;
            }
            if (issuedToken.getRequestSecurityTokenTemplate() != null) {
                if (issuedTokenSecurityEvent instanceof SamlTokenSecurityEvent) {
                    SamlTokenSecurityEvent samlTokenSecurityEvent = (SamlTokenSecurityEvent)issuedTokenSecurityEvent;
                    errorMsg = this.checkIssuedTokenTemplate(issuedToken.getRequestSecurityTokenTemplate(), samlTokenSecurityEvent);
                    if (errorMsg != null) {
                        this.setErrorMessage(errorMsg);
                        this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                        return false;
                    }
                } else if (issuedTokenSecurityEvent instanceof KerberosTokenSecurityEvent) {
                    KerberosTokenSecurityEvent kerberosTokenSecurityEvent = (KerberosTokenSecurityEvent)issuedTokenSecurityEvent;
                    errorMsg = this.checkIssuedTokenTemplate(issuedToken.getRequestSecurityTokenTemplate(), kerberosTokenSecurityEvent);
                    if (errorMsg != null) {
                        this.setErrorMessage(errorMsg);
                        this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                        return false;
                    }
                }
            }
            if ((claims = issuedToken.getClaims()) != null && issuedTokenSecurityEvent instanceof SamlTokenSecurityEvent && (errorMsg = this.validateClaims(claims, (SamlTokenSecurityEvent)issuedTokenSecurityEvent)) != null) {
                this.setErrorMessage(errorMsg);
                this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
                return false;
            }
        }
        catch (XMLSecurityException e) {
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            throw new WSSPolicyException(e.getMessage(), e);
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }

    protected String checkIssuedTokenTemplate(Element template, SamlTokenSecurityEvent samlTokenSecurityEvent) throws XMLSecurityException {
        Node child = template.getFirstChild();
        while (child != null) {
            String errorMsg;
            String content;
            if (child.getNodeType() != 1) {
                child = child.getNextSibling();
                continue;
            }
            if ("TokenType".equals(child.getLocalName())) {
                content = child.getTextContent();
                SAMLVersion samlVersion = samlTokenSecurityEvent.getSamlAssertionWrapper().getSamlVersion();
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(content) && samlVersion != SAMLVersion.VERSION_11) {
                    return "Policy enforces SAML V1.1 token but got " + samlVersion.toString();
                }
                if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(content) && samlVersion != SAMLVersion.VERSION_20) {
                    return "Policy enforces SAML V2.0 token but got " + samlVersion.toString();
                }
            } else if ("KeyType".equals(child.getLocalName())) {
                content = child.getTextContent();
                if (content.endsWith("SymmetricKey")) {
                    Map<String, Key> subjectKeys = ((SamlSecurityToken)samlTokenSecurityEvent.getSecurityToken()).getSecretKey();
                    if (subjectKeys.isEmpty()) {
                        return "Policy enforces SAML token with a symmetric key";
                    }
                } else if (content.endsWith("PublicKey")) {
                    PublicKey publicKey = ((SamlSecurityToken)samlTokenSecurityEvent.getSecurityToken()).getPublicKey();
                    X509Certificate[] x509Certificate = ((SamlSecurityToken)samlTokenSecurityEvent.getSecurityToken()).getX509Certificates();
                    if (publicKey == null && x509Certificate == null) {
                        return "Policy enforces SAML token with an asymmetric key";
                    }
                }
            } else if ("Claims".equals(child.getLocalName()) && (errorMsg = this.validateClaims((Element)child, samlTokenSecurityEvent)) != null) {
                return errorMsg;
            }
            child = child.getNextSibling();
        }
        return null;
    }

    private String checkIssuedTokenTemplate(Element template, KerberosTokenSecurityEvent kerberosTokenSecurityEvent) {
        Node child = template.getFirstChild();
        while (child != null) {
            String valueType;
            String content;
            if (child.getNodeType() != 1) {
                child = child.getNextSibling();
                continue;
            }
            if ("TokenType".equals(child.getLocalName()) && !(content = child.getTextContent()).equals(valueType = kerberosTokenSecurityEvent.getKerberosTokenValueType())) {
                return "Policy enforces Kerberos token of type " + content + " but got " + valueType;
            }
            child = child.getNextSibling();
        }
        return null;
    }

    protected String validateClaims(Element claimsPolicy, SamlTokenSecurityEvent samlTokenSecurityEvent) throws WSSecurityException {
        String dialect = claimsPolicy.getAttributeNS(null, "Dialect");
        if (!DEFAULT_CLAIMS_NAMESPACE.equals(dialect)) {
            return null;
        }
        Node child = claimsPolicy.getFirstChild();
        while (child != null) {
            if (child.getNodeType() != 1) {
                child = child.getNextSibling();
                continue;
            }
            if ("ClaimType".equals(child.getLocalName())) {
                String errorMsg;
                Element claimType = (Element)child;
                String claimTypeUri = claimType.getAttributeNS(null, "Uri");
                String claimTypeOptional = claimType.getAttributeNS(null, "Optional");
                if (("".equals(claimTypeOptional) || !Boolean.parseBoolean(claimTypeOptional)) && (errorMsg = this.findClaimInAssertion(samlTokenSecurityEvent.getSamlAssertionWrapper(), URI.create(claimTypeUri))) != null) {
                    return errorMsg;
                }
            }
            child = child.getNextSibling();
        }
        return null;
    }

    protected String findClaimInAssertion(SamlAssertionWrapper samlAssertionWrapper, URI claimURI) {
        if (samlAssertionWrapper.getSaml1() != null) {
            return this.findClaimInAssertion(samlAssertionWrapper.getSaml1(), claimURI);
        }
        if (samlAssertionWrapper.getSaml2() != null) {
            return this.findClaimInAssertion(samlAssertionWrapper.getSaml2(), claimURI);
        }
        return "Unsupported SAML version";
    }

    protected String findClaimInAssertion(org.opensaml.saml.saml2.core.Assertion assertion, URI claimURI) {
        List<org.opensaml.saml.saml2.core.AttributeStatement> attributeStatements = assertion.getAttributeStatements();
        if (attributeStatements == null || attributeStatements.isEmpty()) {
            return "Attribute " + claimURI + " not found in the SAMLAssertion";
        }
        for (org.opensaml.saml.saml2.core.AttributeStatement statement : attributeStatements) {
            List<org.opensaml.saml.saml2.core.Attribute> attributes = statement.getAttributes();
            for (org.opensaml.saml.saml2.core.Attribute attribute : attributes) {
                if (!attribute.getName().equals(claimURI.toString()) || attribute.getAttributeValues() == null || attribute.getAttributeValues().isEmpty()) continue;
                return null;
            }
        }
        return "Attribute " + claimURI + " not found in the SAMLAssertion";
    }

    protected String findClaimInAssertion(Assertion assertion, URI claimURI) {
        List<AttributeStatement> attributeStatements = assertion.getAttributeStatements();
        if (attributeStatements == null || attributeStatements.isEmpty()) {
            return "Attribute " + claimURI + " not found in the SAMLAssertion";
        }
        for (AttributeStatement statement : attributeStatements) {
            List<Attribute> attributes = statement.getAttributes();
            for (Attribute attribute : attributes) {
                URI attributeNamespace = URI.create(attribute.getAttributeNamespace());
                String desiredRole = attributeNamespace.relativize(claimURI).toString();
                if (!attribute.getAttributeName().equals(desiredRole) || attribute.getAttributeValues() == null || attribute.getAttributeValues().isEmpty()) continue;
                return null;
            }
        }
        return "Attribute " + claimURI + " not found in the SAMLAssertion";
    }
}

