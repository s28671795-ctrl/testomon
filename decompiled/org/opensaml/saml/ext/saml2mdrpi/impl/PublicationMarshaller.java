/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdrpi.Publication;
import org.w3c.dom.Element;

public class PublicationMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        Publication info = (Publication)samlObject;
        if (info.getPublisher() != null) {
            domElement.setAttributeNS(null, "publisher", info.getPublisher());
        }
        if (info.getPublicationId() != null) {
            domElement.setAttributeNS(null, "publicationId", info.getPublicationId());
        }
        if (info.getCreationInstant() != null) {
            String creationInstant = ISODateTimeFormat.dateTime().print(info.getCreationInstant());
            domElement.setAttributeNS(null, "creationInstant", creationInstant);
        }
    }
}

