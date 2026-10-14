/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.spring;

import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.w3c.dom.Element;

public class SoapBindingInfoConfigBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element arg0) {
        return SoapBindingConfiguration.class;
    }
}

