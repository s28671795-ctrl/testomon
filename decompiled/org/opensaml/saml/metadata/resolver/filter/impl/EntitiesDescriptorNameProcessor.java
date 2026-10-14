/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.EntityGroupName;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataNodeProcessor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitiesDescriptorNameProcessor
implements MetadataNodeProcessor {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(EntitiesDescriptorNameProcessor.class);

    @Override
    public void process(XMLObject metadataNode) throws FilterException {
        if (metadataNode instanceof EntityDescriptor) {
            for (XMLObject currentParent = metadataNode.getParent(); currentParent != null; currentParent = currentParent.getParent()) {
                String name;
                if (!(currentParent instanceof EntitiesDescriptor) || (name = StringSupport.trimOrNull(((EntitiesDescriptor)currentParent).getName())) == null) continue;
                if (this.log.isTraceEnabled()) {
                    this.log.trace("Attaching EntityGroupName '{}' to EntityDescriptor: {}", (Object)name, (Object)((EntityDescriptor)metadataNode).getEntityID());
                }
                metadataNode.getObjectMetadata().put(new EntityGroupName(name));
            }
        }
    }
}

