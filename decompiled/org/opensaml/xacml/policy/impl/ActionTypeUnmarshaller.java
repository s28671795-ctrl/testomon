/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.ActionType;

public class ActionTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        ActionType actionType = (ActionType)parentXMLObject;
        if (childXMLObject instanceof ActionMatchType) {
            actionType.getActionMatches().add((ActionMatchType)childXMLObject);
        } else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}

