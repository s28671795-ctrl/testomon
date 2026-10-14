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
import org.opensaml.xacml.profile.saml.XACMLPolicyStatementType;

public class XACMLPolicyStatementTypeUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        XACMLPolicyStatementType xacmlpolicystatement = (XACMLPolicyStatementType)parentObject;
        if (childObject instanceof PolicyType) {
            xacmlpolicystatement.getPolicies().add((PolicyType)childObject);
        } else if (childObject instanceof PolicySetType) {
            xacmlpolicystatement.getPolicySets().add((PolicySetType)childObject);
        } else if (childObject instanceof ReferencedPoliciesType) {
            xacmlpolicystatement.setReferencedPolicies((ReferencedPoliciesType)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }
}

