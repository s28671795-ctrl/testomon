/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeUnmarshaller;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.profile.saml.XACMLPolicyQueryType;

public class XACMLPolicyQueryTypeUnmarshaller
extends RequestAbstractTypeUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        XACMLPolicyQueryType xacmlpolicyquery = (XACMLPolicyQueryType)parentObject;
        if (childObject instanceof RequestType) {
            xacmlpolicyquery.getRequests().add((RequestType)childObject);
        } else if (childObject.getElementQName().equals(IdReferenceType.POLICY_ID_REFERENCE_ELEMENT_NAME)) {
            xacmlpolicyquery.getPolicyIdReferences().add((IdReferenceType)childObject);
        } else if (childObject.getElementQName().equals(IdReferenceType.POLICY_SET_ID_REFERENCE_ELEMENT_NAME)) {
            xacmlpolicyquery.getPolicySetIdReferences().add((IdReferenceType)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }
}

