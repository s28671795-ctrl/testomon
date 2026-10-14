/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.net.URI;
import java.util.List;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ClaimsPolicyValidator;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.w3c.dom.Element;

public class DefaultClaimsPolicyValidator
implements ClaimsPolicyValidator {
    private static final String DEFAULT_CLAIMS_NAMESPACE = "http://schemas.xmlsoap.org/ws/2005/05/identity";

    @Override
    public boolean validatePolicy(Element claimsPolicy, SamlAssertionWrapper assertion) {
        if (claimsPolicy == null) {
            return false;
        }
        String dialect = claimsPolicy.getAttributeNS(null, "Dialect");
        if (!DEFAULT_CLAIMS_NAMESPACE.equals(dialect)) {
            return false;
        }
        Element claimType = DOMUtils.getFirstElement(claimsPolicy);
        while (claimType != null) {
            if ("ClaimType".equals(claimType.getLocalName())) {
                String claimTypeUri = claimType.getAttributeNS(null, "Uri");
                String claimTypeOptional = claimType.getAttributeNS(null, "Optional");
                if (!(!"".equals(claimTypeOptional) && Boolean.parseBoolean(claimTypeOptional) || this.findClaimInAssertion(assertion, URI.create(claimTypeUri)))) {
                    return false;
                }
            }
            claimType = DOMUtils.getNextElement(claimType);
        }
        return true;
    }

    @Override
    public String getDialect() {
        return DEFAULT_CLAIMS_NAMESPACE;
    }

    private boolean findClaimInAssertion(SamlAssertionWrapper assertion, URI claimURI) {
        if (assertion.getSaml1() != null) {
            return this.findClaimInAssertion(assertion.getSaml1(), claimURI);
        }
        if (assertion.getSaml2() != null) {
            return this.findClaimInAssertion(assertion.getSaml2(), claimURI);
        }
        return false;
    }

    private boolean findClaimInAssertion(org.opensaml.saml.saml2.core.Assertion assertion, URI claimURI) {
        List<org.opensaml.saml.saml2.core.AttributeStatement> attributeStatements = assertion.getAttributeStatements();
        if (attributeStatements == null || attributeStatements.isEmpty()) {
            return false;
        }
        for (org.opensaml.saml.saml2.core.AttributeStatement statement : attributeStatements) {
            List<org.opensaml.saml.saml2.core.Attribute> attributes = statement.getAttributes();
            for (org.opensaml.saml.saml2.core.Attribute attribute : attributes) {
                if (!attribute.getName().equals(claimURI.toString()) || attribute.getAttributeValues() == null || attribute.getAttributeValues().isEmpty()) continue;
                return true;
            }
        }
        return false;
    }

    private boolean findClaimInAssertion(Assertion assertion, URI claimURI) {
        List<AttributeStatement> attributeStatements = assertion.getAttributeStatements();
        if (attributeStatements == null || attributeStatements.isEmpty()) {
            return false;
        }
        for (AttributeStatement statement : attributeStatements) {
            List<Attribute> attributes = statement.getAttributes();
            for (Attribute attribute : attributes) {
                URI attributeNamespace = URI.create(attribute.getAttributeNamespace());
                String desiredRole = attributeNamespace.relativize(claimURI).toString();
                if (!attribute.getAttributeName().equals(desiredRole) || attribute.getAttributeValues() == null || attribute.getAttributeValues().isEmpty()) continue;
                return true;
            }
        }
        return false;
    }
}

