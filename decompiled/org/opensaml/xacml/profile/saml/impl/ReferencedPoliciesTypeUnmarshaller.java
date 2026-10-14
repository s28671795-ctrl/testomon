/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;

public class ReferencedPoliciesTypeUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        ReferencedPoliciesType referencedpoliciesType = (ReferencedPoliciesType)parentObject;
        if (childObject instanceof PolicyType) {
            referencedpoliciesType.getPolicies().add((PolicyType)childObject);
        } else if (childObject instanceof PolicySetType) {
            referencedpoliciesType.getPolicySets().add((PolicySetType)childObject);
        }
    }
}

