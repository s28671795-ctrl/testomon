/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class AffiliationDescriptorMarshaller
extends AbstractSAMLObjectMarshaller {
    private final Logger log = LoggerFactory.getLogger(AffiliationDescriptorMarshaller.class);

    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AffiliationDescriptor descriptor = (AffiliationDescriptor)samlElement;
        if (descriptor.getOwnerID() != null) {
            domElement.setAttributeNS(null, "affiliationOwnerID", descriptor.getOwnerID());
        }
        if (descriptor.getID() != null) {
            domElement.setAttributeNS(null, "ID", descriptor.getID());
            domElement.setIdAttributeNS(null, "ID", true);
        }
        if (descriptor.getValidUntil() != null) {
            this.log.debug("Writting validUntil attribute to AffiliationDescriptor DOM element");
            String validUntilStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(descriptor.getValidUntil());
            domElement.setAttributeNS(null, "validUntil", validUntilStr);
        }
        if (descriptor.getCacheDuration() != null) {
            this.log.debug("Writting cacheDuration attribute to AffiliationDescriptor DOM element");
            String cacheDuration = DOMTypeSupport.longToDuration(descriptor.getCacheDuration());
            domElement.setAttributeNS(null, "cacheDuration", cacheDuration);
        }
        this.marshallUnknownAttributes(descriptor, domElement);
    }
}

