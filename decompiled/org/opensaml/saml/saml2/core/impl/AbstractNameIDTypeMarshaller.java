/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.NameIDType;
import org.w3c.dom.Element;

public abstract class AbstractNameIDTypeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        NameIDType nameID = (NameIDType)((Object)samlObject);
        if (nameID.getNameQualifier() != null) {
            domElement.setAttributeNS(null, "NameQualifier", nameID.getNameQualifier());
        }
        if (nameID.getSPNameQualifier() != null) {
            domElement.setAttributeNS(null, "SPNameQualifier", nameID.getSPNameQualifier());
        }
        if (nameID.getFormat() != null) {
            domElement.setAttributeNS(null, "Format", nameID.getFormat());
        }
        if (nameID.getSPProvidedID() != null) {
            domElement.setAttributeNS(null, "SPProvidedID", nameID.getSPProvidedID());
        }
    }

    @Override
    protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
        NameIDType nameID = (NameIDType)((Object)samlObject);
        ElementSupport.appendTextContent(domElement, nameID.getValue());
    }
}

