/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class StatusDetailTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        StatusDetailType statusDetail = (StatusDetailType)parentSAMLObject;
        statusDetail.getUnknownXMLObjects().add(childSAMLObject);
    }
}

