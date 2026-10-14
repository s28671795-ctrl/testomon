/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.w3c.dom.Attr;

public class AuthnContextDeclUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        AuthnContextDecl authnCtcDecl = (AuthnContextDecl)parentXMLObject;
        authnCtcDecl.getUnknownXMLObjects().add(childXMLObject);
    }

    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        AuthnContextDecl authnCtcDecl = (AuthnContextDecl)xmlObject;
        QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        if (attribute.isId()) {
            authnCtcDecl.getUnknownAttributes().registerID(attribQName);
        }
        authnCtcDecl.getUnknownAttributes().put(attribQName, attribute.getValue());
    }

    @Override
    protected void processElementContent(XMLObject xmlObject, String elementContent) {
        AuthnContextDecl authnCtcDecl = (AuthnContextDecl)xmlObject;
        authnCtcDecl.setTextContent(elementContent);
    }
}

