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
import org.apache.wss4j.policy.model.Wss11;
import org.w3c.dom.Element;

public class WSS11Builder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = nestedPolicyElement != null ? factory2.getPolicyEngine().getPolicy(nestedPolicyElement) : new Policy();
        Wss11 wss11 = new Wss11(spVersion, nestedPolicy);
        wss11.setOptional(SPUtils.isOptional(element));
        wss11.setIgnorable(SPUtils.isIgnorable(element));
        return wss11;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.WSS11, SP11Constants.WSS11};
    }
}

