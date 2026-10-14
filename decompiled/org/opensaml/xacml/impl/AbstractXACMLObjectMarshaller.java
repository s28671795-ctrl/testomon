/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Element;

public abstract class AbstractXACMLObjectMarshaller
extends AbstractXMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
    }

    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
    }
}

