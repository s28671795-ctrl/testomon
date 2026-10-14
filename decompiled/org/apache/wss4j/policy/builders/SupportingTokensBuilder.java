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
import org.apache.wss4j.policy.model.SupportingTokens;
import org.w3c.dom.Element;

public class SupportingTokensBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        QName supportingTokenType = SPUtils.getElementQName(element);
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        Policy nestedPolicy = nestedPolicyElement != null ? factory2.getPolicyEngine().getPolicy(nestedPolicyElement) : new Policy();
        SupportingTokens supportingTokens = new SupportingTokens(spVersion, supportingTokenType, nestedPolicy);
        supportingTokens.setOptional(SPUtils.isOptional(element));
        supportingTokens.setIgnorable(SPUtils.isIgnorable(element));
        return supportingTokens;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.SUPPORTING_TOKENS, SP13Constants.SIGNED_SUPPORTING_TOKENS, SP13Constants.ENDORSING_SUPPORTING_TOKENS, SP13Constants.SIGNED_ENDORSING_SUPPORTING_TOKENS, SP13Constants.ENCRYPTED_SUPPORTING_TOKENS, SP13Constants.SIGNED_ENCRYPTED_SUPPORTING_TOKENS, SP13Constants.ENDORSING_ENCRYPTED_SUPPORTING_TOKENS, SP13Constants.SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS, SP11Constants.SUPPORTING_TOKENS, SP11Constants.SIGNED_SUPPORTING_TOKENS, SP11Constants.ENDORSING_SUPPORTING_TOKENS, SP11Constants.SIGNED_ENDORSING_SUPPORTING_TOKENS};
    }
}

