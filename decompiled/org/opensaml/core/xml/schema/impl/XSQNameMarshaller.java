/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSQName;
import org.w3c.dom.Element;

public class XSQNameMarshaller
extends AbstractXMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
    }

    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
        XSQName qname = (XSQName)xmlObject;
        ElementSupport.appendTextContent(domElement, QNameSupport.qnameToContentString(qname.getValue()));
    }
}

