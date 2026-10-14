/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 */
package org.apache.cxf.configuration.spring;

import org.apache.cxf.helpers.DOMUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class StringBeanDefinitionParser
extends AbstractSingleBeanDefinitionParser {
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addConstructorArgValue((Object)DOMUtils.getRawContent(element));
    }

    protected Class<?> getBeanClass(Element arg0) {
        return String.class;
    }
}

