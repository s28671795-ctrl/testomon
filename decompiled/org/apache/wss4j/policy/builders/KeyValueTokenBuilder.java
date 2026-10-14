/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class KeyValueTokenBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String includeTokenValue = SPUtils.getAttribute(element, spVersion.getSPConstants().getIncludeToken());
        Element issuer = SPUtils.getFirstChildElement((Node)element, spVersion.getSPConstants().getIssuer());
        if (issuer != null) {
            throw new IllegalArgumentException("Invalid Policy");
        }
        String issuerName = SPUtils.getFirstChildElementText(element, spVersion.getSPConstants().getIssuerName());
        if (issuerName != null) {
            throw new IllegalArgumentException("Invalid Policy");
        }
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = null;
        nestedPolicy = nestedPolicyElement == null ? new Policy() : factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        KeyValueToken keyValueToken = new KeyValueToken(spVersion, spVersion.getSPConstants().getInclusionFromAttributeValue(includeTokenValue), nestedPolicy);
        keyValueToken.setOptional(SPUtils.isOptional(element));
        keyValueToken.setIgnorable(SPUtils.isIgnorable(element));
        return keyValueToken;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.KEY_VALUE_TOKEN};
    }
}

