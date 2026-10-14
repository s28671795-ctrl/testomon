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
import org.apache.wss4j.policy.model.InitiatorEncryptionToken;
import org.w3c.dom.Element;

public class InitiatorEncryptionTokenBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = nestedPolicyElement != null ? factory2.getPolicyEngine().getPolicy(nestedPolicyElement) : new Policy();
        InitiatorEncryptionToken initiatorEncryptionToken = new InitiatorEncryptionToken(spVersion, nestedPolicy);
        initiatorEncryptionToken.setOptional(SPUtils.isOptional(element));
        initiatorEncryptionToken.setIgnorable(SPUtils.isIgnorable(element));
        return initiatorEncryptionToken;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.INITIATOR_ENCRYPTION_TOKEN, SP11Constants.INITIATOR_ENCRYPTION_TOKEN};
    }
}

