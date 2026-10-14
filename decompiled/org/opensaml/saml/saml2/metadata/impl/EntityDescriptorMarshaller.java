/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EntityDescriptorMarshaller
extends AbstractSAMLObjectMarshaller {
    private final Logger log = LoggerFactory.getLogger(EntityDescriptorMarshaller.class);

    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) {
        EntityDescriptor entityDescriptor = (EntityDescriptor)samlElement;
        if (entityDescriptor.getEntityID() != null) {
            domElement.setAttributeNS(null, "entityID", entityDescriptor.getEntityID());
        }
        if (entityDescriptor.getID() != null) {
            domElement.setAttributeNS(null, "ID", entityDescriptor.getID());
            domElement.setIdAttributeNS(null, "ID", true);
        }
        if (entityDescriptor.getValidUntil() != null) {
            this.log.debug("Writting validUntil attribute to EntityDescriptor DOM element");
            String validUntilStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(entityDescriptor.getValidUntil());
            domElement.setAttributeNS(null, "validUntil", validUntilStr);
        }
        if (entityDescriptor.getCacheDuration() != null) {
            this.log.debug("Writting cacheDuration attribute to EntityDescriptor DOM element");
            String cacheDuration = DOMTypeSupport.longToDuration(entityDescriptor.getCacheDuration());
            domElement.setAttributeNS(null, "cacheDuration", cacheDuration);
        }
        this.marshallUnknownAttributes(entityDescriptor, domElement);
    }
}

