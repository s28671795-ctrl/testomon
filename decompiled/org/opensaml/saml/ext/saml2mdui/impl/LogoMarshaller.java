/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class LogoMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        Logo logo = (Logo)samlObject;
        if (logo.getXMLLang() != null) {
            Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), "http://www.w3.org/XML/1998/namespace", "lang", "xml");
            attribute.setValue(logo.getXMLLang());
            domElement.setAttributeNodeNS(attribute);
        }
        if (logo.getHeight() != null) {
            domElement.setAttributeNS(null, "height", logo.getHeight().toString());
        }
        if (logo.getWidth() != null) {
            domElement.setAttributeNS(null, "width", logo.getWidth().toString());
        }
    }

    @Override
    protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
        Logo logo = (Logo)samlObject;
        if (logo.getURL() != null) {
            ElementSupport.appendTextContent(domElement, logo.getURL());
        }
    }
}

