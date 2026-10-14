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
import org.apache.wss4j.policy.model.X509Token;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class X509TokenBuilder
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
        X509Token x509Token = new X509Token(spVersion, spVersion.getSPConstants().getInclusionFromAttributeValue(includeTokenValue), issuer, issuerName, claims, nestedPolicy);
        x509Token.setOptional(SPUtils.isOptional(element));
        x509Token.setIgnorable(SPUtils.isIgnorable(element));
        return x509Token;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.X509_TOKEN, SP11Constants.X509_TOKEN};
    }
}

