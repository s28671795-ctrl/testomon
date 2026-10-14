/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractXACMLObjectUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
    }

    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
    }

    @Override
    protected void processElementContent(XMLObject xmlObject, String elementContent) {
    }
}

