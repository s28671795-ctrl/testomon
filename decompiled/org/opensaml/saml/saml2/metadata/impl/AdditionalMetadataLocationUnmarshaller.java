/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.w3c.dom.Attr;

public class AdditionalMetadataLocationUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AdditionalMetadataLocation aml = (AdditionalMetadataLocation)samlObject;
        if (attribute.getLocalName().equals("namespace") && attribute.getNamespaceURI() == null) {
            aml.setNamespaceURI(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }

    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        AdditionalMetadataLocation aml = (AdditionalMetadataLocation)samlObject;
        aml.setLocationURI(elementContent);
    }
}

