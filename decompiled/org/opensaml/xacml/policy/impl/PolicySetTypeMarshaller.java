/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.PolicySetType;
import org.w3c.dom.Element;

public class PolicySetTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        PolicySetType policySet = (PolicySetType)xmlObject;
        if (!Strings.isNullOrEmpty(policySet.getPolicySetId())) {
            domElement.setAttributeNS(null, "PolicySetId", policySet.getPolicySetId());
        }
        if (!Strings.isNullOrEmpty(policySet.getVersion())) {
            domElement.setAttributeNS(null, "Version", policySet.getVersion());
        }
        if (!Strings.isNullOrEmpty(policySet.getPolicyCombiningAlgoId())) {
            domElement.setAttributeNS(null, "PolicyCombiningAlgId", policySet.getPolicyCombiningAlgoId());
        }
    }
}

