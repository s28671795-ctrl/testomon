/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.IDPEntry;
import org.w3c.dom.Element;

public class IDPEntryMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        IDPEntry entry = (IDPEntry)samlObject;
        if (entry.getProviderID() != null) {
            domElement.setAttributeNS(null, "ProviderID", entry.getProviderID());
        }
        if (entry.getName() != null) {
            domElement.setAttributeNS(null, "Name", entry.getName());
        }
        if (entry.getLoc() != null) {
            domElement.setAttributeNS(null, "Loc", entry.getLoc());
        }
    }
}

