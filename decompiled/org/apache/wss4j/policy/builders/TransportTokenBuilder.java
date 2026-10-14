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
import org.apache.wss4j.policy.model.TransportToken;
import org.w3c.dom.Element;

public class TransportTokenBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = nestedPolicyElement != null ? factory2.getPolicyEngine().getPolicy(nestedPolicyElement) : new Policy();
        TransportToken transportToken = new TransportToken(spVersion, nestedPolicy);
        transportToken.setOptional(SPUtils.isOptional(element));
        transportToken.setIgnorable(SPUtils.isIgnorable(element));
        return transportToken;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.TRANSPORT_TOKEN, SP11Constants.TRANSPORT_TOKEN};
    }
}

