/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionStatementType;

public class XACMLAuthzDecisionStatementTypeUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        XACMLAuthzDecisionStatementType xacmlauthzdecisionstatement = (XACMLAuthzDecisionStatementType)parentObject;
        if (childObject instanceof RequestType) {
            xacmlauthzdecisionstatement.setRequest((RequestType)childObject);
        } else if (childObject instanceof ResponseType) {
            xacmlauthzdecisionstatement.setResponse((ResponseType)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }
}

