/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class StatusMessageTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
        StatusMessageType message = (StatusMessageType)xmlObject;
        if (message.getValue() != null) {
            ElementSupport.appendTextContent(domElement, message.getValue());
        }
    }
}

