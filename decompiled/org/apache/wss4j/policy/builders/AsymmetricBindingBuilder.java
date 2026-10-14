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
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.w3c.dom.Element;

public class AsymmetricBindingBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        if (nestedPolicyElement == null) {
            throw new IllegalArgumentException("sp:AsymmetricBinding must have an inner wsp:Policy element");
        }
        Policy nestedPolicy = factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        AsymmetricBinding asymmetricBinding = new AsymmetricBinding(spVersion, nestedPolicy);
        asymmetricBinding.setOptional(SPUtils.isOptional(element));
        asymmetricBinding.setIgnorable(SPUtils.isIgnorable(element));
        return asymmetricBinding;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.ASYMMETRIC_BINDING, SP11Constants.ASYMMETRIC_BINDING};
    }
}

