/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdrpi.PublicationInfo;
import org.opensaml.saml.ext.saml2mdrpi.UsagePolicy;
import org.w3c.dom.Attr;

public class PublicationInfoUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        PublicationInfo info = (PublicationInfo)parentObject;
        if (childObject instanceof UsagePolicy) {
            info.getUsagePolicies().add((UsagePolicy)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        PublicationInfo info = (PublicationInfo)samlObject;
        if (attribute.getNamespaceURI() == null) {
            if ("publisher".equals(attribute.getName())) {
                info.setPublisher(attribute.getValue());
            } else if ("creationInstant".equals(attribute.getName())) {
                info.setCreationInstant(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
            } else if ("publicationId".equals(attribute.getName())) {
                info.setPublicationId(attribute.getValue());
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}

