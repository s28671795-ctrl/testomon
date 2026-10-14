/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.spring;

import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.ws.policy.attachment.external.ExternalAttachmentProvider;
import org.w3c.dom.Element;

public class ExternalAttachmentProviderBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element el) {
        return ExternalAttachmentProvider.class;
    }

    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }

    @Override
    protected boolean hasBusProperty() {
        return true;
    }
}

