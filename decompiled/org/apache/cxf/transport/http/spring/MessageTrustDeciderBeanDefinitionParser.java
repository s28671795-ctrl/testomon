/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.spring;

import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.w3c.dom.Element;

public class MessageTrustDeciderBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected String getJaxbPackage() {
        return "org.apache.cxf.transports.http.configuration";
    }

    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return MessageTrustDecider.class;
    }
}

