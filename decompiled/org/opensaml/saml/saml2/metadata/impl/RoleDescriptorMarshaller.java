/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class RoleDescriptorMarshaller
extends AbstractSAMLObjectMarshaller {
    private final Logger log = LoggerFactory.getLogger(RoleDescriptorMarshaller.class);

    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        List<String> supportedProtocols;
        RoleDescriptor roleDescriptor = (RoleDescriptor)samlElement;
        if (roleDescriptor.getID() != null) {
            this.log.trace("Writing ID attribute to RoleDescriptor DOM element");
            domElement.setAttributeNS(null, "ID", roleDescriptor.getID());
            domElement.setIdAttributeNS(null, "ID", true);
        }
        if (roleDescriptor.getValidUntil() != null) {
            this.log.trace("Writting validUntil attribute to RoleDescriptor DOM element");
            String validUntilStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(roleDescriptor.getValidUntil());
            domElement.setAttributeNS(null, "validUntil", validUntilStr);
        }
        if (roleDescriptor.getCacheDuration() != null) {
            this.log.trace("Writting cacheDuration attribute to EntitiesDescriptor DOM element");
            String cacheDuration = DOMTypeSupport.longToDuration(roleDescriptor.getCacheDuration());
            domElement.setAttributeNS(null, "cacheDuration", cacheDuration);
        }
        if ((supportedProtocols = roleDescriptor.getSupportedProtocols()) != null && supportedProtocols.size() > 0) {
            this.log.trace("Writting protocolSupportEnumberation attribute to RoleDescriptor DOM element");
            StringBuilder builder = new StringBuilder();
            for (String protocol : supportedProtocols) {
                builder.append(protocol);
                builder.append(" ");
            }
            domElement.setAttributeNS(null, "protocolSupportEnumeration", builder.toString().trim());
        }
        if (roleDescriptor.getErrorURL() != null) {
            this.log.trace("Writting errorURL attribute to RoleDescriptor DOM element");
            domElement.setAttributeNS(null, "errorURL", roleDescriptor.getErrorURL());
        }
        this.marshallUnknownAttributes(roleDescriptor, domElement);
    }
}

