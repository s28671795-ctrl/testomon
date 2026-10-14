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
import org.apache.wss4j.policy.model.IssuedToken;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class IssuedTokenBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String includeTokenValue = SPUtils.getAttribute(element, spVersion.getSPConstants().getIncludeToken());
        Element issuer = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getIssuer());
        String issuerName = SPUtils.getFirstChildElementText(element, spVersion.getSPConstants().getIssuerName());
        if (spVersion == SPConstants.SPVersion.SP11 && issuerName != null) {
            throw new IllegalArgumentException("Invalid Policy");
        }
        Element claims = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getClaims());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        if (nestedPolicyElement == null) {
            throw new IllegalArgumentException("sp:IssuedToken must have an inner wsp:Policy element");
        }
        Policy nestedPolicy = factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        Element requestSecurityTokenTemplate = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getRequestSecurityTokenTemplate());
        if (requestSecurityTokenTemplate == null) {
            throw new IllegalArgumentException("sp:IssuedToken must have a sp:RequestSecurityTokenTemplate element");
        }
        IssuedToken issuedToken = new IssuedToken(spVersion, spVersion.getSPConstants().getInclusionFromAttributeValue(includeTokenValue), issuer, issuerName, requestSecurityTokenTemplate, claims, nestedPolicy);
        issuedToken.setOptional(SPUtils.isOptional(element));
        issuedToken.setIgnorable(SPUtils.isIgnorable(element));
        return issuedToken;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.ISSUED_TOKEN, SP11Constants.ISSUED_TOKEN};
    }
}

