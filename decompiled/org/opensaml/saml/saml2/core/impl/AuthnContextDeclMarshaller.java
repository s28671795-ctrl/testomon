/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.Map;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class AuthnContextDeclMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AuthnContextDecl authnCtxDecl = (AuthnContextDecl)xmlObject;
        for (Map.Entry<QName, String> entry : authnCtxDecl.getUnknownAttributes().entrySet()) {
            Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (!XMLObjectProviderRegistrySupport.isIDAttribute(entry.getKey()) && !authnCtxDecl.getUnknownAttributes().isIDAttribute(entry.getKey())) continue;
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
    }

    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AuthnContextDecl authnCtxDecl = (AuthnContextDecl)xmlObject;
        if (authnCtxDecl.getTextContent() != null) {
            ElementSupport.appendTextContent(domElement, authnCtxDecl.getTextContent());
        }
    }
}

