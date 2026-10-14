/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.spring;

import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.w3c.dom.Element;

public class AddressingBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return WSAddressingFeature.class;
    }
}

