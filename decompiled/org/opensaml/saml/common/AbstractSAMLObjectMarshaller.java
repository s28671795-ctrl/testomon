/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.SAMLObjectSupport;
import org.opensaml.saml.common.SignableSAMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractSAMLObjectMarshaller
extends AbstractXMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
    }

    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
    }

    @Override
    public Element marshall(XMLObject xmlObject, Document document) throws MarshallingException {
        if (xmlObject instanceof SignableSAMLObject) {
            SAMLObjectSupport.declareNonVisibleNamespaces((SignableSAMLObject)xmlObject);
        }
        return super.marshall(xmlObject, document);
    }

    @Override
    public Element marshall(XMLObject xmlObject, Element parentElement) throws MarshallingException {
        if (xmlObject instanceof SignableSAMLObject) {
            SAMLObjectSupport.declareNonVisibleNamespaces((SignableSAMLObject)xmlObject);
        }
        return super.marshall(xmlObject, parentElement);
    }
}

