/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeUnmarshaller;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;
import org.w3c.dom.Attr;

public class XACMLAuthzDecisionQueryTypeUnmarshaller
extends RequestAbstractTypeUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        XACMLAuthzDecisionQueryType xacmlauthzdecisionquery = (XACMLAuthzDecisionQueryType)parentObject;
        if (childObject instanceof RequestType) {
            xacmlauthzdecisionquery.setRequest((RequestType)childObject);
        } else if (childObject instanceof PolicyType) {
            xacmlauthzdecisionquery.getPolicies().add((PolicyType)childObject);
        } else if (childObject instanceof PolicySetType) {
            xacmlauthzdecisionquery.getPolicySets().add((PolicySetType)childObject);
        } else if (childObject instanceof ReferencedPoliciesType) {
            xacmlauthzdecisionquery.setReferencedPolicies((ReferencedPoliciesType)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        XACMLAuthzDecisionQueryType authzDS = (XACMLAuthzDecisionQueryType)samlObject;
        if (attribute.getLocalName().equals("InputContextOnly")) {
            authzDS.setInputContextOnly(XSBooleanValue.valueOf(attribute.getValue()));
        }
        if (attribute.getLocalName().equals("ReturnContext")) {
            authzDS.setReturnContext(XSBooleanValue.valueOf(attribute.getValue()));
        }
        if (attribute.getLocalName().equals("CombinePolicies")) {
            authzDS.setCombinePolicies(XSBooleanValue.valueOf(attribute.getValue()));
        }
        super.processAttribute(samlObject, attribute);
    }
}

