/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.builders.Trust10Builder;
import org.apache.wss4j.policy.model.Trust13;
import org.w3c.dom.Element;

public class Trust13Builder
extends Trust10Builder {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        if (nestedPolicyElement == null) {
            throw new IllegalArgumentException("sp:Trust13 must have an inner wsp:Policy element");
        }
        Policy nestedPolicy = factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        Trust13 trust13 = new Trust13(spVersion, nestedPolicy);
        trust13.setOptional(SPUtils.isOptional(element));
        trust13.setIgnorable(SPUtils.isIgnorable(element));
        return trust13;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.TRUST_13};
    }
}

