/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.w3c.dom.Attr;

public class XSStringUnmarshaller
extends AbstractXMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
    }

    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
    }

    @Override
    protected void processElementContent(XMLObject xmlObject, String elementContent) {
        XSString xsiString = (XSString)xmlObject;
        xsiString.setValue(elementContent);
    }
}

