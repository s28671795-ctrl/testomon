/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeMarshaller;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;
import org.w3c.dom.Element;

public class XACMLAuthzDecisionQueryTypeMarshaller
extends RequestAbstractTypeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        XACMLAuthzDecisionQueryType query = (XACMLAuthzDecisionQueryType)samlObject;
        if (query.getInputContextOnlyXSBooleanValue() != null) {
            domElement.setAttributeNS(null, "InputContextOnly", query.getInputContextOnlyXSBooleanValue().toString());
        }
        if (query.getReturnContextXSBooleanValue() != null) {
            domElement.setAttributeNS(null, "ReturnContext", query.getReturnContextXSBooleanValue().toString());
        }
        if (query.getCombinePoliciesXSBooleanValue() != null) {
            domElement.setAttributeNS(null, "CombinePolicies", query.getCombinePoliciesXSBooleanValue().toString());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

