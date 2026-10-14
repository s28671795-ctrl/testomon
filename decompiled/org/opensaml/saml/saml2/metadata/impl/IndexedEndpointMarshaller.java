/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.opensaml.saml.saml2.metadata.impl.EndpointMarshaller;
import org.w3c.dom.Element;

public class IndexedEndpointMarshaller
extends EndpointMarshaller {
    @Override
    public void marshallAttributes(XMLObject samlObject, Element domElement) {
        IndexedEndpoint iEndpoint = (IndexedEndpoint)samlObject;
        if (iEndpoint.getIndex() != null) {
            domElement.setAttributeNS(null, "index", iEndpoint.getIndex().toString());
        }
        if (iEndpoint.isDefaultXSBoolean() != null) {
            domElement.setAttributeNS(null, "isDefault", iEndpoint.isDefaultXSBoolean().toString());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

