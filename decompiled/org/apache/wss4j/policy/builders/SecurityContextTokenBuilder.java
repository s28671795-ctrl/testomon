/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SecurityContextTokenBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String includeTokenValue = SPUtils.getAttribute(element, spVersion.getSPConstants().getIncludeToken());
        Element issuer = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getIssuer());
        if (spVersion == SPConstants.SPVersion.SP11 && issuer != null) {
            throw new IllegalArgumentException("Invalid Policy");
        }
        String issuerName = SPUtils.getFirstChildElementText(element, spVersion.getSPConstants().getIssuerName());
        if (spVersion == SPConstants.SPVersion.SP11 && issuerName != null) {
            throw new IllegalArgumentException("Invalid Policy");
        }
        Element claims = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getClaims());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = null;
        nestedPolicy = nestedPolicyElement == null ? new Policy() : factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        SecurityContextToken securityContextToken = new SecurityContextToken(spVersion, spVersion.getSPConstants().getInclusionFromAttributeValue(includeTokenValue), issuer, issuerName, claims, nestedPolicy);
        securityContextToken.setOptional(SPUtils.isOptional(element));
        securityContextToken.setIgnorable(SPUtils.isIgnorable(element));
        return securityContextToken;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.SECURITY_CONTEXT_TOKEN, SP11Constants.SECURITY_CONTEXT_TOKEN};
    }
}

