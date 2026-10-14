/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.osgi.service.blueprint.reflect.BeanProperty
 *  org.osgi.service.blueprint.reflect.Metadata
 */
package org.apache.cxf.ws.policy.blueprint;

import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.cxf.configuration.blueprint.AbstractBPBeanDefinitionParser;
import org.apache.cxf.ws.policy.attachment.external.ExternalAttachmentProvider;
import org.osgi.service.blueprint.reflect.BeanProperty;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;

public class ExternalAttachmentProviderBPDefinitionParser
extends AbstractBPBeanDefinitionParser {
    public Metadata parse(Element element, ParserContext context) {
        MutableBeanMetadata attachmentProvider = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        attachmentProvider.setRuntimeClass(ExternalAttachmentProvider.class);
        if (this.hasBusProperty()) {
            boolean foundBus = false;
            for (BeanProperty bp : attachmentProvider.getProperties()) {
                if (!"bus".equals(bp.getName())) continue;
                foundBus = true;
            }
            if (!foundBus) {
                attachmentProvider.addProperty("bus", (Metadata)this.getBusRef(context, "cxf"));
            }
        }
        this.parseAttributes(element, context, attachmentProvider);
        this.parseChildElements(element, context, attachmentProvider);
        return attachmentProvider;
    }

    @Override
    protected boolean hasBusProperty() {
        return true;
    }
}

